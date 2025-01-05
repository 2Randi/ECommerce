package fr.montpellier.myecommerce.activity.client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import fr.montpellier.myecommerce.activity.ConnectedClientActivity;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Product;
import fr.montpellier.myecommerce.db.entity.Store;
import fr.montpellier.myecommerce.R;

import java.util.ArrayList;

public class ConsultStoreActivityC extends ConnectedClientActivity {
    Store store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(is_redirected) return;
        setContentView(R.layout.c_consult_store);
        store = (Store) getIntent().getSerializableExtra("store");
        ImageButton consult_categories = findViewById(R.id.consult_categories);
        ImageButton consult_products = findViewById(R.id.consult_products);
        //  Button consult_categories = findViewById(R.id.consult_categories);
        //  Button consult_products = findViewById(R.id.consult_products);
        ImageView image_store = findViewById(R.id.img_store);
        TextView name_store = findViewById(R.id.name_store);
        name_store.setText(store.name);
 /*
        Bitmap bmp;
        if ( store.img_store != null) {
            bmp = BitmapFactory.decodeByteArray(store.img_store, 0, store.img_store.length);
        } else {
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.store);
        }
        image_store.setImageBitmap(bmp);
        */


        consult_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(ConsultStoreActivityC.this, ConsultCategoriesStoreActivityC.class);
                intent.putExtra("store",store);
                ConsultStoreActivityC.this.startActivity(intent);
            }
        });

        consult_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(ConsultStoreActivityC.this, ConsultListProductsActivityC.class);
                intent.putExtra("title",getResources().getString(R.string.products_of)+store.name);
                ArrayList<Product> products = (ArrayList<Product>) AppDatabase.getInstance(ConsultStoreActivityC.this).categoryDAO().getProductsOfCategoriesofStore(store.id_user_store);
                intent.putExtra("products",products);
                ConsultStoreActivityC.this.startActivity(intent);
            }
        });

    }

}