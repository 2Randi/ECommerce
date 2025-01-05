package fr.montpellier.myecommerce.activity.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import fr.montpellier.myecommerce.R;

import fr.montpellier.myecommerce.activity.ConnectedClientActivity;
import fr.montpellier.myecommerce.adapter.client.ProductListAdapterC;
import fr.montpellier.myecommerce.db.entity.Product;
import java.util.List;

public class ConsultListProductsActivityC extends ConnectedClientActivity {
    List<Product> products;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(is_redirected) return;
        this.setContentView(R.layout.c_list_products);
        products = (List<Product>) getIntent().getSerializableExtra("products");
        String title = getIntent().getStringExtra("title");
        boolean is_interesting_products = getIntent().getBooleanExtra("is_interesting_products",false);

        ListView list_products = findViewById(R.id.list_products);
        TextView title_consult_products = findViewById(R.id.title_consult_products);
        TextView no_result  = findViewById(R.id.no_result);

        title_consult_products.setText(title);

        if(products.size()>0){
            ProductListAdapterC customAdapter = new ProductListAdapterC(this, products);
            list_products.setAdapter(customAdapter);
            no_result.setVisibility(View.GONE);
        } else {
            no_result.setVisibility(View.VISIBLE);
        }
    }
}