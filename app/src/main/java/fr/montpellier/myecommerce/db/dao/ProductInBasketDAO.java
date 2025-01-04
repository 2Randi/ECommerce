package fr.montpellier.myecommerce.db.dao;


import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import fr.montpellier.myecommerce.db.entity.ProductInBasket;
import fr.montpellier.myecommerce.db.entity.Store;

import java.util.List;

@Dao
public interface ProductInBasketDAO {

    // Récupère un produit dans le panier par son ID
    @Query("SELECT * FROM ProductInBasket WHERE ProductInBasket.id_pib = :id LIMIT 1")
    ProductInBasket get(int id);

    // Récupère tous les produits dans le panier d'un utilisateur
    @Query("SELECT * FROM ProductInBasket WHERE ProductInBasket.id_user = :id_user")
    List<ProductInBasket> getWithUser(int id_user);

    // Récupère tous les magasins ayant des produits dans le panier d'un utilisateur
    @Query("SELECT Distinct Store.* FROM ProductInBasket " +
            "inner join Product on ProductInBasket.id_product = Product.id_product " +
            "inner join Category on Product.id_category = Category.id_category " +
            "inner join Store on Category.id_store = Store.id_user_store " +
            "WHERE ProductInBasket.id_user = :id_user")
    List<Store> getStoreWithProductsInBasket(int id_user);

    // Récupère un produit spécifique dans le panier d'un utilisateur
    @Query("SELECT * FROM ProductInBasket WHERE ProductInBasket.id_product = :id_product and ProductInBasket.id_user = :id_user")
    ProductInBasket getWithProductAndUser(int id_product, int id_user);

    // Récupère tous les produits dans le panier d'un utilisateur pour un magasin spécifique
    @Query("SELECT ProductInBasket.* FROM ProductInBasket " +
            "inner join Product on ProductInBasket.id_product = Product.id_product " +
            "inner join Category on Product.id_category = Category.id_category " +
            "WHERE Category.id_store = :id_store and ProductInBasket.id_user = :id_user")
    List<ProductInBasket> getWithStoreAndUser(int id_store, int id_user);

    // Calcule la somme totale des prix des produits dans le panier d'un utilisateur
    @Query("SELECT sum(total_price_product) FROM (SELECT ProductInBasket.quantity * Product.price as total_price_product FROM ProductInBasket inner join Product on ProductInBasket.id_product = Product.id_product " +
            "WHERE ProductInBasket.id_user = :id_user)")
    float getSumBasket(int id_user);

    // Met à jour la quantité d'un produit dans le panier
    @Query("UPDATE ProductInBasket SET quantity = :quantity WHERE ProductInBasket.id_pib = :id_pib")
    void changeQuantity(int id_pib, int quantity);

    // Insère un produit dans le panier avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insert(ProductInBasket pib);

    // Insère une liste de produits dans le panier avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insertAll(List<ProductInBasket> pibs);

    // Supprime tous les produits dans le panier d'un utilisateur
    @Query("DELETE FROM ProductInBasket where ProductInBasket.id_user = :id_user")
    void clearBasket(int id_user);

    // Supprime un produit spécifique dans le panier par son ID
    @Query("DELETE FROM ProductInBasket where ProductInBasket.id_pib = :id_pib")
    void clear(int id_pib);

    // Supprime tous les produits dans le panier
    @Query("DELETE FROM ProductInBasket")
    void clear();
}
