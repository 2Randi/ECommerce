package fr.montpellier.myecommerce.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Store",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id_user",
                childColumns = "id_user_store"))
public class Store implements Serializable {
    @PrimaryKey
    public int id_user_store;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] img_store;

    public Store(int id_user_store, String name, byte[] img_store) {
        this.id_user_store = id_user_store;
        this.name = name;
        this.img_store = img_store;
    }
}
