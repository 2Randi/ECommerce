package fr.montpellier.myecommerce.db.entity;

import androidx.room.*;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "Offer",
        foreignKeys = @ForeignKey(entity = Product.class,
                parentColumns = "id_product",
                childColumns = "id_product"))
public class Offer implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_offer;

    @ColumnInfo(name = "id_product", index = true)
    public int id_product;

    @ColumnInfo(name = "price")
    public float price;

    @ColumnInfo(name = "date_start")
    public Date date_start;

    @ColumnInfo(name = "date_end")
    public Date date_end;

    public Offer(int id_product, float price, Date date_start, Date date_end) {
        this.id_product = id_product;
        this.price = price;
        this.date_start = date_start;
        this.date_end = date_end;
    }
}