package fr.montpellier.myecommerce.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.montpellier.myecommerce.db.entity.Category;
import fr.montpellier.myecommerce.db.entity.Product;


@Dao
public interface CategoryDAO {
    // Récupérer une catégorie par son ID
    @Query("SELECT * FROM Category WHERE Category.id_category = :id LIMIT 1")
    Category get(int id);

    // Récupérer une catégorie avec un nom spécifique dans un magasin spécifique
    @Query("SELECT * FROM Category WHERE Category.id_store = :id_store and Category.name = :name LIMIT 1")
    Category getWithStoreAndName(int id_store, String name);

    // Récupérer toutes les catégories d'un magasin spécifique
    @Query("SELECT * FROM Category WHERE Category.id_store = :id_store")
    List<Category> getCategoriesWithStore(int id_store);

    // Récupérer tous les produits de toutes les catégories d'un magasin spécifique
    @Query("SELECT distinct Product.* FROM Category INNER JOIN Product on Product.id_category = Category.id_category where Category.id_store = :id_store")
    List<Product> getProductsOfCategoriesofStore(int id_store);

    // Récupérer tous les produits d'une catégorie spécifique
    @Query("SELECT distinct Product.* FROM Category INNER JOIN Product on Product.id_category = Category.id_category where Category.id_category = :id_category")
    List<Product> getProductsOfCategory(int id_category);

    // Insérer une catégorie dans la base de données
    @Insert(onConflict = ABORT)
    long insert(Category category);

    // Insérer plusieurs catégories dans la base de données
    @Insert(onConflict = ABORT)
    void insertAll(List<Category> categories);

    // Supprimer toutes les catégories de la base de données
    @Query("DELETE FROM Category")
    void clear();
}