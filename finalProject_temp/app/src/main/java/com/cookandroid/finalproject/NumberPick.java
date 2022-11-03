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
                //Toast.makeText(getApplicationContext(), String.valueOf(num), Toast.LENGTH_SHORT).show();
                sqlDB.execSQL("INSERT INTO groupNumber (numValue) VALUES ('"
                        + num +"');");
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
            db.execSQL("CREATE TABLE groupNumber (numId INTEGER PRIMARY KEY autoincrement, numValue INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(oldVersion < 1){
                db.execSQL("DROP TABLE IF EXISTS groupNumber");
                onCreate(db);
            }
        }
    }
}

