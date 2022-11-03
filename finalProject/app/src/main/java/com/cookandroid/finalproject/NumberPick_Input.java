package com.cookandroid.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumberPick_Input extends AppCompatActivity {
    EditText etNumberPickInput;
    Button btnNumberPickInput;

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
                Intent intent = new Intent(getApplicationContext(), PickLog.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_pick_input);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        etNumberPickInput = (EditText)findViewById(R.id.etNumberPickInput);
        btnNumberPickInput = (Button)findViewById(R.id.btnNumberPickInput);

        ItemList = new ArrayList<list_item_pick>();
        ItemPercentageList = new ArrayList<Float>();

        btnNumberPickInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float sum = 0;
                int error_catch = 0;
                int num_limit = 0;
                try {
                    num_limit = Integer.parseInt(etNumberPickInput.getText().toString());
                }
                catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "값을 제대로 입력해주세요", Toast.LENGTH_SHORT).show();
                    error_catch = 1;
                }

                if(error_catch == 0) {
                    if (num_limit > 6 || num_limit < 0) {
                        Toast.makeText(getApplicationContext(), "값이 범위를 벗어납니다", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i = 0; i < num_limit; i++){
                            ItemList.add(new list_item_pick(Integer.toString(i)));
                            ItemPercentageList.add( ((float)100 / num_limit) );
                        }

                        // 인텐트 넘겨주어야 함
                        Intent intent = new Intent(NumberPick_Input.this, PickResult.class);
                        intent.putExtra("ItemList", ItemList);
                        intent.putExtra("ItemPercentageList", ItemPercentageList);
                        intent.putExtra("category","숫자 뽑기" );
                        startActivity(intent);

                        ItemList.clear();
                        ItemPercentageList.clear();
                    }
                }
            }
        });
    }
}
