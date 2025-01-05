package fr.montpellier.myecommerce.activity;

import fr.montpellier.myecommerce.middleware.MerchantWithoutStoreMiddleware;

public  class ConnectedMerchantWithoutStoreActivity extends ConnectedMerchantActivity {
    public ConnectedMerchantWithoutStoreActivity(){
        super();
        store_middleWare = new MerchantWithoutStoreMiddleware();
    }
}