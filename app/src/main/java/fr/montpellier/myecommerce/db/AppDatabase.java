package fr.montpellier.myecommerce.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.ArrayList;

import fr.montpellier.myecommerce.db.dao.CategoryDAO;
import fr.montpellier.myecommerce.db.dao.InterestDAO;
import fr.montpellier.myecommerce.db.dao.InterestForCategoryDAO;
import fr.montpellier.myecommerce.db.dao.OfferDAO;
import fr.montpellier.myecommerce.db.dao.OrderDAO;
import fr.montpellier.myecommerce.db.dao.ProductDAO;
import fr.montpellier.myecommerce.db.dao.ProductInBasketDAO;
import fr.montpellier.myecommerce.db.dao.ProductInOrderDAO;
import fr.montpellier.myecommerce.db.dao.StoreDAO;
import fr.montpellier.myecommerce.db.dao.UserDAO;
import fr.montpellier.myecommerce.db.dao.UserHasInterestDAO;
import fr.montpellier.myecommerce.db.entity.Category;
import fr.montpellier.myecommerce.db.entity.ProductInBasket;
import fr.montpellier.myecommerce.db.entity.Interest;
import fr.montpellier.myecommerce.db.entity.InterestForCategory;
import fr.montpellier.myecommerce.db.entity.Offer;
import fr.montpellier.myecommerce.db.entity.Order;
import fr.montpellier.myecommerce.db.entity.Product;
import fr.montpellier.myecommerce.db.entity.ProductInOrder;
import fr.montpellier.myecommerce.db.entity.Store;
import fr.montpellier.myecommerce.db.entity.User;
import fr.montpellier.myecommerce.db.entity.UserHasInterest;
import fr.montpellier.myecommerce.manager.TemplateManager;
import fr.montpellier.myecommerce.utils.HashUtil;

/**
 * Classe Singleton qui gère la base de données dans toute l'application
 */

