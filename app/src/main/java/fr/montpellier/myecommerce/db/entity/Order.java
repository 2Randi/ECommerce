package fr.montpellier.myecommerce.db.entity;

import androidx.room.*;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "OrderTable",
        foreignKeys = {
                @ForeignKey(entity = Store.class,
                        parentColumns = "id_user_store",
                        childColumns = "id_store"),
                @ForeignKey(entity = User.class,
                        parentColumns = "id_user",
                        childColumns = "id_user")
        })
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_order;

    @ColumnInfo(name = "id_store", index = true)
    public int id_store;

    @ColumnInfo(name = "id_user", index = true)
    public int id_user;

    @ColumnInfo(name = "date_order")
    public Date date_order;

    public Order(int id_store, int id_user, Date date_order) {
        this.id_store = id_store;
        this.id_user = id_user;
        this.date_order = date_order;
    }
}