package com.cookandroid.finalproject;

import java.io.Serializable;

public class list_item_food_pick implements Serializable {
    private String foodName;

    // Alt + Insert
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public list_item_food_pick(String foodName) {
        this.foodName = foodName;
    }
}
