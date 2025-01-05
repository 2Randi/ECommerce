package fr.montpellier.myecommerce.activity.client;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.montpellier.myecommerce.activity.ConnectedClientActivity;
import fr.montpellier.myecommerce.adapter.client.StoreListAdapterC;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Store;
import fr.montpellier.myecommerce.R;

public class FindStoreActivityC extends ConnectedClientActivity {

    private ListView list_stores;
    private TextView no_result;
    private EditText store_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(is_redirected) return;
        this.setContentView(R.layout.c_find_store);


        list_stores = findViewById(R.id.list_stores);
        no_result  = findViewById(R.id.no_result);

        updateListStores("");

        store_name = findViewById(R.id.find_stores);
        store_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateListStores(editable.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListStores(store_name.getText().toString());
    }

    public void updateListStores(String str){
        List<Store> stores = AppDatabase.getInstance(FindStoreActivityC.this).storeDAO().getStoresLike(str);
        StoreListAdapterC customAdapter = new StoreListAdapterC(this, stores);
        list_stores.setAdapter(customAdapter);
        if(stores.size()>0){
            no_result.setVisibility(View.GONE);
        } else {
            no_result.setVisibility(View.VISIBLE);
        }
    }
}