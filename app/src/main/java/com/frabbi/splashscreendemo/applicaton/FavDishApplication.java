package com.frabbi.splashscreendemo.applicaton;

import android.app.Application;

import com.frabbi.splashscreendemo.model.database.FavDishRepository;
import com.frabbi.splashscreendemo.model.database.FavDishRoomDatabase;

public class FavDishApplication extends Application {

    private static FavDishRoomDatabase roomDatabase;

    public static FavDishRepository repository;

    private synchronized void getInstance() {
        if (roomDatabase == null) {
            roomDatabase = FavDishRoomDatabase.getDatabase(FavDishApplication.this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getInstance();
        if(repository == null){
            repository = new FavDishRepository(roomDatabase.favDishDao());
        }
    }
}
