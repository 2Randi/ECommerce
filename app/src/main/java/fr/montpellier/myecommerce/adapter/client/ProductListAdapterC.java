package fr.montpellier.myecommerce.adapter.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Date;
import java.util.List;

import fr.montpellier.myecomerce.R;
import fr.montpellier.myecommerce.activity.client.ConsultProductActivityC;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Offer;
import fr.montpellier.myecommerce.db.entity.Product;
import fr.montpellier.myecommerce.db.entity.ProductInBasket;
import fr.montpellier.myecommerce.db.entity.User;
import fr.montpellier.myecommerce.manager.ConnectionManager;

public class ProductListAdapterC extends ArrayAdapter<Product> {
    private int resourceLayout;

    public ProductListAdapterC(Context c, List<Product> products) {
        super(c, R.layout.c_product_item, products);
        this.resourceLayout = R.layout.c_product_item;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(resourceLayout, null);
        }

        Product p = getItem(position);
        User user = ConnectionManager.getInstance().getUtilisateur();

        if (p != null) {
            TextView name_product = v.findViewById(R.id.product_name);
            TextView details_product = v.findViewById(R.id.product_details);
            TextView price_product = v.findViewById(R.id.product_price);
            final Button add_to_basket = v.findViewById(R.id.add_to_basket);

            ConstraintLayout btn_item = v.findViewById(R.id.btn_item);
            btn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    intent = new Intent(getContext(), ConsultProductActivityC.class);
                    intent.putExtra("product",p);
                    getContext().startActivity(intent);
                }
            });

            ProductInBasket pib = AppDatabase.getInstance(getContext()).productInBasketDAO().getWithProductAndUser(p.id_product,user.id_user);

            if(pib==null){
                add_to_basket.setVisibility(View.VISIBLE);
                add_to_basket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProductInBasket new_pib = new ProductInBasket(p.id_product,user.id_user,1);
                        AppDatabase.getInstance(getContext()).productInBasketDAO().insert(new_pib);
                        Toast.makeText(getContext(),"The product has been added in the basket",Toast.LENGTH_SHORT).show();
                        add_to_basket.setVisibility(View.GONE);
                    }
                });
            } else {
                add_to_basket.setVisibility(View.GONE);
            }

            if (name_product != null ) {
                name_product.setText(p.name);
            }

            if (details_product != null ) {
                details_product.setText(p.details);
            }

            if (price_product != null ) {
                float price;
                Offer offer = AppDatabase.getInstance(getContext()).offerDAO().get(p.id_product,new Date());
                if(offer== null ){
                    price = p.price;
                } else {
                    price = offer.price;
                }
                price_product.setText(String.valueOf(price)+getContext().getResources().getString(R.string.euro));
            }
        }

        return v;
    }
}
