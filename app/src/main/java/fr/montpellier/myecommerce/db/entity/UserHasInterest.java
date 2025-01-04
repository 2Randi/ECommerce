package fr.montpellier.myecommerce.db.entity;

import androidx.room.*;

import java.io.Serializable;

@Entity(tableName = "UserHasInterest",
        foreignKeys = {
                @ForeignKey(entity = Interest.class,
                        parentColumns = "id_interest",
                        childColumns = "id_interest"),
                @ForeignKey(entity = User.class,
                        parentColumns = "id_user",
                        childColumns = "id_user")
        })
public class UserHasInterest implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_uhi;

    @ColumnInfo(name = "id_interest", index = true)
    public int id_interest;

    @ColumnInfo(name = "id_user", index = true)
    public int id_user;

    public UserHasInterest(int id_interest, int id_user) {
        this.id_interest = id_interest;
        this.id_user = id_user;
    }
}