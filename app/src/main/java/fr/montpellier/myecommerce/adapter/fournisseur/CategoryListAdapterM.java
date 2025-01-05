package fr.montpellier.myecommerce.adapter.fournisseur;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;


import java.util.ArrayList;
import java.util.List;

import fr.montpellier.myecommerce.R;
import fr.montpellier.myecommerce.activity.fournisseur.AddProductActivityM;
import fr.montpellier.myecommerce.activity.fournisseur.ConsultListProductsActivityM;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Category;
import fr.montpellier.myecommerce.db.entity.Interest;
import fr.montpellier.myecommerce.db.entity.Product;

public class CategoryListAdapterM extends ArrayAdapter<Category> {
    private int resourceLayout;

    public CategoryListAdapterM(Context c, List<Category> categories) {
        super(c, R.layout.m_category_item, categories);
        this.resourceLayout = R.layout.m_category_item;
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

        Category c = getItem(position);

        if (c != null) {
            TextView name_category = v.findViewById(R.id.category_name);
            TextView interests_category = v.findViewById(R.id.interests_category);
            Button add_product = v.findViewById(R.id.add_product);

            add_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    Context context = getContext();
                    intent = new Intent(context, AddProductActivityM.class);
                    intent.putExtra("category",c);
                    context.startActivity(intent);
                }
            });

            ConstraintLayout btn_item = v.findViewById(R.id.btn_item);
            btn_item.setOnClickListener(view -> {
                Intent intent;
                Context context = getContext();
                intent = new Intent(context, ConsultListProductsActivityM.class);
                intent.putExtra("title", c.name);
                intent.putExtra("has_add_product", true);
                intent.putExtra("category", c);
                ArrayList<Product> products = (ArrayList<Product>) AppDatabase.getInstance(context).categoryDAO().getProductsOfCategory(c.id_category);
                intent.putExtra("products",products);
                context.startActivity(intent);
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
