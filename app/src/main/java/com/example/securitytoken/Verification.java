package com.example.securitytoken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Verification extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<String>();
    ListView listView;
    Button clearButton;
    int count;
    String item;
    static ArrayAdapter<String> adapter;
    static boolean isSaved = false;
    boolean adapterSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification);

        listView = (ListView) findViewById(R.id.listView1);
        clearButton = (Button) findViewById(R.id.passButton);

        for(String temp:MainActivity.arrayIntegerList) {
            arrayList.add(temp);
        }
        //adapter reset here? loses the saved time stamp
//        if(isSaved==true) //added
//        {
//            isSaved=false;
//            return;
//        }
        if(isSaved==false) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(adapter);
        }
        isSaved=false;

        clearButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                count = adapter.getCount();
                item = adapter.getItem(count-1);
                adapter.clear();
                adapter.add(item);
                saveTimeStamp();
            }
        });

    }

    public void saveTimeStamp()
    {
        int i = adapter.getCount();
        if(isSaved==true){
            isSaved=false;
            return;
        }

        String lastTimeStamp = adapter.getItem(i-1);

        if(!lastTimeStamp.isEmpty()) {
            MainActivity.editor.putString(MainActivity.TIMESTAMP_KEY, lastTimeStamp);
            MainActivity.editor.commit();
            Toast.makeText(getBaseContext(), "time stamp saved", Toast.LENGTH_SHORT).show();
            isSaved = true;
        }else{
            Toast.makeText(getBaseContext(), "nothing saved", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public static void restoreTimeStamp()
    {
        String lastTimeStamp = MainActivity.SavedTimeStamp.getString(MainActivity.TIMESTAMP_KEY, "NOT LOCATED");
        adapter.add(lastTimeStamp);

        //Toast.makeText(getBaseContext(), "time stamp restored", Toast.LENGTH_SHORT).show();
    }

}