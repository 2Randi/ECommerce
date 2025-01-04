package fr.montpellier.myecommerce.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import fr.montpellier.myecommerce.db.entity.Offer;

import java.util.Date;
import java.util.List;

@Dao
public interface OfferDAO {
    // Récupérer une offre par son ID
    @Query("SELECT * FROM Offer WHERE Offer.id_offer = :id LIMIT 1")
    Offer get(int id);

    // Récupérer l'offre la plus récente pour un produit spécifique à une date donnée
    @Query("SELECT * FROM Offer WHERE Offer.id_product == :id_product and Offer.date_end > :date and Offer.date_start <= :date order by Offer.date_start DESC, Offer.date_end ASC LIMIT 1")
    Offer get(int id_product, Date date);

    // Insérer une offre dans la base de données
    @Insert(onConflict = ABORT)
    void insert(Offer offer);

    // Insérer plusieurs offres dans la base de données
    @Insert(onConflict = ABORT)
    void insertAll(List<Offer> offers);

    // Supprimer toutes les offres de la base de données
    @Query("DELETE FROM Offer")
    void clear();
}

