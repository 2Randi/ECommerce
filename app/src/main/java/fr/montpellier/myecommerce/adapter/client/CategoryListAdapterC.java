package fr.montpellier.myecommerce.adapter.client;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

import fr.montpellier.myecomerce.R;
import fr.montpellier.myecommerce.activity.client.ConsultListProductsActivityC;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Category;
import fr.montpellier.myecommerce.db.entity.Interest;
import fr.montpellier.myecommerce.db.entity.Product;
import fr.montpellier.myecommerce.db.entity.Store;

public class CategoryListAdapterC extends ArrayAdapter<Category> {
    private int resourceLayout;

    public CategoryListAdapterC(Context c, List<Category> categories) {
        super(c, R.layout.c_category_item, categories);
        this.resourceLayout = R.layout.c_category_item;
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

        Category c = getItem(position);

        if (c != null) {
            TextView name_category = v.findViewById(R.id.category_name);
            TextView interests_category = v.findViewById(R.id.interests_category);

            ConstraintLayout btn_item = v.findViewById(R.id.btn_item);
            btn_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    Context context = getContext();
                    Resources resources = context.getResources();
                    intent = new Intent(context, ConsultListProductsActivityC.class);
                    Store store =  AppDatabase.getInstance(context).storeDAO().get(c.id_store);
                    intent.putExtra("title",resources.getString(R.string.products_of)+c.name+resources.getString(R.string.space_of_space)+store.name);
                    ArrayList<Product> products = (ArrayList<Product>) AppDatabase.getInstance(context).categoryDAO().getProductsOfCategory(c.id_category);
                    intent.putExtra("products",products);
                    context.startActivity(intent);
                }
            });

            if (name_category != null ) {
                name_category.setText(c.name);
            }

            if (interests_category != null ) {
                List<Interest> interests = AppDatabase.getInstance(getContext()).interestForCategoryDAO().getInterestsWithCategory(c.id_category);
                StringBuilder interest_str_builder = new StringBuilder();
                int size_interests = interests.size();
                if(size_interests>0){
                    interests_category.setVisibility(View.VISIBLE);
                    for(int i = 0 ; i < size_interests;i++){
                        if(i!=0) interest_str_builder.append(" | ");
                        interest_str_builder.append(interests.get(i).name);
                    }
                    interests_category.setText(interest_str_builder.toString());
                } else {
                    interests_category.setVisibility(View.GONE);
                }
            }
        }

        return v;
    }
}
