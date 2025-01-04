package fr.montpellier.myecommerce.db.entity;

import androidx.room.*;

import java.io.Serializable;

@Entity(tableName = "Product",
        foreignKeys = @ForeignKey(entity = Category.class,
                parentColumns = "id_category",
                childColumns = "id_category"))
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_product;

    @ColumnInfo(name = "id_category", index = true)
    public int id_category;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "details")
    public String details;

    @ColumnInfo(name = "price")
    public float price;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] img;

    public Product(String name, String details, float price, int id_category, byte[] img) {
        this.id_category = id_category;
        this.name = name;
        this.details = details;
        this.price = price;
        this.img = img;
    }
}
