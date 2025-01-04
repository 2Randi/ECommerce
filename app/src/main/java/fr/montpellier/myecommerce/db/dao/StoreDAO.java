package fr.montpellier.myecommerce.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import fr.montpellier.myecommerce.db.entity.Store;

@Dao
public interface StoreDAO {

    // Récupère un magasin par son ID
    @Query("SELECT * FROM Store where Store.id_user_store = :id LIMIT 1")
    Store get(int id);

    // Récupère une liste de magasins dont le nom contient la chaîne spécifiée
    @Query("SELECT * FROM Store where Store.name like '%' || :name_store || '%'")
    List<Store> getStoresLike(String name_store);

    // Supprime tous les enregistrements de la table Store
    @Query("DELETE FROM Store")
    void clear();

    // Insère un magasin dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insert(Store store);

    // Insère une liste de magasins dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insertAll(List<Store> stores);

    // Supprime un magasin de la base de données
    @Delete
    void delete(Store store);
}
