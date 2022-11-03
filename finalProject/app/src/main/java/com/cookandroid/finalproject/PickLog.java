package com.cookandroid.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class PickLog extends AppCompatActivity implements ActionBar.TabListener{

    ActionBar.Tab tabLogDefault, tabLogSearch;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_log);

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        tabLogDefault = bar.newTab();
        tabLogDefault.setText("기본 로그");
        tabLogDefault.setTabListener(this);
        bar.addTab(tabLogDefault);

        tabLogSearch = bar.newTab();
        tabLogSearch.setText("범주 내 검색");
        tabLogSearch.setTabListener(this);
        bar.addTab(tabLogSearch);
    }

    MyTabFragment myFrags[] = new MyTabFragment[2];

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        MyTabFragment myTabFrag = null;
        if(myFrags[tab.getPosition()] == null){
            myTabFrag = new MyTabFragment();
            Bundle data = new Bundle();
            data.putString("tabName", tab.getText().toString());
            myTabFrag.setArguments(data);
            myFrags[tab.getPosition()] = myTabFrag;
        }
        else{
            myTabFrag = myFrags[tab.getPosition()];
        }
        ft.replace(android.R.id.content, myTabFrag);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public static class MyTabFragment extends Fragment {
        String tabName;

        SQLiteDatabase sqlDB;
        myDBHelper myDBHelper;

        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            Bundle data = getArguments();
            tabName = data.getString("tabName");
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            LinearLayout baseLayout = new LinearLayout(super.getActivity());
            baseLayout.setOrientation(LinearLayout.VERTICAL);
            baseLayout.setLayoutParams(params);

            if(tabName=="기본 로그") {
                ListView listViewPickLogDefault;
                MyDBListAdapter myDBListAdapter;
                View view = inflater.inflate(R.layout.activity_pick_log_default, null);
                listViewPickLogDefault = (ListView)view.findViewById(R.id.listViewPickLogDefault);

                myDBHelper = new PickLog.myDBHelper(getContext());
                //baseLayout.setBackgroundColor(Color.RED);
                sqlDB = myDBHelper.getReadableDatabase();

                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM pickTable;", null);

                ArrayList<list_item_pick_db> itemList = new ArrayList<list_item_pick_db>();

                String ItemNames, ItemInfo;
                while (cursor.moveToNext()) {
                    ItemNames = "";
                    ItemInfo = "";
                    for(int i = 0; i < 6; i++){
                        if(!cursor.getString(i+3).equals("null")) {
                            //ItemList.add(new list_item_pick(cursor.getColumnName(i)));
                            ItemNames += cursor.getString(i+3) + "   ";
                        }
                    }
                    ItemNames +="\n";
                    for(int i = 0; i < 6; i++) {
                        if (!cursor.getString(i + 9).equals("null")) {
                            //ItemList.add(new list_item_pick(cursor.getColumnName(i)));
                            ItemNames += cursor.getString(i + 9) + "   ";
                        }
                    }
                    ItemInfo += cursor.getString(0) + ", " +cursor.getString(1);

                    itemList.add(new list_item_pick_db(ItemNames, ItemInfo));
                }
                cursor.close();
                sqlDB.close();

                myDBListAdapter = new MyDBListAdapter(getContext(), itemList);
                listViewPickLogDefault.setAdapter(myDBListAdapter);

                inflater.inflate(R.layout.activity_pick_log_default, baseLayout,true);
            }
            if(tabName == "범주 내 검색") baseLayout.setBackgroundColor(Color.GREEN);

            return baseLayout;
        }
    }

    public static class myDBHelper extends SQLiteOpenHelper {
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
