package fr.montpellier.myecommerce.db.dao;


import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import fr.montpellier.myecommerce.db.entity.Order;

import java.util.List;

@Dao
public interface OrderDAO {

    // Récupère une commande par son ID
    @Query("SELECT * FROM OrderTable WHERE OrderTable.id_order = :id LIMIT 1")
    Order get(int id);

    // Récupère toutes les commandes d'un utilisateur dans un magasin spécifique
    @Query("SELECT * FROM OrderTable WHERE OrderTable.id_user = :id_user and OrderTable.id_store = :id_store")
    List<Order> get(int id_user, int id_store);

    // Récupère toutes les commandes d'un utilisateur
    @Query("SELECT * FROM OrderTable WHERE OrderTable.id_user = :id_user")
    List<Order> getWithUser(int id_user);

    // Récupère toutes les commandes d'un magasin
    @Query("SELECT * FROM OrderTable WHERE OrderTable.id_store = :id_store")
    List<Order> getWithStore(int id_store);

    // Insère une commande dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    long insert(Order order);

    // Insère une liste de commandes dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insertAll(List<Order> orders);

    // Supprime toutes les commandes de la table OrderTable
    @Query("DELETE FROM OrderTable")
    void clear();
}
