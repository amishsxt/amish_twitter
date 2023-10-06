package com.example.amish_twitter.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @PrimaryKey (autoGenerate = true)
    public int id;

    @ColumnInfo(name="Username")
    public String username;

    @ColumnInfo(name="Password")
    public String password;

    @ColumnInfo(name="Name")
    public String name;

    @ColumnInfo(name="DOB")
    public String dob;


    public User(){

    }

    public User(String username, String password, String name, String dob) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.dob = dob;
    }

    public User(String username, String name, String dob) {
        this.username = username;
        this.name = name;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
