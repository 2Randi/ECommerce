package fr.montpellier.myecommerce.activity;

import fr.montpellier.myecommerce.middleware.NotConnectedMiddleware;

public  class NotConnectedActivity extends MiddlewareActivity {
    public NotConnectedActivity(){
        super();
        connection_middleware = new NotConnectedMiddleware();
    }
}