@Database(entities = {
        User.class,
        Product.class,
        Store.class,
        Category.class,
        Offer.class,
        Interest.class,
        UserHasInterest.class,
        InterestForCategory.class,
        ProductInBasket.class,
        Order.class,
        ProductInOrder.class
},version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE = null;
    public abstract UserDAO userDAO();
    public abstract ProductDAO productDAO();
    public abstract StoreDAO storeDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract OfferDAO offerDAO();
    public abstract InterestDAO interestDAO();
    public abstract UserHasInterestDAO userHasInterestDAO();
    public abstract InterestForCategoryDAO interestForCategoryDAO();
    public abstract ProductInBasketDAO productInBasketDAO();
    public abstract OrderDAO orderDAO();
    public abstract ProductInOrderDAO productInOrderDAO();

    public static AppDatabase getInstance(Context applicationContext) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(applicationContext,AppDatabase.class,"db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    public void clear(){
        offerDAO().clear();
        userHasInterestDAO().clear();
        interestForCategoryDAO().clear();
        interestDAO().clear();
        productInOrderDAO().clear();
        orderDAO().clear();
        productInBasketDAO().clear();
        productDAO().clear();
        categoryDAO().clear();
        storeDAO().clear();
        userDAO().clear();
    }


    public void fillUsers(){
        UserDAO userDAO = userDAO();
        ArrayList<User> users = new ArrayList<>();

        //Fournisseur
        users.add(new User("decathlon@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));
        users.add(new User("rajaofetra@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));
        users.add(new User("dago@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));

        //Client
        users.add(new User("tsiory@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),false,"Tsiory","RANDRIAMISAINA"));
        users.add(new User("andry@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),false,"Andry","RANDRIAMISAINA"));

        userDAO.insertAll(users);
    }



    public void fillStores(){
        StoreDAO storeDAO = storeDAO();
        ArrayList<Store> stores = new ArrayList<>();

        stores.add(new Store(userDAO().get("decathlon@gmail.com").id_user,"Decathlon", null));
        stores.add(new Store(userDAO().get("rajaofetrav@gmail.com").id_user,"Rajaofetra shop", null));
        stores.add(new Store(userDAO().get("dago@gmail.com").id_user,"Dago Urban Wear", null));



        storeDAO.insertAll(stores);
        fillUsers();
    }

    public void fillInterests(){
        InterestDAO interestDAO = interestDAO();
        ArrayList<Interest> interests = new ArrayList<>();
        // Intêret

        interests.add(new Interest("Formal Wear"));
        interests.add(new Interest("Extreme sports"));
        interests.add(new Interest("Classical sports"));
        interests.add(new Interest("Classical music"));
        interests.add(new Interest("Capture tech"));
        interests.add(new Interest("Entertainment tech"));

        interestDAO.insertAll(interests);
    }

    public void fillUsersHaveInterests(){
        UserHasInterestDAO userHasInterestDAO = userHasInterestDAO();
        ArrayList<UserHasInterest> uhis = new ArrayList<>();

        uhis.add(new UserHasInterest(interestDAO().get("Extreme sports").id_interest,userDAO().get("decathlon@gmail.com").id_user));

        userHasInterestDAO.insertAll(uhis);
    }

    public void fillCategories(){
        TemplateManager tm = TemplateManager.getInstance();

        tm.generateClassyCostumeTemplate(this,userDAO().get("decathlon@gmail.com").id_user);
        tm.generateCasualOutfitTemplate(this,userDAO().get("rajaofetra@gmail.com").id_user);
        tm.generateBurberryCostumeTemplate(this,userDAO().get("dago@gmail.com").id_user);


    }
    public void fillProducts(){
        ProductDAO productDAO = productDAO();
        ArrayList<Product> products = new ArrayList<>();

        /** generateMusicTemplate */
        products.add(new Product("Fender Player Stratocaster PF Fiesta Red","Electric Guitar, Rightist, Maple, 3 mics (Alnico V)", 769.0f, categoryDAO().getWithStoreAndName(userDAO().get("rajaofetra@gmail.com").id_user,"Guitars").id_category,null));
        products.add(new Product("Yamaha PSR 3000","Keyboard, 61 velocity-sensitive keys, 820 sounds, 290 models, 64 voice polyphony", 377.0f, categoryDAO().getWithStoreAndName(userDAO().get("rajaofetra@gmail.com").id_user,"Keys").id_category,null));
        products.add(new Product("Thomman JAS500Q","Saxophone, Body yellow brass, High F# key, Gold lacquer finish, Key Eb", 860.0f, categoryDAO().getWithStoreAndName(userDAO().get("rajaofetra@gmail.com").id_user,"Winds").id_category,null));

        /** generateTechnologyTemplate */
        products.add(new Product("ASUS Zephyrus M16","Laptop, i7 11800H, RTX 3060, 16GB", 1999.0f, categoryDAO().getWithStoreAndName(userDAO().get("rajaofetra@gmail.com").id_user,"Computers").id_category,null));
        products.add(new Product("Apple iPhone 16 Pro","Black, 256GB, Apple A15, 5G", 979.0f, categoryDAO().getWithStoreAndName(userDAO().get("rajaofetra@gmail.com").id_user,"Cell-phones").id_category,null));
        products.add(new Product("Apple Watch Series 7","GPS, 41mm Starlight Aluminum Case with Starlight Sport Band, Regular", 389.0f, categoryDAO().getWithStoreAndName(userDAO().get("rajaofetra@gmail.com").id_user,"Wearables").id_category,null));


        /** generateClothingTemplate  */
        products.add(new Product("Sweatshirt","Grey, 90% Cotton 10% Polyester, M", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("dago@gmail.com").id_user,"Sweaters").id_category,null));
        products.add(new Product("Cocktail Dress","Black, 100% Polyester, 38", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("dago@gmail.com").id_user,"Dresses").id_category,null));
        products.add(new Product("Vintage Jeans","Bleached, 98% Cotton 2% Elastane, 40", 39.99f, categoryDAO().getWithStoreAndName(userDAO().get("dago@gmail.com").id_user,"Jeans").id_category,null));
        products.add(new Product("Flare Trousers","Purple, 90% Cotton 10% Polyester, 40", 24.99f, categoryDAO().getWithStoreAndName(userDAO().get("dago@gmail.com").id_user,"Pants").id_category,null));

        /**  generateSportTemplate */
        products.add(new Product("Running Shoes","Grey/White, 43", 19.99f, categoryDAO().getWithStoreAndName(userDAO().get("decathlon@gmail.com").id_user,"Athletics").id_category,null));
        products.add(new Product("Ball","World Cup 2022", 9.99f, categoryDAO().getWithStoreAndName(userDAO().get("decathlon@gmail.com").id_user,"Football").id_category,null));
        products.add(new Product("Swim Goggles","Blue", 9.99f, categoryDAO().getWithStoreAndName(userDAO().get("decathlon@gmail.com").id_user,"Swimming").id_category,null));
        products.add(new Product("Tennis Racket","Standard", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("decathlon@gmail.com").id_user,"Tennis").id_category,null));

        productDAO.insertAll(products);
    }


    public void init(){
        if (getUserCount() == 0) {
            // Efface toutes les tables et remplit la base de données avec des exemples
            clear();
            fillUsers();
            fillStores();
            fillInterests();
            fillUsersHaveInterests();
            fillCategories();
            fillProducts();
        }
    }

    // Verifier si la base de donnée est vide
    private int getUserCount() {
        //  return userDAO().getTotalUsers() == 0;
        return userDAO().getTotalUsers();

    }


}