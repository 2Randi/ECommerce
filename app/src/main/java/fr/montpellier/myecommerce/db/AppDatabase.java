package fr.montpellier.myecommerce.db;

import android.content.Context;

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

        // Fournisseur Costumes
        users.add(new User("armani@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));
        users.add(new User("boss@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));
        users.add(new User("burberry@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));

        users.add(new User("celio@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));
        users.add(new User("intersport@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));
        users.add(new User("leclerc@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));
        users.add(new User("loreal@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));
        users.add(new User("pioneer@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));
        users.add(new User("boulanger@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),true,"",""));



        //Client

        users.add(new User("boualam@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),false,"Karim","BOUALAM"));
        users.add(new User("ighachan@gmail.com", HashUtil.getSHA256SecurePassword("123456",""),false,"M'hamed","IGHACHANE"));

        userDAO.insertAll(users);
    }

    public void fillStores(){
        StoreDAO storeDAO = storeDAO();
        ArrayList<Store> stores = new ArrayList<>();

        stores.add(new Store(userDAO().get("armani@gmail.com").id_user,"Emporio Armani", null));
        stores.add(new Store(userDAO().get("boss@gmail.com").id_user,"Burberry", null));
        stores.add(new Store(userDAO().get("Burberry@gmail.com").id_user,"Hugo Boss", null));

        stores.add(new Store(userDAO().get("celio@gmail.com").id_user,"Celio", null));
        stores.add(new Store(userDAO().get("intersport@gmail.com").id_user,"InterSport", null));
        stores.add(new Store(userDAO().get("leclerc@gmail.com").id_user,"Leclerc", null));
        stores.add(new Store(userDAO().get("loreal@gmail.com").id_user,"Loreal", null));
        stores.add(new Store(userDAO().get("pioneer@gmail.com").id_user,"Pioneer", null));
        stores.add(new Store(userDAO().get("boulanger@gmail.com").id_user,"Boulanger", null));



        storeDAO.insertAll(stores);
    }

    public void fillInterests(){
        InterestDAO interestDAO = interestDAO();
        ArrayList<Interest> interests = new ArrayList<>();
        // Intêret

        // Costumes
        interests.add(new Interest("Formal Wear"));
        interests.add(new Interest("Casual Wear"));
        interests.add(new Interest("Burberry Wear"));

        interests.add(new Interest("Extreme sports"));
        interests.add(new Interest("Classical sports"));
        interests.add(new Interest("Industrial food"));
        interests.add(new Interest("Natural food"));
        interests.add(new Interest("Skin wellness"));
        interests.add(new Interest("Hair wellness"));
        interests.add(new Interest("Urban music"));
        interests.add(new Interest("Classical music"));
        interests.add(new Interest("Capture tech"));
        interests.add(new Interest("Entertainment tech"));
        interests.add(new Interest("Top garments"));
        interests.add(new Interest("Bottom garments"));


        interestDAO.insertAll(interests);
    }

    public void fillUsersHaveInterests(){
        UserHasInterestDAO userHasInterestDAO = userHasInterestDAO();
        ArrayList<UserHasInterest> uhis = new ArrayList<>();

        uhis.add(new UserHasInterest(interestDAO().get("Extreme sports").id_interest,userDAO().get("boualam@gmail.com").id_user));
        uhis.add(new UserHasInterest(interestDAO().get("Classical sports").id_interest,userDAO().get("boualam@gmail.com").id_user));

        userHasInterestDAO.insertAll(uhis);
    }

    public void fillCategories(){
        TemplateManager tm = TemplateManager.getInstance();

        tm.generateClassyCostumeTemplate(this,userDAO().get("armani@gmail.com").id_user);
        tm.generateCasualOutfitTemplate(this,userDAO().get("boss@gmail.com").id_user);
        tm.generateBurberryCostumeTemplate(this,userDAO().get("Burberry@gmail.com").id_user);

        tm.generateClothingTemplate(this,userDAO().get("celio@gmail.com").id_user);
        tm.generateSportTemplate(this,userDAO().get("intersport@gmail.com").id_user);
        tm.generateMusicTemplate(this,userDAO().get("pioneer@gmail.com").id_user);
        tm.generateTechnologyTemplate(this,userDAO().get("boulanger@gmail.com").id_user);
        tm.generateFoodTemplate(this,userDAO().get("leclerc@gmail.com").id_user);
        tm.generateSelfcareTemplate(this,userDAO().get("loreal@gmail.com").id_user);


    }
    public void fillProducts(){
        ProductDAO productDAO = productDAO();
        ArrayList<Product> products = new ArrayList<>();


        /** generateFoodTemplate */
        products.add(new Product("Plain Bread","300g", 0.87f, categoryDAO().getWithStoreAndName(userDAO().get("leclerc@gmail.com").id_user,"Bakery").id_category,null));
        products.add(new Product("Croissants","6 pieces", 2.49f, categoryDAO().getWithStoreAndName(userDAO().get("leclerc@gmail.com").id_user,"Breakfast").id_category,null));
        products.add(new Product("Rib-eye Steak","900g", 24.90f, categoryDAO().getWithStoreAndName(userDAO().get("leclerc@gmail.com").id_user,"Meat").id_category,null));
        products.add(new Product("Apples","Granny Smith, 1kg", 2.10f, categoryDAO().getWithStoreAndName(userDAO().get("leclerc@gmail.com").id_user,"Fruits & Vegetables").id_category,null));
        products.add(new Product("Fresh Milk","1L", 0.80f, categoryDAO().getWithStoreAndName(userDAO().get("leclerc@gmail.com").id_user,"Dairy").id_category,null));
        products.add(new Product("Canned Tuna","200g", 1.75f, categoryDAO().getWithStoreAndName(userDAO().get("leclerc@gmail.com").id_user,"Pantry").id_category,null));
        products.add(new Product("Haribo Dragibus","150g", 1.37f, categoryDAO().getWithStoreAndName(userDAO().get("leclerc@gmail.com").id_user,"Candy").id_category,null));
        products.add(new Product("Lipton Ice Tea","1.5L", 1.89f, categoryDAO().getWithStoreAndName(userDAO().get("leclerc@gmail.com").id_user,"Beverage").id_category,null));


        /** generateSelfcareTemplate */
        products.add(new Product("Natural Soap","Lemon & Parsimon, 100g", 3.40f, categoryDAO().getWithStoreAndName(userDAO().get("loreal@gmail.com").id_user,"Bath & Body").id_category,null));
        products.add(new Product("Blue Hair Dye","Tumblr certified", 5.79f, categoryDAO().getWithStoreAndName(userDAO().get("loreal@gmail.com").id_user,"Hair care").id_category,null));
        products.add(new Product("Electric Trimmer","7 different caps, 25W", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("loreal@gmail.com").id_user,"Shave").id_category,null));
        products.add(new Product("Solar Cream","Sensitive skin, 125mL", 8.42f, categoryDAO().getWithStoreAndName(userDAO().get("loreal@gmail.com").id_user,"Sun care & Tanning").id_category,null));
        products.add(new Product("Indian Incenses","10 fragrances", 17.59f, categoryDAO().getWithStoreAndName(userDAO().get("loreal@gmail.com").id_user,"Relaxation").id_category,null));

        /** generateMusicTemplate */
        products.add(new Product("Fender Player Stratocaster PF Fiesta Red","Electric Guitar, Rightist, Maple, 3 mics (Alnico V)", 769.0f, categoryDAO().getWithStoreAndName(userDAO().get("pioneer@gmail.com").id_user,"Guitars").id_category,null));
        products.add(new Product("Yamaha TRBX504 Trans Black","Bass, Leftist, Mahogany, 2 mics (Alnico V)", 569.0f, categoryDAO().getWithStoreAndName(userDAO().get("pioneer@gmail.com").id_user,"Basses").id_category,null));
        products.add(new Product("Pearl Drums Export Standard 22","22\"x18\" Bass Drum, 12\"x8\" Tom, 13\"x9\" Tom, 16\"x16\" Floor Tom, 14\"x5.5\" Snare Drum", 825.0f, categoryDAO().getWithStoreAndName(userDAO().get("pioneer@gmail.com").id_user,"Drums").id_category,null));
        products.add(new Product("Yamaha PSR-E473","Keyboard, 61 velocity-sensitive keys, 820 sounds, 290 models, 64 voice polyphony", 377.0f, categoryDAO().getWithStoreAndName(userDAO().get("pioneer@gmail.com").id_user,"Keys").id_category,null));
        products.add(new Product("Eagltone Rimini 4/4","Violin, Spruce/Maple/Ebony", 149.0f, categoryDAO().getWithStoreAndName(userDAO().get("pioneer@gmail.com").id_user,"Strings").id_category,null));
        products.add(new Product("Jupiter JAS500Q","Saxophone, Body yellow brass, High F# key, Gold lacquer finish, Key Eb", 860.0f, categoryDAO().getWithStoreAndName(userDAO().get("pioneer@gmail.com").id_user,"Winds").id_category,null));

        /** generateTechnologyTemplate */
        products.add(new Product("ASUS Zephyrus M16","Laptop, i7 11800H, RTX 3060, 16GB", 1999.0f, categoryDAO().getWithStoreAndName(userDAO().get("boulanger@gmail.com").id_user,"Computers").id_category,null));
        products.add(new Product("The Legend of Zelda : BOTW 2","Nintendo Switch, Action/Adventure, PEGI 12", 59.90f, categoryDAO().getWithStoreAndName(userDAO().get("boulanger@gmail.com").id_user,"Video Games").id_category,null));
        products.add(new Product("Apple iPhone 13","Black, 256GB, Apple A15, 5G", 979.0f, categoryDAO().getWithStoreAndName(userDAO().get("boulanger@gmail.com").id_user,"Cell-phones").id_category,null));
        products.add(new Product("LG OLED55C1","4K UHD, OLED, SmartTV, 4 HDMI / 3 USB", 1299.0f, categoryDAO().getWithStoreAndName(userDAO().get("boulanger@gmail.com").id_user,"TV").id_category,null));
        products.add(new Product("Panasonic Lumix GH5","20,3Mpx, 3.2\" screen, OLED sensor, 2-axis stabilization, 4K, Wi-Fi", 1449.0f, categoryDAO().getWithStoreAndName(userDAO().get("boulanger@gmail.com").id_user,"Cameras").id_category,null));
        products.add(new Product("Enclave EA1000THXUS CineHome Pro 5.1","Custom Drivers, 11 Class-D Digital Amplifiers, Full-Range Rears, 10 Subwoofer, Speaker Level Setup, Whole Room Stereo, Dolby and DTS Audio", 1840.0f, categoryDAO().getWithStoreAndName(userDAO().get("boulanger@gmail.com").id_user,"Home Studio").id_category,null));
        products.add(new Product("Apple Watch Series 7","GPS, 41mm Starlight Aluminum Case with Starlight Sport Band, Regular", 389.0f, categoryDAO().getWithStoreAndName(userDAO().get("boulanger@gmail.com").id_user,"Wearables").id_category,null));


        /** generateClothingTemplate  */
        products.add(new Product("Plain Tshirt","White, 100% Cotton, L", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Tops").id_category,null));
        products.add(new Product("Oversize Tshirt","Black, 100% Cotton, M", 24.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Tops").id_category,null));
        products.add(new Product("Long Sleeve Button-down Shirt","Plaid red, 100% Cotton Flannel, S", 39.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Shirts").id_category,null));
        products.add(new Product("Sweatshirt","Grey, 90% Cotton 10% Polyester, M", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Sweaters").id_category,null));
        products.add(new Product("Cocktail Dress","Black, 100% Polyester, 38", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Dresses").id_category,null));
        products.add(new Product("Vintage Jeans","Bleached, 98% Cotton 2% Elastane, 40", 39.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Jeans").id_category,null));
        products.add(new Product("Flare Trousers","Purple, 90% Cotton 10% Polyester, 40", 24.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Pants").id_category,null));
        products.add(new Product("Cargo Short","Kaki, 100% Cotton, M", 19.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Shorts").id_category,null));
        products.add(new Product("Mini Skirt","Pale Pink, 100% Polyester, 36", 19.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Skirts").id_category,null));
        products.add(new Product("Trench Coat","Beige, 100% Wool, 40", 99.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Coats").id_category,null));
        products.add(new Product("Three Pieces Suit","Navy, 98% Wool 2% Polyester, 40", 149.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Suits").id_category,null));
        products.add(new Product("2-pack Trunks","Black, 100% Cotton, M", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Underwear").id_category,null));
        products.add(new Product("Swimsuit","Blue/Orange Print, Unique", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("celio@gmail.com").id_user,"Swimwear").id_category,null));

        /**  generateSportTemplate */
        products.add(new Product("Running Shoes","Grey/White, 43", 19.99f, categoryDAO().getWithStoreAndName(userDAO().get("intersport@gmail.com").id_user,"Athletics").id_category,null));
        products.add(new Product("Jersey","Red/Green, L", 19.99f, categoryDAO().getWithStoreAndName(userDAO().get("intersport@gmail.com").id_user,"Basketball").id_category,null));
        products.add(new Product("Ropes","5m", 4.99f, categoryDAO().getWithStoreAndName(userDAO().get("intersport@gmail.com").id_user,"Climbing").id_category,null));
        products.add(new Product("Ball","World Cup 2022", 9.99f, categoryDAO().getWithStoreAndName(userDAO().get("intersport@gmail.com").id_user,"Football").id_category,null));
        products.add(new Product("Club set","3 Clubs (1 Driver, 1 Sand Wedge, 1 Putter), 3 balls", 199.99f, categoryDAO().getWithStoreAndName(userDAO().get("intersport@gmail.com").id_user,"Golf").id_category,null));
        products.add(new Product("Kimono","White", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("intersport@gmail.com").id_user,"Karate").id_category,null));
        products.add(new Product("Swim Goggles","Blue", 9.99f, categoryDAO().getWithStoreAndName(userDAO().get("intersport@gmail.com").id_user,"Swimming").id_category,null));
        products.add(new Product("Tennis Racket","Standard", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("intersport@gmail.com").id_user,"Tennis").id_category,null));
        products.add(new Product("Wing","Standard", 299.99f, categoryDAO().getWithStoreAndName(userDAO().get("intersport@gmail.com").id_user,"Paragliding").id_category,null));


        /**
         *  costumes *
         */


        /** generateClassyCostumeTemplate */

        // Classy Suits
        products.add(new Product("Classic Black Suit", "Slim Fit, Wool Blend", 299.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Classy Suits").id_category, null));
        products.add(new Product("Navy Blue Three-Piece Suit", "Regular Fit, Polyester", 249.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Classy Suits").id_category, null));
        products.add(new Product("Charcoal Gray Suit", "Tailored Fit, Tweed", 349.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Classy Suits").id_category, null));

        // Formal Shirts
        products.add(new Product("White French Cuff Shirt", "Classic Fit, Cotton", 49.99f,categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Formal Shirts").id_category, null));
        products.add(new Product("Light Blue Dress Shirt", "Slim Fit, Oxford", 39.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Formal Shirts").id_category, null));
        products.add(new Product("Striped Pink Shirt", "Modern Fit, Polyester", 29.99f,categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Formal Shirts").id_category, null));

        // Ties
        products.add(new Product("Silk Black Tie", "Solid Color, Classic Width", 19.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Ties").id_category, null));
        products.add(new Product("Striped Navy Blue Tie", "Patterned, Skinny Width", 14.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Ties").id_category, null));
        products.add(new Product("Polka Dot Red Tie", "Silk, Standard Width", 17.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Ties").id_category, null));

        // Dress Shoes
        products.add(new Product("Black Leather Oxford Shoes", "Formal, Lace-up", 79.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Dress Shoes").id_category, null));
        products.add(new Product("Brown Monk Strap Shoes", "Classic, Genuine Leather", 89.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Dress Shoes").id_category, null));
        products.add(new Product("Wingtip Brogue Shoes", "Two-Tone, Derby Style", 99.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Dress Shoes").id_category, null));

        // Accessories Classy Suits
        products.add(new Product("Silk Pocket Square", "White with Navy Trim", 9.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Accessories Classy Suits").id_category, null));
        products.add(new Product("Gold Cufflinks", "Classic Design", 14.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Accessories Classy Suits").id_category, null));
        products.add(new Product("Black Leather Belt", "Polished Silver Buckle", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("armani@gmail.com").id_user, "Accessories Classy Suits").id_category, null));


        /** generateCasualOutfitTemplate */

        // Casual Shirts
        products.add(new Product("Plaid Flannel Shirt", "Regular Fit, Cotton", 34.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Shirts").id_category, null));
        products.add(new Product("Chambray Button-Up Shirt", "Slim Fit, Linen", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Shirts").id_category, null));
        products.add(new Product("Striped Henley Shirt", "Relaxed Fit, Jersey", 24.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Shirts").id_category, null));

        // Casual Pants
        products.add(new Product("Khaki Chinos", "Straight Fit, Cotton", 39.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Pants").id_category, null));
        products.add(new Product("Slim Fit Jogger Pants", "Elastic Waist, Polyester", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Pants").id_category, null));
        products.add(new Product("Cargo Pants", "Loose Fit, Ripstop Fabric", 44.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Pants").id_category, null));

        // Denim Jackets
        products.add(new Product("Classic Blue Denim Jacket", "Regular Fit, Button Closure", 59.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Denim Jackets").id_category, null));
        products.add(new Product("Distressed Denim Jacket", "Slim Fit, Ripped Details", 64.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Denim Jackets").id_category, null));
        products.add(new Product("Black Denim Trucker Jacket", "Relaxed Fit, Collared", 54.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Denim Jackets").id_category, null));

        // Casual Shoes
        products.add(new Product("Canvas Slip-On Sneakers", "Casual Comfort, Rubber Sole", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Shoes").id_category, null));
        products.add(new Product("Casual Loafers", "Faux Leather, Moccasin Toe", 39.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Shoes").id_category, null));
        products.add(new Product("Suede Desert Boots", "Lace-up, Crepe Sole", 49.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Shoes").id_category, null));

        // Casual Accessories
        products.add(new Product("Casual Canvas Belt", "D-Ring Buckle, Adjustable", 14.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Accessories").id_category, null));
        products.add(new Product("Beanie Hat", "Knit, Slouchy Style", 9.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Accessories").id_category, null));
        products.add(new Product("Aviator Sunglasses", "Metal Frame, UV Protection", 19.99f, categoryDAO().getWithStoreAndName(userDAO().get("boss@gmail.com").id_user, "Casual Accessories").id_category, null));


        /** generateBurberryCostumeTemplate */
        // Burberry Suits
        products.add(new Product("Burberry Classic Suit", "Tailored Fit, Wool Blend", 499.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Suits").id_category, null));
        products.add(new Product("Burberry Checkered Suit", "Slim Fit, Polyester", 549.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Suits").id_category, null));
        products.add(new Product("Burberry Trench Coat Suit", "Regular Fit, Cotton Blend", 599.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Suits").id_category, null));

        // Burberry Shirts
        products.add(new Product("Burberry White Dress Shirt", "Classic Fit, Cotton", 79.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Shirts").id_category, null));
        products.add(new Product("Burberry Striped Shirt", "Slim Fit, Oxford", 89.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Shirts").id_category, null));
        products.add(new Product("Burberry Checkered Shirt", "Modern Fit, Polyester", 99.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Shirts").id_category, null));

        // Burberry Ties
        products.add(new Product("Burberry Silk Black Tie", "Solid Color, Classic Width", 59.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Ties").id_category, null));
        products.add(new Product("Burberry Striped Navy Blue Tie", "Patterned, Skinny Width", 49.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Ties").id_category, null));
        products.add(new Product("Burberry Polka Dot Red Tie", "Silk, Standard Width", 54.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Ties").id_category, null));

        // Burberry Dress Shoes
        products.add(new Product("Burberry Black Leather Oxford Shoes", "Formal, Lace-up", 129.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Dress Shoes").id_category, null));
        products.add(new Product("Burberry Brown Monk Strap Shoes", "Classic, Genuine Leather", 139.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Dress Shoes").id_category, null));
        products.add(new Product("Burberry Wingtip Brogue Shoes", "Two-Tone, Derby Style", 149.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Dress Shoes").id_category, null));

        // Burberry Accessories
        products.add(new Product("Burberry Silk Pocket Square", "Checkered Pattern", 19.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Accessories").id_category, null));
        products.add(new Product("Burberry Gold Cufflinks", "Classic Design", 29.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Accessories").id_category, null));
        products.add(new Product("Burberry Leather Belt", "Polished Gold Buckle", 39.99f, categoryDAO().getWithStoreAndName(userDAO().get("Burberry@gmail.com").id_user, "Burberry Accessories").id_category, null));

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