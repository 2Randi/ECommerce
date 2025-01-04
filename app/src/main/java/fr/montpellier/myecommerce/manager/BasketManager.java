package fr.montpellier.myecommerce.manager;

import android.content.Context;

import java.util.Date;
import java.util.List;

import fr.montpellier.myecommerce.db.AppDatabase;
import fr.montpellier.myecommerce.db.entity.Offer;
import fr.montpellier.myecommerce.db.entity.Product;
import fr.montpellier.myecommerce.db.entity.ProductInBasket;

public class BasketManager {

    // Méthode pour calculer la somme totale du panier
    public static float getSumBasket(Context context, int id_user){
        // Obtention de l'instance de la base de données
        AppDatabase apd =  AppDatabase.getInstance(context);

        // Récupération des produits dans le panier pour l'utilisateur donné
        List<ProductInBasket> pibs = apd.productInBasketDAO().getWithUser(id_user);

        // Initialisation de la taille du panier et de la somme totale
        int size_basket = pibs.size();
        float sum_basket = 0;

        // Parcours des produits dans le panier
        for(int i = 0; i < size_basket;i++){
            // Récupération des détails du produit
            Product product =  apd.productDAO().get(pibs.get(i).id_product);

            float price;

            // Vérification des offres disponibles pour le produit à la date actuelle
            Offer offer = apd.offerDAO().get(product.id_product,new Date());

            // Calcul du prix en fonction de l'offre disponible
            if(offer == null ){
                price = product.price;
            } else {
                price = offer.price;
            }

            // Calcul de la somme totale pour le produit actuel
            sum_basket += pibs.get(i).quantity * price;
        }

        // Arrondi de la somme totale à deux décimales
        return Math.round(sum_basket*100.0f)/100.0f;
    }
}
