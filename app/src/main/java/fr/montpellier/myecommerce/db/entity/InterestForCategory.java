package fr.montpellier.myecommerce.db.entity;

import androidx.room.*;

import java.io.Serializable;

@Entity(tableName = "InterestForCategory",
        foreignKeys = {
                @ForeignKey(entity = Interest.class,
                        parentColumns = "id_interest",
                        childColumns = "id_interest"),
                @ForeignKey(entity = Category.class,
                        parentColumns = "id_category",
                        childColumns = "id_category")
        })
public class InterestForCategory implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_ifc;

    @ColumnInfo(name = "id_interest", index = true)
    public int id_interest;

    @ColumnInfo(name = "id_category", index = true)
    public int id_category;

    public InterestForCategory(int id_interest, int id_category) {
        this.id_interest = id_interest;
        this.id_category = id_category;
    }
}