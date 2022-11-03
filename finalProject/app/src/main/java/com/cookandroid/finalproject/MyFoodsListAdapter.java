package com.cookandroid.finalproject;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyFoodsListAdapter extends BaseAdapter {
    Context context;
    ArrayList<list_item_pick> myFoodsList;

    //GridLayout glMyFoodsList;
    TextView tvMyFoodsList_item;
    Button btnMyFoodsList_Load, btnMyFoodsList_Delete;

    public MyFoodsListAdapter(Context context, ArrayList<list_item_pick> myFoodsList) {
        this.context = context;
        this.myFoodsList = myFoodsList;
    }

    @Override
    public int getCount() {
        return this.myFoodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.myFoodsList.get(position);
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
        tvMyFoodsList_item = (TextView)convertView.findViewById(R.id.tvMyFoodsList_item);
        btnMyFoodsList_Load = (Button)convertView.findViewById(R.id.btnMyFoodsList_Load);
        btnMyFoodsList_Delete = (Button)convertView.findViewById(R.id.btnMyFoodsList_Delete);

        /*
        int col = 0;
        for(int i = 0; i < 6; i++){
            if(!myFoodsList.get(position).getItemName()[i].equals("null")){
                col++;
            }
            else{
                break;
            }
        }*/

        //final int columns = col;
        /*final GridLayout gl = glMyFoodsList;
        gl.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                glMyFoodsList.setColumnCount(columns);
                int weight = glMyFoodsList.getWidth();
                int height = glMyFoodsList.getHeight();

                    for(int i = 0; i < columns; i++){
                        TextView textview = new TextView(context);
                        glMyFoodsList.addView(textview);

                        textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textview.setTextSize(15);

                        textview.setText(myFoodsList.get(position).getItemName()[i].toString());
                    }
            }
        });*/
        tvMyFoodsList_item.setText(myFoodsList.get(position).getItemName());

        btnMyFoodsList_Load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "불러오기는 구현 한계로 미구현입니다", Toast.LENGTH_SHORT).show();
            }
        });

        btnMyFoodsList_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "DB 삭제 처리는 구현 한계로 미구현입니다", Toast.LENGTH_SHORT).show();
                myFoodsList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
