package com.cookandroid.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class PickResult extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_result);

        TextView tvFoodPickResult;

        Intent intent = getIntent();

        ArrayList<list_item_food_pick> foodList = (ArrayList<list_item_food_pick>)intent.getSerializableExtra("FoodList");
        ArrayList<Float> foodPercentageList = (ArrayList<Float>)intent.getSerializableExtra("FoodPercentageList");

        tvFoodPickResult = (TextView)findViewById(R.id.tvFoodPickResult);
        String tempStr = "";
        for(int i = 0; i < foodList.size(); i++){
            tempStr += foodList.get(i).getFoodName();
            tempStr += foodPercentageList.get(i).toString();
            tempStr += "\n";
        }
        tvFoodPickResult.setText(tempStr);
    }
}
