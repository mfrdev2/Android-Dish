package com.frabbi.splashscreendemo.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.frabbi.splashscreendemo.R;
import com.frabbi.splashscreendemo.databinding.ActivitySecondActivityBinding;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondActivityBinding asb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asb = ActivitySecondActivityBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_second_activity);
        setContentView(asb.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.fragmentContainer);
        AppBarConfiguration barConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this,navController,barConfiguration);
        NavigationUI.setupWithNavController(asb.bottomNavigationView,navController);
    }
}