package fr.montpellier.myecommerce.activity.client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fr.montpellier.myecommerce.R;
import fr.montpellier.myecommerce.activity.ConnectedClientActivity;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Category;
import fr.montpellier.myecommerce.db.entity.Offer;
import fr.montpellier.myecommerce.db.entity.Product;
import fr.montpellier.myecommerce.db.entity.ProductInBasket;
import fr.montpellier.myecommerce.db.entity.Store;
import fr.montpellier.myecommerce.db.entity.User;
import fr.montpellier.myecommerce.manager.ConnectionManager;

import java.util.Date;

public class ConsultProductActivityC extends ConnectedClientActivity {
    Product product;
    Category category;
    Store store;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(is_redirected) return;
        setContentView(R.layout.c_consult_product);
        AppDatabase apd = AppDatabase.getInstance(this);
        product = (Product) getIntent().getSerializableExtra("product");
        category = apd.categoryDAO().get(product.id_category);
        store = apd.storeDAO().get(category.id_store);

        Button consult_store = findViewById(R.id.consult_store);
        Button validate_quantity = findViewById(R.id.validate_quantity);
        ImageView img_product = findViewById(R.id.img_product);
        TextView store_name = findViewById(R.id.store_name);
        TextView category_name = findViewById(R.id.category_name);
        TextView product_name = findViewById(R.id.product_name);
        TextView price_product = findViewById(R.id.price_product);
        TextView details_product = findViewById(R.id.product_details);
        TextView quantity = findViewById(R.id.quantity);
        User user = ConnectionManager.getInstance().getUtilisateur();
        ProductInBasket pib = apd.productInBasketDAO().getWithProductAndUser(product.id_product, user.id_user);
        if(pib != null){
            quantity.setText(String.valueOf(pib.quantity));
        } else {
            quantity.setText("0");
        }

        product_name.setText(product.name);
        category_name.setText(category.name);
        store_name.setText(store.name);

        float price;
        Offer offer = apd.offerDAO().get(product.id_product,new Date());
        if(offer == null ){
            price = product.price;
        } else {
            price = offer.price;
        }

        price_product.setText(String.valueOf(price)+getResources().getString(R.string.euro));
        details_product.setText(product.details);
        consult_store.setText(getResources().getString(R.string.consult) + store.name);

        Bitmap bmp;
        if (product.img != null) {
            bmp = BitmapFactory.decodeByteArray(product.img, 0, product.img.length);
        } else {
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.product);
        }
        img_product.setImageBitmap(bmp);

        consult_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(ConsultProductActivityC.this, ConsultStoreActivityC.class);
                intent.putExtra("store",store);
                ConsultProductActivityC.this.startActivity(intent);
            }
        });

        validate_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity_int = Integer.parseInt(quantity.getText().toString());
                if(quantity_int > 0){
                    if(pib != null){
                        apd.productInBasketDAO().changeQuantity(pib.id_pib,quantity_int);
                        Toast.makeText(ConsultProductActivityC.this,"Quantity has been modified",Toast.LENGTH_SHORT).show();
                    } else {
                        ProductInBasket new_pib = new ProductInBasket(product.id_product,user.id_user,quantity_int);
                        apd.productInBasketDAO().insert(new_pib);
                        Toast.makeText(ConsultProductActivityC.this,"The product has been added in "+quantity_int+" cop"+(quantity_int>1?"ies":"y"),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ConsultProductActivityC.this,"Quantity must be valid",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}