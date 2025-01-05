package fr.montpellier.myecommerce.adapter.client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Date;
import java.util.List;

import fr.montpellier.myecommerce.R;
import fr.montpellier.myecommerce.activity.client.ConsultBasketActivityC;
import fr.montpellier.myecommerce.activity.client.ConsultProductActivityC;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Offer;
import fr.montpellier.myecommerce.db.entity.Product;
import fr.montpellier.myecommerce.db.entity.ProductInBasket;

public class ProductInBasketAdapterC extends ArrayAdapter<ProductInBasket> {
    private final int resourceLayout;
    private final ConsultBasketActivityC activity;

    public ProductInBasketAdapterC(ConsultBasketActivityC cba, List<ProductInBasket> pibs) {
        super(cba, R.layout.c_product_item_basket, pibs);
        this.resourceLayout = R.layout.c_product_item_basket;
        activity = cba;
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

        ProductInBasket pib = getItem(position);

        if (pib != null) {
            Product p = AppDatabase.getInstance(getContext()).productDAO().get(pib.id_product);

            TextView name_product = v.findViewById(R.id.product_name);
            TextView total_price = v.findViewById(R.id.total_price_product);
            TextView quantity = v.findViewById(R.id.quantity);
            ImageButton change_quantity = v.findViewById(R.id.validate_quantity);
            Button remove_from_basket = v.findViewById(R.id.remove_from_basket);

            ConstraintLayout btn_item = v.findViewById(R.id.btn_item);

            change_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppDatabase.getInstance(getContext()).productInBasketDAO().changeQuantity(pib.id_pib,Integer.parseInt(quantity.getText().toString()));
                    activity.updateBasket();
                }
            });

            remove_from_basket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppDatabase.getInstance(getContext()).productInBasketDAO().clear(pib.id_pib);
                    activity.updateBasket();
                }
            });

            btn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    intent = new Intent(getContext(), ConsultProductActivityC.class);
                    intent.putExtra("product",p);
                    getContext().startActivity(intent);
                }
            });

            if (name_product != null) {
                name_product.setText(p.name);
            }

            if (total_price != null) {

                float price;
                Offer offer = AppDatabase.getInstance(getContext()).offerDAO().get(p.id_product,new Date());
                if(offer== null ){
                    price = p.price;
                } else {
                    price = offer.price;
                }

                float total_price_product = Math.round(price * pib.quantity *100.0f)/100.0f;
                total_price.setText(String.valueOf(total_price_product) + getContext().getResources().getString(R.string.euro));
            }

            if (quantity != null) {
                quantity.setText(String.valueOf(pib.quantity));
            }
        }

        return v;
    }
}
