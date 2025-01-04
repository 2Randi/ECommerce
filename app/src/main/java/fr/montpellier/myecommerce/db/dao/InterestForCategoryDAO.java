package fr.montpellier.myecommerce.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import fr.montpellier.myecommerce.db.entity.Interest;
import fr.montpellier.myecommerce.db.entity.InterestForCategory;

import java.util.List;

@Dao
public interface InterestForCategoryDAO {
    // Récupérer un lien entre un intérêt et une catégorie par son ID
    @Query("SELECT * FROM InterestForCategory WHERE InterestForCategory.id_ifc = :id LIMIT 1")
    InterestForCategory get(int id);

    // Récupérer tous les intérêts associés à une catégorie spécifique
    @Query("SELECT Interest.* FROM InterestForCategory inner join Interest on InterestForCategory.id_interest = Interest.id_interest WHERE InterestForCategory.id_category = :id_category")
    List<Interest> getInterestsWithCategory(int id_category);

    // Insérer un lien entre un intérêt et une catégorie dans la base de données
    @Insert(onConflict = ABORT)
    void insert(InterestForCategory ifc);

    // Insérer plusieurs liens entre intérêts et catégories dans la base de données
    @Insert(onConflict = ABORT)
    void insertAll(List<InterestForCategory> ifcs);

    // Supprimer tous les liens entre intérêts et catégories de la base de données
    @Query("DELETE FROM InterestForCategory")
    void clear();
}
