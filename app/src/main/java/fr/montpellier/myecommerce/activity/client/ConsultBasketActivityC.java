package fr.montpellier.myecommerce.activity.client;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;


import fr.montpellier.myecommerce.R;
import fr.montpellier.myecommerce.activity.ConnectedClientActivity;
import fr.montpellier.myecommerce.adapter.client.ProductInBasketAdapterC;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Offer;
import fr.montpellier.myecommerce.db.entity.Order;
import fr.montpellier.myecommerce.db.entity.Product;
import fr.montpellier.myecommerce.db.entity.ProductInBasket;
import fr.montpellier.myecommerce.db.entity.ProductInOrder;
import fr.montpellier.myecommerce.db.entity.Store;
import fr.montpellier.myecommerce.db.entity.User;
import fr.montpellier.myecommerce.manager.BasketManager;
import fr.montpellier.myecommerce.manager.ConnectionManager;

public class ConsultBasketActivityC extends ConnectedClientActivity {

    private TextView no_result;
    private ListView list_products_basket;
    TextView total_price;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (is_redirected) return;
        this.setContentView(R.layout.c_consult_basket);

        user = ConnectionManager.getInstance().getUtilisateur();

        list_products_basket = findViewById(R.id.list_products_basket);
        no_result = findViewById(R.id.no_result);
        total_price = findViewById(R.id.total_price);
        Button clear_basket = findViewById(R.id.clear_basket);
        Button validate_basket = findViewById(R.id.validate_basket);

        clear_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase.getInstance(ConsultBasketActivityC.this).productInBasketDAO().clearBasket(user.id_user);
                updateBasket();
            }
        });

        validate_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase apd = AppDatabase.getInstance(ConsultBasketActivityC.this);
                Date date = new Date();
                //Get all stores concerned
                List<Store> stores = apd.productInBasketDAO().getStoreWithProductsInBasket(user.id_user);
                int size_stores = stores.size();
                if (size_stores <= 0) {
                    Toast.makeText(ConsultBasketActivityC.this, "Your basket is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < size_stores; i++) {
                    //One order by store
                    Order o = new Order(stores.get(i).id_user_store, user.id_user, date);
                    int id_order = (int) apd.orderDAO().insert(o);
                    //Get all products of this store
                    List<ProductInBasket> pibs = apd.productInBasketDAO().getWithStoreAndUser(stores.get(i).id_user_store, user.id_user);

                    int size_pibs = pibs.size();
                    for (int j = 0; j < size_pibs; j++) {
                        Product product = apd.productDAO().get(pibs.get(j).id_product);

                        float price;
                        Offer offer = apd.offerDAO().get(product.id_product, new Date());
                        if (offer == null) {
                            price = product.price;
                        } else {
                            price = offer.price;
                        }

                        float total_price = pibs.get(j).quantity * price;
                        ProductInOrder pio = new ProductInOrder(id_order, pibs.get(j).id_product, pibs.get(j).quantity, total_price);
                        apd.productInOrderDAO().insert(pio);
                    }
                }
                apd.productInBasketDAO().clearBasket(user.id_user);


                finish();
                Intent intent;
                intent = new Intent(ConsultBasketActivityC.this, ConsultOrdersActivityC.class);
                startActivity(intent);
            }
        });

        updateBasket();
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateBasket();
    }

    @SuppressLint("SetTextI18n")
    public void updateBasket() {
        List<ProductInBasket> pibs = AppDatabase.getInstance(ConsultBasketActivityC.this).productInBasketDAO().getWithUser(user.id_user);
        ProductInBasketAdapterC customAdapter = new ProductInBasketAdapterC(this, pibs);
        list_products_basket.setAdapter(customAdapter);
        if (pibs.size() > 0) {
            no_result.setVisibility(View.GONE);
        } else {
            no_result.setVisibility(View.VISIBLE);
        }
        float price = BasketManager.getSumBasket(this, user.id_user);
        total_price.setText(price + getResources().getString(R.string.euro));
    }
}
