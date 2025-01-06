package fr.montpellier.myecommerce.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fr.montpellier.myecommerce.R;
import fr.montpellier.myecommerce.IcoStoreApp;
import fr.montpellier.myecommerce.db.AppDatabase;


public class IcoStoreActivity  extends AppCompatActivity  {
    private ProgressDialog progDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        db.init();

        IcoStoreApp.setCurrentAct(this);

    }

    public void showProgress(){
        progDialog = ProgressDialog.show( this, null, null, false, false );
        progDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT));
        progDialog.setContentView(R.layout.progress_bar );
    }

    public void hideProgress(){
        progDialog.dismiss();
    }


}

