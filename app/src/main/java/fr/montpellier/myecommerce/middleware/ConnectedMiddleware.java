package fr.montpellier.myecommerce.middleware;

import android.content.Intent;

import fr.montpellier.myecommerce.activity.IdentificationActivity;
import fr.montpellier.myecommerce.activity.MiddlewareActivity;
import fr.montpellier.myecommerce.manager.ConnectionManager;


/**
 * Middleware permettant de rediriger l'application sur l'ecran d'identification
 * si l'utilisateur n'est pas connecté
 */
public class ConnectedMiddleware extends ConnectionMiddleware {
    public boolean verify_and_redirect(MiddlewareActivity activity){
        ConnectionManager cm = ConnectionManager.getInstance();
        boolean isConnected = cm.isConnected();
        if(!isConnected){
            redirect(activity);
            return true;
        }
        return false;
    }
    public void redirect(MiddlewareActivity activity){
        activity.finish();
        Intent intent = new Intent(activity, IdentificationActivity.class);
        activity.setIsRedirected(true);
        activity.startActivity(intent);
    }
}
