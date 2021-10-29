package com.frabbi.splashscreendemo.model.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.frabbi.splashscreendemo.model.entities.FavDish;

import java.util.List;

@Dao
public interface FavDishDao {

    @Insert
    void insertFavDishDetails(FavDish favDish);

    @Query("SELECT * FROM FavDish_Table ORDER BY _id")
    LiveData<List<FavDish>> getAllDataFromFavDishTable();
}
