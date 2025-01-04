package fr.montpellier.myecommerce.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Category",
        foreignKeys = @ForeignKey(entity = Store.class,
                parentColumns = "id_user_store",
                childColumns = "id_store"))
public class Category implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_category;

    @ColumnInfo(name = "id_store", index = true)
    public int id_store;

    @ColumnInfo(name = "name")
    public String name;

    public Category(String name, int id_store) {
        this.name = name;
        this.id_store = id_store;
    }
}