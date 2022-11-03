package com.cookandroid.finalproject;

public class list_item_pick_db {
    private String itemName;
    private String itemInformation;

    public list_item_pick_db(String itemName, String itemInformation) {
        this.itemName = itemName;
        this.itemInformation = itemInformation;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemInformation() {
        return itemInformation;
    }

    public void setItemInformation(String itemInformation) {
        this.itemInformation = itemInformation;
    }

}
