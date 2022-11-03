package com.cookandroid.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NumberPick extends AppCompatActivity {
    Button btnNumberPick_pick;
    SQLiteDatabase sqlDB;
    myDBHelper myHelper;
    Integer num;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_pick);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        myHelper = new myDBHelper(this);

        btnNumberPick_pick = (Button)findViewById(R.id.btnNumberPick_pick);
        btnNumberPick_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();

                sqlDB = myHelper.getWritableDatabase();
                num = ((int)(Math.random()*10)+1);

                SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy/MM/dd, HH:mm:ss");
                Date time = new Date();
                String timeStr = dateFormat.format(time);

                //Toast.makeText(getApplicationContext(), String.valueOf(num), Toast.LENGTH_SHORT).show();
                sqlDB.execSQL("INSERT INTO pickTable VALUES ('"
                        + timeStr + "', '"
                        + "숫자뽑기" + "', '"
                        + num.toString() + "', '"
                        + num.toString() + "', '"
                        + "null" + "', '"
                        + "null" + "', '"
                        + "null" + "', '"
                        + "null" + "', '"
                        + "null" + "', '"
                        + ((float)1/10) + "', '"
                        + 0 + "', '"
                        + 0 + "', '"
                        + 0 + "', '"
                        + 0 + "', '"
                        + 0 + "');");
                sqlDB.close();

                Toast.makeText(getApplicationContext(), String.valueOf(num), Toast.LENGTH_SHORT).show();
                finish();
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

