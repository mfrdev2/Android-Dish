package com.frabbi.splashscreendemo.model.database;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.frabbi.splashscreendemo.model.entities.FavDish;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {FavDish.class},version = 1)
public abstract class FavDishRoomDatabase extends RoomDatabase {
    public abstract FavDishDao  favDishDao();
    private static volatile FavDishRoomDatabase favDishRoomDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static FavDishRoomDatabase getDatabase(final Context context) {
        if (favDishRoomDatabase == null) {
            synchronized (FavDishRoomDatabase.class) {
                if (favDishRoomDatabase == null) {
                    favDishRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            FavDishRoomDatabase.class, "FavDish_Database")
                            .build();
                }
            }
        }
        return favDishRoomDatabase;
    }
}
