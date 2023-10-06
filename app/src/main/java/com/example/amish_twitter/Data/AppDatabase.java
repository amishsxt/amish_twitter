package com.example.amish_twitter.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kotlin.jvm.Volatile;

@Database(entities = User.class, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String db_name = "XUserDatabase";

    @Volatile
    private static AppDatabase instance;

    public static synchronized AppDatabase getDB(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, db_name)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract UserDao userDao();
}
