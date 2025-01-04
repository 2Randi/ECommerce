package fr.montpellier.myecommerce.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import fr.montpellier.myecommerce.db.entity.User;

@Dao
public interface UserDAO {

    // Récupère un utilisateur par son ID
    @Query("SELECT * FROM User where User.id_user = :id LIMIT 1")
    User get(int id);

    // Récupère un utilisateur par son adresse e-mail
    @Query("SELECT * FROM User where User.mail = :mail LIMIT 1")
    User get(String mail);

    // Récupère un utilisateur par son token de connexion
    @Query("SELECT * FROM User where User.token_connect = :token LIMIT 1")
    User getWithToken(String token);

    // Supprime tous les enregistrements de la table User
    @Query("DELETE FROM User")
    void clear();

    // Insère un utilisateur dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    long insert(User user);

    // Insère une liste d'utilisateurs dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insertAll(List<User> users);

    // Supprime un utilisateur de la base de données
    @Delete
    void delete(User user);

    // Vérifie le nombre total d'utilisateurs dans la table User
    // pour créer la base de donnée si elle est vide
    @Query("SELECT COUNT(*) FROM user")
    int getTotalUsers();
    // Fin de la vérification
}
