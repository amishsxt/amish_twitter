package com.example.amish_twitter.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select *from User order by id asc")
    List<User> getAllRecords();

    @Insert
    void insertRecord(User user);

    @Delete
    void deleteRecord(User user);

    @Update
    void updateRecord(User user);
}
