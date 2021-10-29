package com.frabbi.splashscreendemo.model.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavDish_Table")
public class FavDish {
    @PrimaryKey(autoGenerate = true)
    public int _id = 0;

    @ColumnInfo(name = "image_path")
    public String imagePath;

    @ColumnInfo(name = "image_source")
    public String image;

    @ColumnInfo
    public String title;

    @ColumnInfo
    public String type;

    @ColumnInfo
    public String category;

    @ColumnInfo
    public String ingredients;

    @ColumnInfo(name = "cooking_time")
    public  String cookingTime;

    @ColumnInfo(name = "instructions")
    public String directionToCook;

    @ColumnInfo(name = "favorite_dish")
    public Boolean favoriteDish = false;

    /**
     *
     * @param imagePath
     * @param image
     * @param title
     * @param type
     * @param category
     * @param ingredients
     * @param cookingTime
     * @param directionToCook
     * @param favoriteDish
     */

    public FavDish(String imagePath, String image, String title,
                   String type, String category, String ingredients,
                   String cookingTime, String directionToCook,
                   Boolean favoriteDish) {
        this.imagePath = imagePath;
        this.image = image;
        this.title = title;
        this.type = type;
        this.category = category;
        this.ingredients = ingredients;
        this.cookingTime = cookingTime;
        this.directionToCook = directionToCook;
        this.favoriteDish = favoriteDish;
    }
}
