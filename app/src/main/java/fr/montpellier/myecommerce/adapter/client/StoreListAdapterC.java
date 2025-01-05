package fr.montpellier.myecommerce.adapter.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import fr.montpellier.myecommerce.R;
import fr.montpellier.myecommerce.activity.client.ConsultStoreActivityC;
import fr.montpellier.myecommerce.db.entity.Store;

public class StoreListAdapterC extends ArrayAdapter<Store> {
    private int resourceLayout;

    public StoreListAdapterC(Context c, List<Store> stores) {
        super(c, R.layout.store_item, stores);
        this.resourceLayout = R.layout.store_item;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(resourceLayout, null);
        }

        Store s = getItem(position);

        if (s != null) {
            ImageView image_store = v.findViewById(R.id.image_store);
            TextView name_store = v.findViewById(R.id.name_store);
            ConstraintLayout btn_item = v.findViewById(R.id.btn_item);
            btn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    Context context = getContext();
                    intent = new Intent(context, ConsultStoreActivityC.class);
                    intent.putExtra("store",s);
                    context.startActivity(intent);
                }
            });

            if (image_store != null ) {
                Bitmap bmp;
                if ( s.img_store != null) {
                    bmp = BitmapFactory.decodeByteArray(s.img_store, 0, s.img_store.length);
                } else {
                    bmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.boutique);
                }
                image_store.setImageBitmap(bmp);
            }

            if (name_store != null) {
                name_store.setText(s.name);
            }
        }

        return v;
    }
}
