package fr.montpellier.myecommerce.activity.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import fr.montpellier.myecommerce.activity.ConnectedClientActivity;
import fr.montpellier.myecommerce.adapter.client.CategoryListAdapterC;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Category;
import fr.montpellier.myecommerce.db.entity.Store;

public class ConsultCategoriesStoreActivityC extends ConnectedClientActivity {
    Store store;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(is_redirected) return;
        this.setContentView(R.layout.c_consult_categories_store);
        store = (Store) getIntent().getSerializableExtra("store");

        ListView list_categories = findViewById(R.id.list_categories);
        TextView title_consult_categories = findViewById(R.id.categories_of_title);
        TextView no_result  = findViewById(R.id.no_result);

        title_consult_categories.setText(getResources().getString(R.string.categories_of) + store.name);

        List<Category> categories = AppDatabase.getInstance(ConsultCategoriesStoreActivityC.this).categoryDAO().getCategoriesWithStore(store.id_user_store);

        if(categories.size()>0){
            CategoryListAdapterC customAdapter = new CategoryListAdapterC(this, categories);
            list_categories.setAdapter(customAdapter);
            no_result.setVisibility(View.GONE);
        } else {
            no_result.setVisibility(View.VISIBLE);
        }
    }
}
