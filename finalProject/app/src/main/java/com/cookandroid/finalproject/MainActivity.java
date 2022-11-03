package com.cookandroid.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<list_item_pick> ItemList;
    ArrayList<Float> ItemPercentageList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_btn1:
                Intent intent = new Intent(MainActivity.this, PickLog.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button btnNumberPick, btnDicePick, btnFoodPick, btnChoicePick;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        ItemList = new ArrayList<list_item_pick>();
        ItemPercentageList = new ArrayList<Float>();

        btnNumberPick = (Button)findViewById(R.id.btnNumberPick);
        btnNumberPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumberPick_Input.class);
                startActivity(intent);
            }
        });

        btnDicePick = (Button)findViewById(R.id.btnDicePick);
        btnDicePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemList.add(new list_item_pick("1"));
                ItemList.add(new list_item_pick("2"));
                ItemList.add(new list_item_pick("3"));
                ItemList.add(new list_item_pick("4"));
                ItemList.add(new list_item_pick("5"));
                ItemList.add(new list_item_pick("6"));

                for(int i = 0; i < 6; i++){
                    ItemPercentageList.add( ((float)100 / 6) );
                }

                // 인텐트 넘겨주어야 함
                Intent intent = new Intent(MainActivity.this, PickResult.class);
                intent.putExtra("ItemList", ItemList);
                intent.putExtra("ItemPercentageList", ItemPercentageList);
                intent.putExtra("category","주사위 굴리기" );
                startActivity(intent);

                ItemList.clear();
                ItemPercentageList.clear();
            }
        });

        btnFoodPick = (Button)findViewById(R.id.btnFoodPick);
        btnFoodPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodPick.class);
                startActivity(intent);
            }
        });

        btnChoicePick = (Button)findViewById(R.id.btnChoicePick);
        btnChoicePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChoicePick.class);
                startActivity(intent);
            }
        });
    }
}