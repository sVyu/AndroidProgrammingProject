package com.cookandroid.finalproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PickResult extends AppCompatActivity{

    TextView tvPickCategory, tvPickResult;
    ImageView ivDice;
    Button btnPickResult;

    int index_picked = 0;
    double tmpRandom = (Math.random() * 100);
    double tmpRatePrev = 0, tmpRateNext = 0;

    Integer imageId[] = {R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3,
                        R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6};

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_result);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        Intent intent = getIntent();

        ArrayList<list_item_pick> itemList = (ArrayList<list_item_pick>)intent.getSerializableExtra("ItemList");
        ArrayList<Float> itemPercentageList = (ArrayList<Float>)intent.getSerializableExtra("ItemPercentageList");
        String category = (String)intent.getStringExtra("category");

        tvPickCategory = (TextView)findViewById(R.id.tvPickCategory);
        tvPickResult = (TextView)findViewById(R.id.tvPickResult);
        ivDice = (ImageView)findViewById(R.id.ivDice);
        btnPickResult = (Button)findViewById(R.id.btnPickResult);

        tvPickCategory.setText(category);
        String tempStr = "";
        for(int i = 0; i < itemList.size(); i++){
            tempStr += itemList.get(i).getItemName() + " : ";
            tempStr += itemPercentageList.get(i).toString() + "%";
            tempStr += "\n";
        }
        tvPickResult.setText(tempStr);
        tvPickResult.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        btnPickResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 뽑기 구현, 참고 : https://kkkapuq.tistory.com/107
                // 자리수 https://url.kr/sn1yq9
                for(int i = 0; i < itemList.size(); i++){
                    if(tmpRandom == 100){
                        Toast.makeText(getApplicationContext(), "당첨! 100%!" + itemList.get(i).getItemName(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        float rate = itemPercentageList.get(i);
                        tmpRateNext = tmpRatePrev + rate;
                        if(tmpRandom > tmpRatePrev && tmpRandom < tmpRateNext){
                            Toast.makeText(getApplicationContext(), "당첨! " + itemList.get(i).getItemName() +"!\nRand: " + tmpRandom, Toast.LENGTH_SHORT).show();
                            index_picked = i;   // 당첨 item index

                            String tempStr2 = "";
                            if (category.equals("주사위 굴리기")) {
                                ivDice.setImageResource(imageId[index_picked]);
                            }
                            else{
                                // 당첨 표시
                                for(int index = 0; index < itemList.size(); index++){
                                    if(index == index_picked){
                                        tempStr2 += "★  ";
                                    }
                                    tempStr2 += itemList.get(index).getItemName() + " : ";
                                    tempStr2 += itemPercentageList.get(index).toString() + "%";
                                    if(index == index_picked){
                                        tempStr2 += "  ★";
                                    }
                                    tempStr2 += "\n";
                                }
                            }
                            tvPickResult.setText(tempStr2);
                            btnPickResult.setEnabled(false);
                            break;
                        }
                        else{
                            tmpRatePrev = tmpRateNext;
                        }
                    }
                }
            }
        });
    }
}
