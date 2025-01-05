package fr.montpellier.myecommerce.activity;


import fr.montpellier.myecommerce.middleware.MerchantWithStoreMiddleWare;

public  class ConnectedMerchantWithStoreActivity extends ConnectedMerchantActivity {
    public ConnectedMerchantWithStoreActivity(){
        super();
        store_middleWare = new MerchantWithStoreMiddleWare();
    }
}
