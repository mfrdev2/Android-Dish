package com.frabbi.splashscreendemo.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.frabbi.splashscreendemo.model.database.FavDishRepository;
import com.frabbi.splashscreendemo.model.entities.FavDish;

import java.util.List;

public class FavDishViewModel extends ViewModel {
    FavDishRepository favDishRepository;


    public FavDishViewModel(FavDishRepository favDishRepository) {
        this.favDishRepository = favDishRepository;
    }


    public void insertFavDishDetails(FavDish data) {
        favDishRepository.insertFavDishDetails(data);
    }


    public LiveData<List<FavDish>> getListData() {
        return favDishRepository.getAllDishesList();
    }
}

