package fr.montpellier.myecommerce.activity.fournisseur;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import fr.montpellier.myecommerce.R;
import fr.montpellier.myecommerce.activity.ConnectedMerchantWithStoreActivity;
import fr.montpellier.myecommerce.adapter.fournisseur.OrderListAdapterM;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Order;
import fr.montpellier.myecommerce.manager.ConnectionManager;

public class ConsultOrdersActivityM extends ConnectedMerchantWithStoreActivity {
    List<Order> orders;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(is_redirected) return;
        this.setContentView(R.layout.m_consult_orders);

        ListView list_products = findViewById(R.id.list_orders);
        TextView title_consult_products = findViewById(R.id.title_consult_orders);
        TextView no_result  = findViewById(R.id.no_result);

        title_consult_products.setText(getResources().getString(R.string.my_orders));

        orders = AppDatabase.getInstance(this).orderDAO().getWithStore(ConnectionManager.getInstance().getUtilisateur().id_user);

        if(orders.size()>0){
            OrderListAdapterM customAdapter = new OrderListAdapterM(this, orders);
            list_products.setAdapter(customAdapter);
            no_result.setVisibility(View.GONE);
        } else {
            no_result.setVisibility(View.VISIBLE);
        }
    }
}
