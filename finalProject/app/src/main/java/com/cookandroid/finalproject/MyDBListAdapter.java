package com.cookandroid.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyDBListAdapter extends BaseAdapter {
    Context context;
    ArrayList<list_item_pick_db> itemList;

    TextView tvMyDBList_item,tvMyDBList_info;
    Button btnMyDBList_Delete;

    public MyDBListAdapter(Context context, ArrayList<list_item_pick_db> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return this.itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_myfoods_list,null);
        }
        //glMyFoodsList = (GridLayout)convertView.findViewById(R.id.glMyFoodsList);
        tvMyDBList_item = (TextView)convertView.findViewById(R.id.tvMyDBList_item);
        tvMyDBList_info = (TextView)convertView.findViewById(R.id.tvMyDBList_info);
        btnMyDBList_Delete = (Button)convertView.findViewById(R.id.btnMyDBList_Delete);

        tvMyDBList_item.setText(itemList.get(position).getItemName());

        btnMyDBList_Delete.setText(itemList.get(position).getItemInformation());

        btnMyDBList_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "DB 삭제 처리는 구현 한계로 미구현입니다", Toast.LENGTH_SHORT).show();
                itemList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
