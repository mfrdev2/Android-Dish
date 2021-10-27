package com.frabbi.splashscreendemo.utils;

import java.util.ArrayList;
import java.util.List;

public enum ConstantValue {
    DishType,DishCategory,DishCookingTime;

    public static List<String>  dishType(){
        List<String>  list = new ArrayList<>();
        list.add("Breakfast");
        list.add("Lunch");
        list.add("Snacks");
        list.add("Dinner");
        list.add("Salad");
        list.add("Side dish");
        list.add("Dessert");
        list.add("Other");
        return list;
    }

    public static List<String>  dishCategories(){
        List<String>  list = new ArrayList<>();
        list.add("Pizza");
        list.add("BBQ");
        list.add("Bakery");
        list.add("Burger");
        list.add("Cafe");
        list.add("Chicken");
        list.add("Drinks");
        list.add("Hot Dogs");
        list.add("Juices");
        list.add("Sandwich");
        list.add("Tea & Coffee");
        list.add("Wraps");
        list.add("Other");
        return list;
    }

    public static List<String>  dishCookTimes(){
        List<String>  list = new ArrayList<>();
        list.add("10");
        list.add("15");
        list.add("20");
        list.add("30");
        list.add("45");
        list.add("50");
        list.add("60");
        list.add("90");
        list.add("120");
        list.add("150");
        list.add("180");
        return list;
    }
}
