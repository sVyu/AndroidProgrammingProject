package com.cookandroid.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MyItemListAdapter extends BaseAdapter{
    Context context;
    ArrayList<list_item_pick> itemList;

    TextView tv_item;
    Button btnItemDelete;

    public MyItemListAdapter(Context context, ArrayList<list_item_pick> foodList) {
        this.context = context;
        this.itemList = foodList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pick,null);
        }
        tv_item = (TextView) convertView.findViewById(R.id.tv_item);
        btnItemDelete = (Button)convertView.findViewById(R.id.btnItemDelete);

        tv_item.setText(itemList.get(position).getItemName());
        btnItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
