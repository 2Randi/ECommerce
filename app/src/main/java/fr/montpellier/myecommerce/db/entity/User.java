package fr.montpellier.myecommerce.db.entity;


import androidx.room.*;

import java.io.Serializable;

import fr.montpellier.myecommerce.utils.HashUtil;

@Entity(tableName = "User")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_user;

    @ColumnInfo(name = "mail")
    public String mail;

    @ColumnInfo(name = "hash_pwd")
    public String hash_pwd;

    @ColumnInfo(name = "is_merchant")
    public boolean is_merchant;

    @ColumnInfo(name = "token_connect")
    public String token_connect;

    //CUSTOMER
    @ColumnInfo(name = "first_name")
    public String first_name;

    @ColumnInfo(name = "last_name")
    public String last_name;

    public User(String mail,String hash_pwd,boolean is_merchant, String first_name, String last_name) {
        this.mail = mail;
        this.hash_pwd = hash_pwd;
        this.is_merchant = is_merchant;
        this.first_name = first_name;
        this.last_name = last_name;
        this.token_connect = HashUtil.randomString(128);
    }
}
