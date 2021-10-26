package com.frabbi.splashscreendemo.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.frabbi.splashscreendemo.R;
import com.frabbi.splashscreendemo.databinding.ActivityAddBinding;
import com.frabbi.splashscreendemo.databinding.DialogCustomAddImageBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;


import java.util.List;
import java.util.Objects;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddBinding mBinding;
    private static final int CAMERA = 101;
    private static final int GALLERY = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        //Setup toolbar to Actionbar
        setupActionBar();

        //clickListener set in addImage view
        mBinding.ivAddDishImage.setOnClickListener(this);
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

    //clickListener set in addImage view implementation scope
    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.iv_add_dish_image:
                    pickImage();
                    return;
            }
        }
    }

    private void pickImage() {
        DialogCustomAddImageBinding dBinding = DialogCustomAddImageBinding.inflate(getLayoutInflater());
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(dBinding.getRoot());

        //For Camera Action
        dBinding.cameraImgId.setOnClickListener(v -> {
            cameraAction();
            dialog.dismiss();
        });

        // For Gallery Action
        dBinding.galleryImgId.setOnClickListener(v -> {
            galleryAction();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void galleryAction() {
        //runtime permission gallery
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                       //galleryTask controlling
                        galleryTask();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                       //Rationale Permission cause
                        showRationaleDialog();
                    }
                })
                .onSameThread().check();
    }


    private void cameraAction() {
        //runtime permission camera
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport != null) {
                            if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                //CameraTask controlling
                                cameraTask();
                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        //  permissionToken.continuePermissionRequest();
                        //Rationale Permission cause
                        showRationaleDialog();
                    }
                })
                .onSameThread().check();
    }

    //Rationale Permission cause
    private void showRationaleDialog() {
        new AlertDialog.Builder(this)
                .setMessage("It looks like you have turned off the permission required for this feature. It can be enabled under Application Settings")

                .setPositiveButton("GO TO SETTINGS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                })

                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })

                .show();

    }

    //CameraTask controlling
    private void cameraTask() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    //galleryTask controlling
    private void galleryTask() {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,GALLERY);
    }

    //setup data into UI
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {
                try {
                    assert data != null;
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    mBinding.ivDishImage.setImageBitmap(bitmap);
                    mBinding.ivAddDishImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_edit_24));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (requestCode == GALLERY) {
                try {
                    assert data != null;
                    Uri uri = data.getData();
                    mBinding.ivDishImage.setImageURI(uri);
                    mBinding.ivAddDishImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_edit_24));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}