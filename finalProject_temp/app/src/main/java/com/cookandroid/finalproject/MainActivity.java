package com.cookandroid.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button btnNumberPick, btnDicePick, btnFoodPick, btnChoicePick;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        btnNumberPick = (Button)findViewById(R.id.btnNumberPick);
        btnNumberPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumberPick.class);
                startActivity(intent);
            }
        });

        btnDicePick = (Button)findViewById(R.id.btnDicePick);
        btnDicePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });
    }
}