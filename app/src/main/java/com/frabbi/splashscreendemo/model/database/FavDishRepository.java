package com.frabbi.splashscreendemo.model.database;


import androidx.lifecycle.LiveData;

import com.frabbi.splashscreendemo.model.entities.FavDish;

import java.util.List;

public class FavDishRepository {
    private FavDishDao favDishDao;

    public FavDishRepository(FavDishDao favDishDao){
       this.favDishDao = favDishDao;
    }


    public void insertFavDishDetails(FavDish favDish) {
        FavDishRoomDatabase.databaseWriteExecutor.execute(() -> {
            favDishDao.insertFavDishDetails(favDish);
        });
    }

   public LiveData<List<FavDish>> getAllDishesList(){
       return favDishDao.getAllDataFromFavDishTable();
    }
}
