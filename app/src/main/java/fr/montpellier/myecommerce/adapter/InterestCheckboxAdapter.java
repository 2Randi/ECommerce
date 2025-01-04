package fr.montpellier.myecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import fr.montpellier.myecomerce.R;
import fr.montpellier.myecommerce.InterestCheckbox;

import java.util.ArrayList;
import java.util.List;

public class InterestCheckboxAdapter extends ArrayAdapter<InterestCheckbox> {
    private Context mContext;
    private ArrayList<InterestCheckbox> interests;
    private boolean isFromView = false;

    public InterestCheckboxAdapter(Context context, int resource, List<InterestCheckbox> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.interests = (ArrayList<InterestCheckbox>) objects;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_interest_item, null);
            holder = new ViewHolder();
            holder.mTextView = convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            holder.mSpinnerItem = convertView.findViewById(R.id.item_spinner_interest);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(interests.get(position).getInterest().name);

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(interests.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);

            holder.mSpinnerItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.mCheckBox.setChecked(!holder.mCheckBox.isChecked());
                }
            });
        }

        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isFromView) {
                    interests.get(position).setSelected(isChecked);
                }
            }
        });
        return convertView;
    }

    public List<InterestCheckbox> getInterests(){
        return interests;
    }

    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
        private ConstraintLayout mSpinnerItem;
    }
}