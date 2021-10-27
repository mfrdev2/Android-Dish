package com.frabbi.splashscreendemo.view.activities;

import static com.frabbi.splashscreendemo.utils.ConstantValue.DishCategory;
import static com.frabbi.splashscreendemo.utils.ConstantValue.DishCookingTime;
import static com.frabbi.splashscreendemo.utils.ConstantValue.DishType;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.frabbi.splashscreendemo.R;
import com.frabbi.splashscreendemo.databinding.ActivityAddBinding;
import com.frabbi.splashscreendemo.databinding.DialogCustomAddImageBinding;
import com.frabbi.splashscreendemo.databinding.DialogCustomListBinding;
import com.frabbi.splashscreendemo.utils.ConstantValue;
import com.frabbi.splashscreendemo.view.adapters.CustomListItemAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddBinding mBinding;
    private static final int CAMERA = 101;
    private static final int GALLERY = 102;
    private static final String IMAGE_DIRECTORY = "MyDishData";
    private String imgPath;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        //Setup toolbar to Actionbar
        setupActionBar();

        //clickListener set in addImage view
        mBinding.ivAddDishImage.setOnClickListener(this);

        mBinding.etType.setOnClickListener(this);
        mBinding.etCategory.setOnClickListener(this);
        mBinding.etCookingTimeInMinutes.setOnClickListener(this);
        mBinding.addDishButton.setOnClickListener(this);
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
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.iv_add_dish_image:
                    pickImage();
                    return;

                case R.id.et_type:
                    customItemListDialog(getResources().getString(R.string.title_select_dish_type),
                            ConstantValue.dishType(),
                            DishType.toString());
                    Log.i("Type", "Working");

                    return;
                case R.id.et_category:
                    customItemListDialog(getResources().getString(R.string.title_select_dish_category),
                            ConstantValue.dishCategories(),
                            DishCategory.toString());
                    Log.i("Category", "Working");

                    return;
                case R.id.et_cooking_time_in_minutes:
                    customItemListDialog(getResources().getString(R.string.title_select_dish_cooking_time),
                            ConstantValue.dishCookTimes(),
                            DishCookingTime.toString());
                    Log.i("Cooking Time", "Working");
                    return;
                case R.id.add_dish_button:
                    dataSaveTaskByAddDishButton();
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
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY);
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
                    // mBinding.ivDishImage.setImageBitmap(bitmap);

                    Glide.with(this)
                            .load(bitmap)
                            .centerCrop()
                            .into(mBinding.ivDishImage);

                    //Image store under the package
                    imgPath = saveImage(bitmap);
                    Log.i("ImagePath", imgPath);

                    mBinding.ivAddDishImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_edit_24));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (requestCode == GALLERY) {
                try {
                    assert data != null;
                    Uri uri = data.getData();
                    // mBinding.ivDishImage.setImageURI(uri);

                    Glide.with(this)
                            .load(uri)
                            .centerCrop()
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    Log.i("ImagePath", "Error occur");
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                    if (isFirstResource) {
                                        //Image store under the package
                                        BitmapDrawable bd = (BitmapDrawable) resource;
                                        Bitmap bitmap = bd.getBitmap();
                                        imgPath = saveImage(bitmap);
                                        Log.i("ImagePath", imgPath);
                                    }

                                    return false;
                                }
                            })
                            .into(mBinding.ivDishImage);

                    mBinding.ivAddDishImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_edit_24));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } //#END

    //Picker Image Store to Internal storage.
    private String saveImage(Bitmap bitmapImg) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File dir = contextWrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE);
        dir = new File(dir, UUID.randomUUID() + ".jpg");

        try {
            OutputStream outputStream = new FileOutputStream(dir);
            bitmapImg.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dir.getAbsolutePath();
    }//#END


    //add popUp list Item on view
    private void customItemListDialog(String title, List<String> itemList, String selection) {
        DialogCustomListBinding dclBinding = DialogCustomListBinding.inflate(getLayoutInflater());
        dialog = new Dialog(this);
        dialog.setContentView(dclBinding.getRoot());

        dclBinding.tvTitle.setText(title);
        dclBinding.rvList.setLayoutManager(new LinearLayoutManager(this));
        dclBinding.rvList.setAdapter(new CustomListItemAdapter(AddActivity.this, itemList, selection));
        dialog.show();
    }//#END

    public void selectedItemInsertIntoInputField(String item, String selection) {
        ConstantValue s = ConstantValue.valueOf(selection);
        switch (s) {
            case DishType:
                mBinding.etType.setText(item);
                dialog.dismiss();
                return;
            case DishCategory:
                mBinding.etCategory.setText(item);
                dialog.dismiss();
                return;
            case DishCookingTime:
                mBinding.etCookingTimeInMinutes.setText(item);
                dialog.dismiss();
                return;
        }
    }//#END

    private void dataSaveTaskByAddDishButton() {
        String title = mBinding.etTitle.getText().toString().trim();
        String type = mBinding.etType.getText().toString().trim();
        String category = mBinding.etCategory.getText().toString().trim();
        String ingredients = mBinding.etIngredients.getText().toString().trim();
        String cookingTimeInMinutes = mBinding.etCookingTimeInMinutes.getText().toString().trim();
        String cookingDirection = mBinding.etDirectionToCook.getText().toString().trim();

        boolean inputStatus = true;

        if (TextUtils.isEmpty(imgPath)) {
            Toast.makeText(this, getResources().getString(R.string.err_msg_select_dish_image), Toast.LENGTH_SHORT).show();
            inputStatus = false;
            return;
        }
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, getResources().getString(R.string.err_msg_enter_dish_title), Toast.LENGTH_SHORT).show();
            inputStatus = false;
            return;
        }
        if (TextUtils.isEmpty(type)) {
            Toast.makeText(this, getResources().getString(R.string.err_msg_select_dish_type), Toast.LENGTH_SHORT).show();
            inputStatus = false;
            return;
        }
        if (TextUtils.isEmpty(category)) {
            Toast.makeText(this, getResources().getString(R.string.err_msg_select_dish_category), Toast.LENGTH_SHORT).show();
            inputStatus = false;
            return;
        }
        if (TextUtils.isEmpty(ingredients)) {
            Toast.makeText(this, getResources().getString(R.string.err_msg_enter_dish_ingredients), Toast.LENGTH_SHORT).show();
            inputStatus = false;
            return;
        }
        if (TextUtils.isEmpty(cookingTimeInMinutes)) {
            Toast.makeText(this, getResources().getString(R.string.err_msg_select_dish_cooking_time), Toast.LENGTH_SHORT).show();
            inputStatus = false;
            return;
        }
        if (TextUtils.isEmpty(cookingDirection)) {
            Toast.makeText(this, getResources().getString(R.string.err_msg_enter_dish_instructions), Toast.LENGTH_SHORT).show();
            inputStatus = false;
            return;
        }

        if(inputStatus){
            Toast.makeText(this,"All Entries are valid",Toast.LENGTH_SHORT).show();
        }
    }
}