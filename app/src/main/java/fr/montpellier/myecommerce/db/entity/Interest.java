package fr.montpellier.myecommerce.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Interest")
public class Interest implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_interest;

    @ColumnInfo(name = "name")
    public String name;


    public Interest(String name) {
        this.name = name;
    }
}
