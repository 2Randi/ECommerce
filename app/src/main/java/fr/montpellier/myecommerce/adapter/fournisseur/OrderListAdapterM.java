package fr.montpellier.myecommerce.adapter.fournisseur;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import fr.montpellier.myecomerce.R;
import fr.montpellier.myecommerce.activity.fournisseur.ConsultOrderActivityM;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Order;
import fr.montpellier.myecommerce.db.entity.Store;
import fr.montpellier.myecommerce.db.entity.User;

public class OrderListAdapterM extends ArrayAdapter<Order> {
    private int resourceLayout;

    public OrderListAdapterM(Context c, List<Order> orders) {
        super(c, R.layout.m_order_item, orders);
        this.resourceLayout = R.layout.m_order_item;
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

        Order o = getItem(position);

        if (o != null) {
            TextView client_name = v.findViewById(R.id.client_name);
            TextView date_order = v.findViewById(R.id.date_order);
            TextView total_price_order = v.findViewById(R.id.total_price_order);

            ConstraintLayout btn_item = v.findViewById(R.id.btn_item);
            btn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    intent = new Intent(getContext(), ConsultOrderActivityM.class);
                    intent.putExtra("order",o);
                    getContext().startActivity(intent);
                }
            });

            if (client_name != null ) {
                User u = AppDatabase.getInstance(getContext()).userDAO().get(o.id_user);
                Store s = AppDatabase.getInstance(getContext()).storeDAO().get(o.id_store);
                client_name.setText(u.first_name.charAt(0) + ". " + u.last_name);
            }

            if (date_order != null ) {
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date_str = dateFormat.format(o.date_order);
                date_order.setText(date_str);
            }

            if (total_price_order != null ) {
                float price = Math.round(AppDatabase.getInstance(getContext()).productInOrderDAO().getSumOrder(o.id_order) * 100.0f)/100.0f;
                total_price_order.setText(price+getContext().getResources().getString(R.string.euro));
            }
        }

        return v;
    }
}

