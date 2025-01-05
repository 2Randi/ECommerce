package fr.montpellier.myecommerce.activity;

import fr.montpellier.myecommerce.middleware.ClientMiddleware;

public  class ConnectedClientActivity extends ConnectedActivity {
    public ConnectedClientActivity(){
        super();
        type_user_middleware = new ClientMiddleware();
    }
}