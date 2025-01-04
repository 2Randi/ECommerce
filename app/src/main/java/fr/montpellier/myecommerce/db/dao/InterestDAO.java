package fr.montpellier.myecommerce.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.montpellier.myecommerce.db.entity.Interest;

@Dao
public interface InterestDAO {
    // Récupérer un intérêt par son ID
    @Query("SELECT * FROM Interest WHERE Interest.id_interest = :id LIMIT 1")
    Interest get(int id);

    // Récupérer un intérêt par son nom
    @Query("SELECT * FROM Interest WHERE Interest.name = :name LIMIT 1")
    Interest get(String name);

    // Récupérer tous les intérêts
    @Query("SELECT * FROM Interest")
    List<Interest> getAll();

    // Insérer un intérêt dans la base de données
    @Insert(onConflict = ABORT)
    void insert(Interest interest);

    // Insérer plusieurs intérêts dans la base de données
    @Insert(onConflict = ABORT)
    void insertAll(List<Interest> interests);

    // Supprimer tous les intérêts de la base de données
    @Query("DELETE FROM Interest")
    void clear();
}

