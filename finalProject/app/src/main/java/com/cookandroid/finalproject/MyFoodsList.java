package com.cookandroid.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyFoodsList extends AppCompatActivity {
    TextView tvMyFoodsList;
    ListView listViewMyFoodsList;

    ArrayList<list_item_pick> myFoodsList;

    MyFoodsListAdapter myFoodsListAdapter;

    SQLiteDatabase sqlDB;
    MyFoodsList.myDBHelper myDBHelper;

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
        setContentView(R.layout.activity_myfoods_list);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        myDBHelper = new myDBHelper(this);
        //tvMyFoodsList = (TextView)findViewById(R.id.tvMyFoodsList);
        listViewMyFoodsList =(ListView)findViewById(R.id.listViewMyFoodsList);
        sqlDB = myDBHelper.getReadableDatabase();

        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM pickTable where category = 'MyFoods'", null);

        myFoodsList = new ArrayList<list_item_pick>();

        String FoodsNames;
        while (cursor.moveToNext()) {
            FoodsNames = "";
            for(int i = 0; i < 6; i++){
                if(!cursor.getString(i+3).equals("null")) {
                    //ItemList.add(new list_item_pick(cursor.getColumnName(i)));
                    FoodsNames += cursor.getString(i+3) + "   ";
                }
            }
            myFoodsList.add(new list_item_pick(FoodsNames));
        }
        cursor.close();
        sqlDB.close();

        myFoodsListAdapter = new MyFoodsListAdapter(MyFoodsList.this, myFoodsList);
        listViewMyFoodsList.setAdapter(myFoodsListAdapter);
        //tvMyFoodsList.setText("test");
    }

    public class myDBHelper extends SQLiteOpenHelper {
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
