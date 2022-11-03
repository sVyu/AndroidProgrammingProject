package com.cookandroid.finalproject;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;


public class FoodPick extends AppCompatActivity{

    /*
    class myAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return fruits.length;
        }

        @Override
        public Object getItem(int position) {
            return fruits[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = new TextView(getApplicationContext());
            view.setText(fruits[position]);

            return view;
        }
    }
    */

/*
    class myAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return foods.length;
        }

        @Override
        public Object getItem(int position) {
            return foods[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //TextView view = new TextView(getApplicationContext());
            TextView view = new TextView(FoodPick.this);
            view.setText(foods[position]);

            return view;
        }
    }*/

    /*
    class myAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return foodList.size();
        }

        @Override
        public Object getItem(int position) {
            return foodList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = new TextView(getApplicationContext());

            if(convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item,null);

            return view;
        }
    }*/
    Button btnFoodAdd, btnMyFoodsRegister, btnMyFoodsLoadDelete, btnFoodPick_pick ;
    ScrollView scrollViewFoodPick;
    ListView listViewFoodPick;
    ListView listViewFoodPickDialogue;
    EditText edtTextFood;
    CheckBox cbFoodPick;

    SQLiteDatabase sqlDB;
    FoodPick.myFoodListDBHelper myFoodListDBHelper;

    MyFoodListAdapter myFoodListAdapter;
    MyFoodListPercentageAdapter myFoodListPercentageAdapter;
    ArrayList<list_item_food_pick> foodList;
    ArrayList<list_item_food_pick> foodList2;
    ArrayList<list_item_food_pick> foodListDBArray;

    ArrayList<Float> foodPercentageList;

    View dialogView, toastView;
    //myAdapter adapter;
    //ArrayList<String> foodList;

    //String[] fruits = {"귤", "천혜향", "사과"};
    //String[] foods = {"짜장", "짬뽕", "탕수육"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_pick);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        btnFoodAdd = (Button)findViewById(R.id.btnFoodAdd);
        btnMyFoodsRegister = (Button)findViewById(R.id.btnMyFoodsRegister);
        btnFoodPick_pick = (Button)findViewById(R.id.btnFoodpick_pick);

        scrollViewFoodPick = (ScrollView)findViewById(R.id.scrollViewFoodPick);
        listViewFoodPick = (ListView)findViewById(R.id.listViewFoodPick);
        //listViewFoodPickDialogue = (ListView)findViewById(R.id.listViewFoodPickDialogue);
        edtTextFood = (EditText)findViewById(R.id.edtTxtFood);
        cbFoodPick = (CheckBox)findViewById(R.id.cbFoodPick);

        myFoodListDBHelper = new FoodPick.myFoodListDBHelper(this);

        foodList = new ArrayList<list_item_food_pick>();
        foodList.add(new list_item_food_pick("짜장"));
        foodList.add(new list_item_food_pick("짬뽕"));
        foodList.add(new list_item_food_pick("탕수육"));

        foodList2 = new ArrayList<list_item_food_pick>();
        foodList2.add(new list_item_food_pick("짬뽕"));
        foodList2.add(new list_item_food_pick("탕수육"));

        foodListDBArray = new ArrayList<list_item_food_pick>();

        myFoodListAdapter = new MyFoodListAdapter(FoodPick.this, foodList);
        listViewFoodPick.setAdapter(myFoodListAdapter);

        btnFoodAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(foodList.size() >= 5){
                    Toast.makeText(getApplicationContext(), "최대 5개까지 가능합니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    foodList.add(new list_item_food_pick("계란빵"));
                    myFoodListAdapter.notifyDataSetChanged();
                }
            }
        });

        btnMyFoodsRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myFoodListDBHelper.getWritableDatabase();
                myFoodListDBHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();

                sqlDB = myFoodListDBHelper.getWritableDatabase();
                foodList.size();
                for(int i = 0; i < foodList.size(); i++){
                    foodListDBArray.add(foodList.get(i));
                }
                while(foodListDBArray.size()<5){
                    foodListDBArray.add(new list_item_food_pick("null"));
                }

                //Toast.makeText(getApplicationContext(), String.valueOf(num), Toast.LENGTH_SHORT).show();
                sqlDB.execSQL("INSERT INTO groupFood(foodName1, foodName2, foodName3, foodName4, foodName5) VALUES ('"
                        + foodListDBArray.get(0).getFoodName() + "', '"
                        + foodListDBArray.get(1).getFoodName() + "', '"
                        + foodListDBArray.get(2).getFoodName() + "', '"
                        + foodListDBArray.get(3).getFoodName() + "', '"
                        + foodListDBArray.get(4).getFoodName() + "');");
                sqlDB.close();
                /*
                for(int i = 0 ; i < foodListDBArray.size(); i++){
                    Toast.makeText(getApplicationContext(), foodListDBArray.get(i).getFoodName(), Toast.LENGTH_SHORT).show();
                }*/
                foodListDBArray.clear();
            }
        });

        foodPercentageList = new ArrayList<Float>();

        //Button btnFoodPick = (Button)findViewById(R.id.btnFoodPick);
        //btnFoodPick.setText("Null");


        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.activity_food_pick_dialogue, null);
        listViewFoodPickDialogue = (ListView)header.findViewById(R.id.listViewFoodPickDialogue);

        myFoodListPercentageAdapter = new MyFoodListPercentageAdapter(FoodPick.this, foodList);
        listViewFoodPickDialogue.setAdapter(myFoodListPercentageAdapter);

        btnFoodPick_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbFoodPick.isChecked() == true){
                    Toast.makeText(FoodPick.this, "같은 확률", Toast.LENGTH_SHORT).show();

                    float tempPercentage = (float)(100 / foodList.size());
                    for(int i = 0; i < foodList.size(); i++){
                        foodPercentageList.add(tempPercentage);
                    }

                    // 인텐트 넘겨주어야 함
                    Intent intent = new Intent(FoodPick.this, PickResult.class);
                    intent.putExtra("FoodList", foodList);
                    intent.putExtra("FoodPercentageList", foodPercentageList);
                    startActivity(intent);
                    foodPercentageList.clear();
                }
                else{
                    Toast.makeText(FoodPick.this, "다른 확률", Toast.LENGTH_SHORT).show();

                    dialogView = (View) View.inflate(FoodPick.this, R.layout.activity_food_pick_dialogue, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(FoodPick.this);

                    dlg.setTitle("확률 입력");
                    dlg.setView(dialogView);

                    dlg.setPositiveButton("잉",null);
                    dlg.setNegativeButton("잉2", null);

                    dlg.show();
                }
            }
        });

    }

    public class myFoodListDBHelper extends SQLiteOpenHelper{
        public myFoodListDBHelper(@Nullable Context context) {
            super(context, "pickDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupFood (numId INTEGER PRIMARY KEY autoincrement, "+
                        "foodName1 char(10), foodName2 char(10), foodName3 char(10), foodName4 char(10), foodName5 char(10));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(oldVersion < 1){
                db.execSQL("DROP TABLE IF EXISTS groupFood");
                onCreate(db);
            }
        }
    }
}
