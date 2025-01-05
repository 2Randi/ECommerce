package fr.montpellier.myecommerce;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import fr.montpellier.myecommerce.activity.IcoStoreActivity;

public class IcoStoreApp extends Application implements LifecycleObserver {

    private static Application ICOAPP;

    @SuppressLint("StaticFieldLeak")
    private static IcoStoreActivity currentAct;

    public static Application getApplication() {
        return ICOAPP;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public static IcoStoreActivity getCurrentAct() {
        return currentAct;
    }

    public static void setCurrentAct(IcoStoreActivity cA) {
        currentAct = cA;
    }

    public static boolean FOREGROUND;

    @Override
    public void onCreate() {
        super.onCreate();
        FOREGROUND = true;
        ICOAPP = this;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public static void onAppBackgrounded() {
        //App in background
        FOREGROUND = false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public static void onAppForegrounded() {
        // App in foreground
        FOREGROUND = true;
    }

    public static boolean isForeground() {
        return FOREGROUND;
    }
}
