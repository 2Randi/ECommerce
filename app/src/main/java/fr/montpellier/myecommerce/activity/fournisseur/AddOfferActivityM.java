package fr.montpellier.myecommerce.activity.fournisseur;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.montpellier.myecommerce.R;
import fr.montpellier.myecommerce.activity.ConnectedMerchantWithStoreActivity;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Offer;
import fr.montpellier.myecommerce.db.entity.Product;

public class AddOfferActivityM extends ConnectedMerchantWithStoreActivity {
    private Product product;
    private TextView date_start_offer;
    private TextView date_end_offer;
    private Calendar calendar;
    private Date date_start;
    private Date date_end;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(is_redirected) return;
        setContentView(R.layout.m_add_offer);
        Button validate = findViewById(R.id.validate);

        product = (Product) getIntent().getSerializableExtra("product");

        TextView title = findViewById(R.id.title_add_offer);
        TextView product_name = findViewById(R.id.product_name);
        TextView product_price = findViewById(R.id.product_price);
        EditText new_price = findViewById(R.id.new_price);
        date_start_offer = findViewById(R.id.date_start_offer);
        date_end_offer = findViewById(R.id.date_end_offer);

        date_start = null;
        date_end = null;

        title.setText(getResources().getString(R.string.add_special_offer)+getResources().getString(R.string.space_to_space) + product.name);

        product_name.setText(product.name);
        product_price.setText(String.valueOf(product.price)+ getResources().getString(R.string.euro));

        calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date_picker_listener_start = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                update_date_start();
            }
        };

        DatePickerDialog.OnDateSetListener date_picker_listener_end = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                update_date_end();
            }
        };

        date_start_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddOfferActivityM.this,date_picker_listener_start,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date_end_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddOfferActivityM.this,date_picker_listener_end,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_price_str = new_price.getText().toString();
                float new_price_nb = Float.parseFloat(new_price_str);

                if(new_price_str.isEmpty()) {
                    Toast.makeText(AddOfferActivityM.this,"Please enter a valid price",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(date_start == null) {
                    Toast.makeText(AddOfferActivityM.this,"Please enter a valid date of start",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(date_end == null) {
                    Toast.makeText(AddOfferActivityM.this,"Please enter a valid date of end",Toast.LENGTH_SHORT).show();
                    return;
                }

                Offer offer = new Offer(product.id_product,new_price_nb,date_start,date_end);
                AppDatabase.getInstance(AddOfferActivityM.this).offerDAO().insert(offer);

                finish();
                Toast.makeText(AddOfferActivityM.this,"Your offer have been saved",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void update_date_start(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRANCE);
        date_start = calendar.getTime();
        date_start_offer.setText(dateFormat.format(calendar.getTime()));
    }

    private void update_date_end(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRANCE);
        date_end = calendar.getTime();
        date_end_offer.setText(dateFormat.format(calendar.getTime()));
    }
}
