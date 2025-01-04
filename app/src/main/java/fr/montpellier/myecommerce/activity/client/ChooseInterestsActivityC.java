package fr.montpellier.myecommerce.activity.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.montpellier.myecomerce.R;
import fr.montpellier.myecommerce.InterestCheckbox;
import fr.montpellier.myecommerce.activity.ConnectedClientActivity;
import fr.montpellier.myecommerce.adapter.InterestCheckboxAdapter;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Interest;
import fr.montpellier.myecommerce.db.entity.User;
import fr.montpellier.myecommerce.db.entity.UserHasInterest;
import fr.montpellier.myecommerce.manager.ConnectionManager;

public class ChooseInterestsActivityC extends ConnectedClientActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(is_redirected) return;
        setContentView(R.layout.c_choose_interests);
        List<Interest> interests = AppDatabase.getInstance(this).interestDAO().getAll();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Button validate = findViewById(R.id.validate);

        User user = ConnectionManager.getInstance().getUtilisateur();

        ArrayList<InterestCheckbox> list_interests = new ArrayList<>();

        int size_interests = interests.size();
        for (int i = 0 ; i < size_interests;i++) {
            InterestCheckbox icb = new InterestCheckbox(interests.get(i));
            UserHasInterest uhi = AppDatabase.getInstance(this).userHasInterestDAO().getWithUserAndInterest(user.id_user,interests.get(i).id_interest);
            icb.setSelected(uhi!=null);
            list_interests.add(icb);
        }

        InterestCheckbox icb = new InterestCheckbox(new Interest("Interests"));
        icb.setSelected(false);
        list_interests.add(0,icb);

        InterestCheckboxAdapter adapter = new InterestCheckboxAdapter(this, 0,list_interests);
        spinner.setAdapter(adapter);


        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<InterestCheckbox> icbs = adapter.getInterests();

                AppDatabase.getInstance(ChooseInterestsActivityC.this).userHasInterestDAO().clearForUser(user.id_user);

                int size_interests = icbs.size();
                for(int i = 0; i < size_interests;i++){
                    if(icbs.get(i).isSelected()){
                        UserHasInterest uhi = new UserHasInterest(icbs.get(i).getInterest().id_interest,user.id_user);
                        AppDatabase.getInstance(ChooseInterestsActivityC.this).userHasInterestDAO().insert(uhi);
                    }
                }

                finish();
                Toast.makeText(ChooseInterestsActivityC.this,"Interests have been saved",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

