package com.example.intern_task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView proList = findViewById(R.id.recyclerView);
        proList.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> dta = new ArrayList<String>();
        ArrayList<String> dta2 = new ArrayList<String>();
        ArrayList<byte[]> bitmap = new ArrayList<>();

        SQLiteDatabase myR = openOrCreateDatabase("DTA.sqlite",android.content.Context.MODE_PRIVATE,null);
        myR.execSQL("CREATE TABLE IF NOT EXISTS DTA(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title Varchar, description   Varchar, image BLOG)");
        Cursor D = myR.rawQuery("select * from DTA",null);
        while(D.moveToNext()){
            String title=(String) D.getString(1);
            String description=(String) D.getString(2);
            byte[] btmp = D.getBlob(3);
            dta.add(title);
            dta2.add(description);
            bitmap.add(btmp);


        }

        proList.setAdapter(new HomeAdapter(dta,dta2,bitmap));






    }

    public void home_new_note(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}