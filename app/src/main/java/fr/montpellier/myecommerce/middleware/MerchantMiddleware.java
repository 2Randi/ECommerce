package fr.montpellier.myecommerce.middleware;

import android.content.Intent;

import fr.montpellier.myecommerce.activity.MiddlewareActivity;
import fr.montpellier.myecommerce.activity.fournisseur.MenuActivityM;
import fr.montpellier.myecommerce.manager.ConnectionManager;

/**
 * Middleware permettant de rediriger l'application sur l'ecran d'identification
 * si l'utilisateur est un client
 */
public class MerchantMiddleware extends TypeUserMiddleware {
    public boolean verify_and_redirect(MiddlewareActivity activity){
        ConnectionManager cm = ConnectionManager.getInstance();
        if(!cm.getUtilisateur().is_merchant){
            redirect(activity);
            return true;
        }
        return false;
    }
    public void redirect(MiddlewareActivity activity){
        activity.finish();
        Intent intent = new Intent(activity, MenuActivityM.class);
        activity.setIsRedirected(true);
        activity.startActivity(intent);
    }
}
