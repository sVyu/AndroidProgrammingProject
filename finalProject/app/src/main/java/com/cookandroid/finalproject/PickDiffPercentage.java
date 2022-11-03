package com.cookandroid.finalproject;

import androidx.annotation.Dimension;
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

public class PickDiffPercentage extends AppCompatActivity {

    TextView tvPickDiffPercentage;
    GridLayout glEnterPercentage;
    Button btnDiffPercentage;
    EditText[] editTexts;
    String category;

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
        setContentView(R.layout.activity_pick_diff_percentage);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        tvPickDiffPercentage = (TextView)findViewById(R.id.tvPickDiffPercentage);
        glEnterPercentage = (GridLayout)findViewById(R.id.glEnterPercentage);
        btnDiffPercentage = (Button)findViewById(R.id.btnDiffPercentage);

        Intent intent = getIntent();

        ArrayList<list_item_pick> itemList = (ArrayList<list_item_pick>)intent.getSerializableExtra("ItemList");
        category = (String)intent.getStringExtra("category");

        int rows = 2;
        int columns = itemList.size();

        editTexts = new EditText[columns];
        ArrayList<Float> percentageLists = new ArrayList<Float>();

        final GridLayout gl = glEnterPercentage;
        gl.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                glEnterPercentage.setColumnCount(columns);
                int weight = glEnterPercentage.getWidth();
                int height = glEnterPercentage.getHeight();

                for(int j = 0; j < rows; j++){
                    for(int i = 0; i < columns; i++){
                        if(j == 0) {
                            TextView textview = new TextView(PickDiffPercentage.this);
                            glEnterPercentage.addView(textview);

                            textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            textview.setTextSize(15);

                            textview.setWidth(weight / columns);
                            textview.setHeight(height / rows);
                            textview.setText(itemList.get(i).getItemName().toString());
                        } else{
                            EditText editText = new EditText(PickDiffPercentage.this);
                            editTexts[i] = editText;
                            glEnterPercentage.addView(editTexts[i]);
                            editTexts[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            editTexts[i].setWidth(weight / columns);
                            editTexts[i].setHeight(height / rows);
                        }
                    }
                }
            }
        });

        btnDiffPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float sum = 0;
                int error_catch = 0;
                for(int i =0; i < columns; i++){
                    try {
                        sum += Float.parseFloat(editTexts[i].getText().toString());
                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(), "숫자를 잘 입력해주세요", Toast.LENGTH_SHORT).show();
                        error_catch = 1;
                        break;
                    }
                }
                if(error_catch == 0) {
                    if (sum != 100.0) {
                        Toast.makeText(getApplicationContext(), "확률의 합이 100이 되지 않습니다, " + sum, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Good !!! ", Toast.LENGTH_SHORT).show();
                        for(int i = 0; i < columns; i++){
                            percentageLists.add( Float.parseFloat(editTexts[i].getText().toString()) );
                        }
                        // 인텐트 넘겨주어야 함
                        Intent intent = new Intent(PickDiffPercentage.this, PickResult.class);
                        intent.putExtra("ItemList", itemList);
                        intent.putExtra("ItemPercentageList", percentageLists);
                        intent.putExtra("category", category);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
