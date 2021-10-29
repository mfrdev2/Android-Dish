package com.frabbi.splashscreendemo.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.frabbi.splashscreendemo.model.database.FavDishRepository;

public class FavDishViewModelFactory implements ViewModelProvider.Factory {
    FavDishRepository repository;

    public FavDishViewModelFactory(FavDishRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavDishViewModel.class)) {
            return (T) new FavDishViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown viewModel class");
    }
}
