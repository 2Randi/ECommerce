package fr.montpellier.myecommerce.middleware;

import android.annotation.SuppressLint;
import android.content.Intent;

import fr.montpellier.myecommerce.activity.fournisseur.CreateStoreActivityM;
import fr.montpellier.myecommerce.activity.MiddlewareActivity;
import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Store;
import fr.montpellier.myecommerce.db.entity.User;
import fr.montpellier.myecommerce.manager.ConnectionManager;


/**
 * Middleware permettant de rediriger l'application sur la page de creation de store
 * si l'utilisateur marchand n'a pas de store
 */
public class MerchantWithStoreMiddleWare extends StoreMiddleWare {
    public boolean verify_and_redirect(MiddlewareActivity activity){
        ConnectionManager cm = ConnectionManager.getInstance();

        User user = cm.getUtilisateur();
        Store store = AppDatabase.getInstance(activity).storeDAO().get(user.id_user);
        if(store==null){
            redirect(activity);
            return true;
        }
        return false;
    }
    @SuppressLint("LongLogTag")
    public void redirect(MiddlewareActivity activity){
        activity.finish();
        Intent intent = new Intent(activity, CreateStoreActivityM.class);
        activity.setIsRedirected(true);
        activity.startActivity(intent);
    }
}
