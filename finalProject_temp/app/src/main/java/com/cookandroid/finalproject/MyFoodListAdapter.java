package com.cookandroid.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MyFoodListAdapter extends BaseAdapter{
    Context context;
    ArrayList<list_item_food_pick> foodList;

    EditText edtTextFood;
    Button btnFoodDelete;

    public MyFoodListAdapter(Context context, ArrayList<list_item_food_pick> foodList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_food_pick,null);
        }
        edtTextFood = (EditText)convertView.findViewById(R.id.edtTxtFood);
        btnFoodDelete = (Button)convertView.findViewById(R.id.btnFoodDelete);

        edtTextFood.setText(foodList.get(position).getFoodName());
        // 리스트 아이템 삭제
        //btnFoodDelete = (Button)convertView.findViewById(R.id.btnFoodDelete);
        btnFoodDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
