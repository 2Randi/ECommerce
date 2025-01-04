package fr.montpellier.myecommerce.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import fr.montpellier.myecommerce.db.entity.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    // Récupère un produit par son ID
    @Query("SELECT * FROM Product WHERE Product.id_product = :id LIMIT 1")
    Product get(int id);

    // Récupère une liste de produits dont le nom ou les détails contiennent la chaîne spécifiée
    @Query("SELECT * FROM Product where Product.name like '%' || :str || '%' or Product.details like '%' || :str || '%'")
    List<Product> getProductsLike(String str);

    // Récupère une liste de produits intéressants pour un utilisateur basé sur ses intérêts
    @Query("SELECT distinct Product.* FROM Product " +
            "inner join Category on Product.id_category = Category.id_category " +
            "inner join InterestForCategory on Category.id_category = InterestForCategory.id_category " +
            "inner join UserHasInterest on InterestForCategory.id_interest = UserHasInterest.id_interest " +
            "where UserHasInterest.id_user = :id_user")
    List<Product> getProductsInterestingForUser(int id_user);

    // Insère un produit dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insert(Product product);

    // Insère une liste de produits dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insertAll(List<Product> products);

    // Supprime tous les produits de la table Product
    @Query("DELETE FROM Product")
    void clear();
}
