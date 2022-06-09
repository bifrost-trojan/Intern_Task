package com.example.intern_task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sign_in(View view) {
        // CHECK FOR MOBILE NUMBER
        Integer mobile_check = 0;
        final String regexStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        final EditText chmobno2 = (EditText)findViewById(R.id.main_email);
        String mb=chmobno2.getText().toString();
        System.out.println("helloji"+mb.length());
        if( mb.matches(regexStr)){
            mobile_check = 1;
        }
        else{
            chmobno2.setError("invalid");
        }

        // CHECK FOR EMAIL ID
        Integer email_check = 0;
        final EditText emailValidate = (EditText)findViewById(R.id.main_email);
        String email = emailValidate.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)) {
            email_check = 1;
        }
        else if(mobile_check == 0){
            emailValidate.setError("invalid");
        }

        // CHECK FOR PASSWORD
        Integer password_check = 0;
        final EditText passwordValidate = (EditText)findViewById(R.id.main_password);
        String pass = passwordValidate.getText().toString().trim();
        String passwordPattern = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})";
        if(pass.matches(passwordPattern) && passwordValidate.length()>7 && passwordValidate.length()<16){
            password_check = 1;
        }
        else{
            passwordValidate.setError("invalid");
        }

        if ((mobile_check == 1 || email_check == 1) && password_check == 1){
            // VALUES IN mb , email , pass
            //VERIFY IF DETAILS PRESENT IN DATABASE OR NOT
            SQLiteDatabase myR = openOrCreateDatabase("User_Details",android.content.Context.MODE_PRIVATE,null);
            myR.execSQL("CREATE TABLE IF NOT EXISTS data_base(UserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Name VARCHAR NOT NULL ,Mob_number Integer DEFAULT 0, email_id Varchar DEFAULT 0, password Varchar DEFAULT 0);");
            Cursor D = myR.rawQuery("select * from data_base",null);
            while(D.moveToNext()){
                String name_count=(String) D.getString(1);
                String mob_count=(String) D.getString(2);
                String email_count=(String) D.getString(3);
                String password_count=(String) D.getString(4);
                if((mob_count.equals(mb) || email_count.equals(email)) && password_count.equals(pass)){
                    Toast.makeText(getApplicationContext(),"Welcome "+name_count, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }

            }
        }
        else{
            Toast.makeText(getApplicationContext(),"INVALID DETAILS", Toast.LENGTH_SHORT).show();
        }
    }
    public void sign_up(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}