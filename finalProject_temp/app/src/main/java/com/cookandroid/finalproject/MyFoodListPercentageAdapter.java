package com.cookandroid.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MyFoodListPercentageAdapter extends BaseAdapter{
    Context context;
    ArrayList<list_item_food_pick> foodList;

    TextView tvFoodPick;
    EditText edtFoodPickPercentage;

    public MyFoodListPercentageAdapter(Context context, ArrayList<list_item_food_pick> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @Override
    public int getCount() {
        return this.foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_food_pick_percentage,null);
        }
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tvFoodPick = (TextView)convertView.findViewById(R.id.tvFoodPick);
        edtFoodPickPercentage = (EditText)convertView.findViewById(R.id.edtFoodPickPercentage);

        tvFoodPick.setText(foodList.get(position).getFoodName());
        edtFoodPickPercentage.setText("0");

        return convertView;
    }
}

