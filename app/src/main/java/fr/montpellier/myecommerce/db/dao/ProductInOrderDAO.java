package fr.montpellier.myecommerce.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import fr.montpellier.myecommerce.db.entity.Product;
import fr.montpellier.myecommerce.db.entity.ProductInOrder;

@Dao
public interface ProductInOrderDAO {

    // Récupère un produit dans une commande par son ID
    @Query("SELECT * FROM ProductInOrder WHERE ProductInOrder.id_pio = :id LIMIT 1")
    ProductInOrder get(int id);

    // Récupère tous les produits associés à une commande
    @Query("SELECT Product.* FROM ProductInOrder inner join Product on Product.id_product = ProductInOrder.id_product WHERE ProductInOrder.id_order = :id_order")
    List<Product> getProductsWithOrder(int id_order);

    // Récupère un enregistrement ProductInOrder spécifique avec une commande et un produit donnés
    @Query("SELECT * FROM ProductInOrder WHERE ProductInOrder.id_order = :id_order and ProductInOrder.id_product = :id_product LIMIT 1")
    ProductInOrder getWithOrderAndProduct(int id_order, int id_product);

    // Calcule la somme totale des prix des produits dans une commande
    @Query("SELECT sum(ProductInOrder.total_price) FROM ProductInOrder WHERE ProductInOrder.id_order = :id_order")
    float getSumOrder(int id_order);

    // Insère un enregistrement ProductInOrder dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    long insert(ProductInOrder pio);

    // Insère une liste d'enregistrements ProductInOrder dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insertAll(List<ProductInOrder> pios);

    // Supprime tous les enregistrements de la table ProductInOrder
    @Query("DELETE FROM ProductInOrder")
    void clear();
}
