package fr.montpellier.myecommerce.activity.fournisseur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import fr.montpellier.myecommerce.R;
import fr.montpellier.myecommerce.activity.ConnectedMerchantWithStoreActivity;
import fr.montpellier.myecommerce.activity.IdentificationActivity;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Product;
import fr.montpellier.myecommerce.db.entity.Store;
import fr.montpellier.myecommerce.manager.ConnectionManager;

public class MenuActivityM extends ConnectedMerchantWithStoreActivity {
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(is_redirected) return;
        setContentView(R.layout.m_menu);
        TextView store_name = findViewById(R.id.store_name);
        store = AppDatabase.getInstance(this).storeDAO().get(ConnectionManager.getInstance().getUtilisateur().id_user);
        store_name.setText(store.name);
    }

    public void disconnect(View view) {
        ConnectionManager cm = ConnectionManager.getInstance();
        cm.disconnect();
        cm.removeTokenUserFromPrefs(this);
        startActivity(new Intent(this, IdentificationActivity.class));
    }

    public void myCategories(View view) {
        startActivity(new Intent(this,ConsultCategoriesStoreActivityM.class));
    }

    public void myProducts(View view) {
        Intent intent;
        intent = new Intent(this, ConsultListProductsActivityM.class);
        intent.putExtra("title", getResources().getString(R.string.products_of)+store.name);
        ArrayList<Product> products = (ArrayList<Product>) AppDatabase.getInstance(this).categoryDAO().getProductsOfCategoriesofStore(store.id_user_store);
        intent.putExtra("products",products);
        startActivity(intent);
    }

    public void orders(View view) {
        startActivity(new Intent(this,ConsultOrdersActivityM.class));
    }
}