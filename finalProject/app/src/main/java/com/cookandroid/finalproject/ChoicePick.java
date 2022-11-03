package com.cookandroid.finalproject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ChoicePick extends AppCompatActivity{
    Button btnItemAdd, btnChoicePick_pick;
    ListView listViewItemPick;
    EditText etChoicePick_category, etChoicePick_item;
    TextView tv_item;
    CheckBox cbChoicePick;

    MyItemListAdapter myItemListAdapter;
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
                Intent intent = new Intent(ChoicePick.this, PickLog.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_pick);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.home);

        btnItemAdd = (Button)findViewById(R.id.btnItemAdd);
        btnChoicePick_pick = (Button)findViewById(R.id.btnChoicePick_pick);

        listViewItemPick = (ListView)findViewById(R.id.listViewItemPick);
        tv_item = (TextView) findViewById(R.id.tv_item);
        cbChoicePick = (CheckBox)findViewById(R.id.cbChoicePick);
        etChoicePick_category = (EditText) findViewById(R.id.etChoicePick_category);
        etChoicePick_item = (EditText) findViewById(R.id.etChoicePick_item);

        ItemList = new ArrayList<list_item_pick>();
        ItemList.add(new list_item_pick("item1"));
        ItemList.add(new list_item_pick("item2"));

        myItemListAdapter = new MyItemListAdapter(ChoicePick.this, ItemList);
        listViewItemPick.setAdapter(myItemListAdapter);

        btnItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etChoicePick_item.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "item명을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(ItemList.size() >= 5){
                    Toast.makeText(getApplicationContext(), "최대 5개까지 가능합니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    ItemList.add(new list_item_pick(etChoicePick_item.getText().toString()));
                    myItemListAdapter.notifyDataSetChanged();
                }
            }
        });

        ItemPercentageList = new ArrayList<Float>();

        btnChoicePick_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etChoicePick_category.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "category명을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(cbChoicePick.isChecked() == true){
                        Toast.makeText(ChoicePick.this, "같은 확률", Toast.LENGTH_SHORT).show();

                        float tempPercentage = ((float)100 / ItemList.size());
                        for(int i = 0; i < ItemList.size(); i++){
                            ItemPercentageList.add(tempPercentage);
                        }

                        // 인텐트 넘겨주어야 함
                        Intent intent = new Intent(ChoicePick.this, PickResult.class);
                        intent.putExtra("ItemList", ItemList);
                        intent.putExtra("ItemPercentageList", ItemPercentageList);
                        intent.putExtra("category",etChoicePick_category.getText().toString() );
                        startActivity(intent);
                        ItemPercentageList.clear();
                    }
                    else{
                        AlertDialog.Builder dlg = new AlertDialog.Builder(ChoicePick.this);

                        dlg.setTitle("다른 확률 확인");
                        dlg.setMessage("각각의 확률을 지정합니다");

                        // 확인을 눌렀을 시 이벤트
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ChoicePick.this, PickDiffPercentage.class);
                                intent.putExtra("ItemList", ItemList);
                                intent.putExtra("category",etChoicePick_category.getText().toString() );
                                startActivity(intent);
                            }
                        });

                        // 취소를 눌렀을 시 이벤트
                        dlg.setNegativeButton("취소", null);

                        dlg.show();
                    }
                }
            }
        });
    }
}
