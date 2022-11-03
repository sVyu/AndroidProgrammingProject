package com.cookandroid.finalproject;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FoodPick extends AppCompatActivity{
    Button btnFoodAdd, btnMyFoodsRegister, btnMyFoodsList_enter, btnFoodPick_pick;
    ListView listViewFoodPick;
    AutoCompleteTextView autoComTextView;
    TextView tv_item;
    CheckBox cbFoodPick;

    SQLiteDatabase sqlDB;
    myDBHelper myDBHelper;

    MyItemListAdapter myItemListAdapter;
    ArrayList<list_item_pick> foodList;
    ArrayList<list_item_pick> foodListDBArray;

    ArrayList<Float> foodPercentageList;

    SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy/MM/dd, HH:mm:ss");
    Date time = new Date();
    String timeStr = dateFormat.format(time);

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_pick);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        btnFoodAdd = (Button)findViewById(R.id.btnFoodAdd);
        btnMyFoodsRegister = (Button)findViewById(R.id.btnMyFoodsRegister);
        btnMyFoodsList_enter = (Button)findViewById(R.id.btnMyFoodsList_enter);
        btnFoodPick_pick = (Button)findViewById(R.id.btnFoodpick_pick);

        listViewFoodPick = (ListView)findViewById(R.id.listViewFoodPick);
        tv_item = (TextView) findViewById(R.id.tv_item);
        cbFoodPick = (CheckBox)findViewById(R.id.cbFoodPick);
        autoComTextView = (AutoCompleteTextView)findViewById(R.id.autoComTextView);

        myDBHelper = new myDBHelper(this);

        String[] items = { "짜장", "짬뽕", "냉면", "test"};

        autoComTextView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, items));

        foodList = new ArrayList<list_item_pick>();
        foodList.add(new list_item_pick("짜장"));
        foodList.add(new list_item_pick("짬뽕"));
        foodList.add(new list_item_pick("탕수육"));

        foodListDBArray = new ArrayList<list_item_pick>();

        myItemListAdapter = new MyItemListAdapter(FoodPick.this, foodList);
        listViewFoodPick.setAdapter(myItemListAdapter);

        btnFoodAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoComTextView.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "음식명을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(foodList.size() >= 6){
                    Toast.makeText(getApplicationContext(), "최대 6개까지 가능합니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    foodList.add(new list_item_pick(autoComTextView.getText().toString()));
                    myItemListAdapter.notifyDataSetChanged();
                }
            }
        });

        // MyFoods 등록
        btnMyFoodsRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(FoodPick.this);

                dlg.setTitle("MyFoods");
                dlg.setMessage("해당 메뉴들로 MyFoods를 등록합니다");

                // 확인을 눌렀을 시 이벤트
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sqlDB = myDBHelper.getWritableDatabase();
                        myDBHelper.onUpgrade(sqlDB, 1, 2);
                        sqlDB.close();

                        foodList.size();
                        for(int i = 0; i < foodList.size(); i++){
                            foodListDBArray.add(foodList.get(i));
                        }
                        while(foodListDBArray.size()<6){
                            foodListDBArray.add(new list_item_pick("null"));
                        }

                        sqlDB = myDBHelper.getWritableDatabase();
                        //Toast.makeText(getApplicationContext(), String.valueOf(num), Toast.LENGTH_SHORT).show();
                        sqlDB.execSQL("INSERT INTO pickTable VALUES ('"
                                + timeStr + "', '"
                                + "MyFoods" + "', '"
                                + "null" + "', '"
                                + foodListDBArray.get(0).getItemName() + "', '"
                                + foodListDBArray.get(1).getItemName() + "', '"
                                + foodListDBArray.get(2).getItemName() + "', '"
                                + foodListDBArray.get(3).getItemName() + "', '"
                                + foodListDBArray.get(4).getItemName() + "', '"
                                + foodListDBArray.get(5).getItemName() + "', '"
                                + 0 + "', '"
                                + 0 + "', '"
                                + 0 + "', '"
                                + 0 + "', '"
                                + 0 + "', '"
                                + 0 + "');");
                        sqlDB.close();
                        Toast.makeText(getApplicationContext(), "MyFoods 등록이 완료되었습니다", Toast.LENGTH_SHORT).show();

                        foodListDBArray.clear();
                    }
                });

                // 취소를 눌렀을 시 이벤트
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
        btnMyFoodsList_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodPick.this, MyFoodsList.class);
                startActivity(intent);
            }
        });

        foodPercentageList = new ArrayList<Float>();

        btnFoodPick_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFoodPick.isChecked() == true){
                    Toast.makeText(FoodPick.this, "같은 확률", Toast.LENGTH_SHORT).show();

                    float tempPercentage = ((float)100 / foodList.size());
                    for(int i = 0; i < foodList.size(); i++){
                        foodPercentageList.add(tempPercentage);
                    }

                    // 인텐트 넘겨주어야 함
                    Intent intent = new Intent(FoodPick.this, PickResult.class);
                    intent.putExtra("ItemList", foodList);
                    intent.putExtra("ItemPercentageList", foodPercentageList);
                    intent.putExtra("category", "음식");
                    startActivity(intent);
                    foodPercentageList.clear();
                }
                else{
                    AlertDialog.Builder dlg = new AlertDialog.Builder(FoodPick.this);

                    dlg.setTitle("다른 확률 확인");
                    dlg.setMessage("각각의 확률을 지정합니다");

                    // 확인을 눌렀을 시 이벤트
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(FoodPick.this, PickDiffPercentage.class);
                            intent.putExtra("ItemList", foodList);
                            intent.putExtra("category", "음식");
                            startActivity(intent);
                        }
                    });

                    // 취소를 눌렀을 시 이벤트
                    dlg.setNegativeButton("취소", null);

                    dlg.show();
                }
            }
        });
    }

    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(@Nullable Context context) {
            super(context, "pickDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE pickTable ("
                    + "date char(20), category char(10), winning char(10),"
                    + "item1 char(10), item2 char(10), item3 char(10), item4 char(10), item5 char(10), item6 char(10),"
                    + "per1 float(10), per2 float(10), per3 float(10), per4 float(10), per5 float(10), per6 float(10));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(oldVersion < 1){
                db.execSQL("DROP TABLE IF EXISTS pickTable");
                onCreate(db);
            }
        }
    }
}
