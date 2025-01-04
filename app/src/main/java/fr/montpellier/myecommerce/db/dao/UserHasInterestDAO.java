package fr.montpellier.myecommerce.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.montpellier.myecommerce.db.entity.UserHasInterest;

@Dao
public interface UserHasInterestDAO {

    // Récupère une relation utilisateur-intérêt par son ID
    @Query("SELECT * FROM UserHasInterest WHERE UserHasInterest.id_uhi = :id LIMIT 1")
    UserHasInterest get(int id);

    // Récupère une relation utilisateur-intérêt spécifique par l'ID de l'utilisateur et l'ID de l'intérêt
    @Query("SELECT * FROM UserHasInterest WHERE UserHasInterest.id_interest = :id_interest and UserHasInterest.id_user = :id_user")
    UserHasInterest getWithUserAndInterest(int id_user, int id_interest);

    // Insère une relation utilisateur-intérêt dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insert(UserHasInterest uhi);

    // Insère une liste de relations utilisateur-intérêt dans la base de données avec une stratégie de conflit ABORT
    @Insert(onConflict = ABORT)
    void insertAll(List<UserHasInterest> uhis);

    // Supprime tous les enregistrements de la table UserHasInterest
    @Query("DELETE FROM UserHasInterest")
    void clear();

    // Supprime toutes les relations utilisateur-intérêt pour un utilisateur donné
    @Query("DELETE FROM UserHasInterest WHERE UserHasInterest.id_user = :id_user")
    void clearForUser(int id_user);
}
