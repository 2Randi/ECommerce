package fr.montpellier.myecommerce.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.regex.Pattern;

import fr.montpellier.myecommerce.R;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.dao.UserDAO;
import fr.montpellier.myecommerce.db.entity.User;
import fr.montpellier.myecommerce.manager.ConnectionManager;
import fr.montpellier.myecommerce.utils.HashUtil;

// Classe principale pour l'activité IdentificationActivity
public class IdentificationActivity extends NotConnectedActivity {

    // Variables membres pour différents éléments UI
    private boolean is_sign_up;
    private boolean is_merchant;
    private TextView tv_title;
    private EditText et_email;
    private EditText et_pwd;
    private EditText et_pwd_confirm;
    private EditText et_first_name;
    private EditText et_last_name;
    private Button but_submit;
    private Button but_toggle_identification;
    private CheckBox checkbox_stay_connected;
    private CheckBox checkbox_is_merchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenir l'instance de la base de données
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        // Démarrer l'initialisation de la base de données dans un thread séparé
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.init();
            }
        }).start();

        // Vérifier si une redirection a eu lieu, si oui, retourner
        if(is_redirected) return;
        // Vérifier et gérer l'état de la connexion
        ConnectionManager cm =ConnectionManager.getInstance();
        if(!cm.isConnected()){
            String token = cm.getTokenUserFromPrefs(this);
            if(token != null){
                User user = AppDatabase.getInstance(this).userDAO().getWithToken(token);
                if(user != null){
                    cm.setConnected(user);
                    connection_middleware.redirect(this);
                }
            }
        }


        //We begin on the signin page as a customer
        is_sign_up = false;
        is_merchant = false;

        //Put the right layout
        this.setContentView(R.layout.identification);

        tv_title = findViewById(R.id.title_identification);
        et_email = findViewById(R.id.input_email_identification);
        et_pwd = findViewById(R.id.input_pwd_identification);
        et_pwd_confirm = findViewById(R.id.input_pwd_confirm_identification);
        et_first_name = findViewById(R.id.input_first_name);
        et_last_name = findViewById(R.id.find_stores);
        but_submit = findViewById(R.id.submit_identification);
        but_toggle_identification = findViewById(R.id.toggle_identification);
        checkbox_stay_connected = findViewById(R.id.checkbox_stay_connected_identification);
        checkbox_is_merchant = findViewById(R.id.checkbox_is_merchant_identification);

        //Initialize text and hint of each component
        initializeFormidentification();

    }

    private void actualizeFormIdentification() {
        if (is_sign_up) {
            tv_title.setText(R.string.register);
            but_toggle_identification.setText(R.string.toggle_identification_from_signup);
            et_pwd.setText("");
            et_pwd_confirm.setText("");
            et_pwd_confirm.setVisibility(View.VISIBLE);
            checkbox_is_merchant.setVisibility(View.VISIBLE);
        } else {
            checkbox_is_merchant.setChecked(false);
            checkbox_is_merchant.setVisibility(View.GONE);
            tv_title.setText(R.string.login);
            but_toggle_identification.setText(R.string.toggle_identification_from_signin);
            et_pwd.setText("");
            et_pwd_confirm.setVisibility(View.GONE);
        }
        if(!is_sign_up){ // Pour cacher les textinput
            et_first_name.setVisibility(View.GONE);
            et_last_name.setVisibility(View.GONE);
        } else {
            et_first_name.setVisibility(View.VISIBLE);
            et_last_name.setVisibility(View.VISIBLE);
        }
        but_submit.setText(R.string.validate);
    }

    private void initializeFormidentification() {
        et_email.setHint(R.string.mail);
        et_pwd.setHint(R.string.password);
        et_pwd_confirm.setHint(R.string.password2);


        but_toggle_identification.setOnClickListener(v -> {
            is_sign_up = !is_sign_up;
            actualizeFormIdentification();
        });

        checkbox_is_merchant.setOnClickListener(v -> {
            is_merchant = !is_merchant;
            actualizeFormIdentification();
        });



        but_submit.setOnClickListener(v -> submit());

        actualizeFormIdentification();
    }



    // Méthode pour gérer la soumission du formulaire
    private void submit() {

        // Initialisation de la base de données
        AppDatabase.getInstance(getApplicationContext()).init();

        //Get the value of each input
        String email = et_email.getText().toString();

        Pattern regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");

        if(!regex.matcher(email).matches()){
            Toast.makeText(this,"Email not valid",Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = et_pwd.getText().toString();

        if(pwd.length() < 6){
            Toast.makeText(this,"Password must be composed of at least 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean stay_connected = checkbox_stay_connected.isChecked();

        UserDAO user_DAO = AppDatabase.getInstance(this).userDAO();

        boolean connect = false;
        User user = user_DAO.get(email);
        if(is_sign_up){//REGISTER
            if(user == null){
                String pwd_confirm = et_pwd_confirm.getText().toString();
                if(pwd.equals(pwd_confirm)){
                    String first_name = "";
                    String last_name = "";
                    if(!is_merchant){
                        first_name = et_first_name.getText().toString();
                        last_name = et_last_name.getText().toString();
                        if(first_name.length() == 0){
                            Toast.makeText(this,"Prénom est vide",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(last_name.length() == 0){
                            Toast.makeText(this,"Nom est vide",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    user = new User(email, HashUtil.getSHA256SecurePassword(pwd,""), is_merchant, first_name, last_name);
                    user.id_user = (int) user_DAO.insert(user);
                    connect = true;
                } else {
                    Toast.makeText(this,"Les mots de pass ne sont pas identique",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this,"Ce compte est déja existant",Toast.LENGTH_SHORT).show();
                return;
            }
        } else {//LOGIN
            if(user != null){
                if(HashUtil.getSHA256SecurePassword(pwd,"").equals(user.hash_pwd)){
                    connect = true;
                } else {
                    Toast.makeText(this,"Mot de pass incorrecte",Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(this,"Compte inexistant",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(connect){
            ConnectionManager cm = ConnectionManager.getInstance();
            cm.setConnected(user);
            if(stay_connected){
                cm.storeTokenUserInPrefs(this);
            } else {
                cm.removeTokenUserFromPrefs(this);
            }
            connection_middleware.redirect(this);
        }

    }
}