package com.cookandroid.finalproject;

import java.io.Serializable;

public class list_item_pick implements Serializable {
    private String itemName;

    // Alt + Insert
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public list_item_pick(String itemName) {
        this.itemName = itemName;
    }
}
