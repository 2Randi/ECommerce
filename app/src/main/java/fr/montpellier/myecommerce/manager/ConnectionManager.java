package fr.montpellier.myecommerce.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import fr.montpellier.myecommerce.db.entity.User;

public class ConnectionManager {
    private final static String TOKEN_PREF = "TOKEN_PREF";
    private final static String NAME_SHARED_PREFERENCE_IDENTIFICATION = "IDENTIFICATION";

    private User utilisateur;
    private boolean connected;
    private static ConnectionManager INSTANCE;

    private ConnectionManager() {
        utilisateur = null;
        connected = false;
    }

    public static ConnectionManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionManager();
        }
        return INSTANCE;
    }

    /**
     * Connecte l'utilisateur
     *
     * @param u Utilisateur
     */
    public void setConnected(User u) {
        utilisateur = u;
        connected = true;
    }

    /**
     * Getter de connected
     *
     * @return si l'utilisateur est connecté
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Getter de utilisateur
     *
     * @return utilisateur
     */
    public User getUtilisateur() {
        return utilisateur;
    }

    /**
     * Déconnecte l'utilisateur
     */
    public void disconnect() {
        utilisateur = null;
        connected = false;
    }

    /**
     * Obtient l'utilisateur stocké dans les préférences partagées et le connecte
     * @param act activité en cours
     * @return succès de l'action
     */
    public String getTokenUserFromPrefs(Activity act){
        SharedPreferences preferences = act.getSharedPreferences(NAME_SHARED_PREFERENCE_IDENTIFICATION, Activity.MODE_PRIVATE);
        return preferences.getString(ConnectionManager.TOKEN_PREF, null);
    }

    /**
     * Stocke l'utilisateur connecté pour pouvoir le récupérer ultérieurement (stocké dans les préférences partagées en mode privé)
     * @param act activité en cours
     */
    public void storeTokenUserInPrefs(Activity act){
        SharedPreferences preferences = act.getSharedPreferences(NAME_SHARED_PREFERENCE_IDENTIFICATION, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(utilisateur != null){
            editor.putString(ConnectionManager.TOKEN_PREF, utilisateur.token_connect);
            editor.apply();
        }
    }

    /**
     * Supprime l'utilisateur stocké dans les préférences partagées
     * @param act activité en cours
     */
    public void removeTokenUserFromPrefs(Activity act){
        SharedPreferences preferences = act.getSharedPreferences(NAME_SHARED_PREFERENCE_IDENTIFICATION, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(ConnectionManager.TOKEN_PREF);
        editor.apply();
    }
}
