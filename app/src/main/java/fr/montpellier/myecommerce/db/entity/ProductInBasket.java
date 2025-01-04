package fr.montpellier.myecommerce.db.entity;

import androidx.room.*;

import java.io.Serializable;

@Entity(tableName = "ProductInBasket",
        foreignKeys = {
                @ForeignKey(entity = Product.class,
                        parentColumns = "id_product",
                        childColumns = "id_product"),
                @ForeignKey(entity = User.class,
                        parentColumns = "id_user",
                        childColumns = "id_user")
        })
public class ProductInBasket implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_pib;

    @ColumnInfo(name = "id_product", index = true)
    public int id_product;

    @ColumnInfo(name = "id_user", index = true)
    public int id_user;


    @ColumnInfo(name = "quantity")
    public int quantity;

    public ProductInBasket(int id_product, int id_user, int quantity) {
        this.id_product = id_product;
        this.id_user = id_user;
        this.quantity = quantity;
    }
}
