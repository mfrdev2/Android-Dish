package com.frabbi.splashscreendemo.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.frabbi.splashscreendemo.R;
import com.frabbi.splashscreendemo.databinding.ActivityAddBinding;

import java.util.Objects;

public class AddActivity extends AppCompatActivity {
    private ActivityAddBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        //Setup toolbar to Actionbar
        setupActionBar();
    }

    private void setupActionBar() {
        //set actionbar as toolbar
        setSupportActionBar(mBinding.toolbar);

        //nav BackButton enable
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //set listener to nav. backButton
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}