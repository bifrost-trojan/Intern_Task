package com.example.intern_task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void save_details(View view) {

        // CHECK FOR NAME
        Integer name_check = 0;
        final EditText name_n = (EditText)findViewById(R.id.signup_name);
        String nname = name_n.getText().toString();
        if(nname.length()!=0){
            name_check = 1;
        }
        else{
            name_n.setError("invalid");
        }


        // CHECK FOR MOBILE NUMBER
        Integer mobile_check = 0;
        final String regexStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        final EditText chmobno = (EditText)findViewById(R.id.signup_phone_number);
        String mb=chmobno.getText().toString();
        System.out.println("helloji"+mb.length());
        if( mb.matches(regexStr)){
            mobile_check = 1;
        }
        else{
            //if(mb.length()!=0) {
                chmobno.setError("invalid");
            //}
            //Toast.makeText(SignupActivity.this, "Please enter Valid Mobile Number", Toast.LENGTH_LONG).show();
        }

        // CHECK FOR EMAIL ID
        Integer email_check = 0;
        final EditText emailValidate = (EditText)findViewById(R.id.signup_email);
        String email = emailValidate.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)) {
            email_check = 1;
        }
        else {
            //if(email.length()!=0) {
                emailValidate.setError("invalid");
            //}
            //Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
        }

        // CHECK FOR PASSWORD
        Integer password_check = 0;
        final EditText passwordValidate = (EditText)findViewById(R.id.signup_password);
        String pass = passwordValidate.getText().toString().trim();
        String passwordPattern = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})";
        if(pass.matches(passwordPattern) && passwordValidate.length()>7 && passwordValidate.length()<16){
            password_check = 1;
        }
        else{
            passwordValidate.setError("invalid");
        }


        // FINAL VARIABLES nname , mb , email , pass
        if( name_check == 1 && mobile_check == 1 && email_check == 1 && password_check == 1){

            // DATABASE CODE - START
            SQLiteDatabase myR = openOrCreateDatabase("User_Details",android.content.Context.MODE_PRIVATE,null);
            myR.execSQL("CREATE TABLE IF NOT EXISTS data_base(UserID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Name VARCHAR NOT NULL ,Mob_number Integer DEFAULT 0, email_id Varchar DEFAULT 0, password Varchar DEFAULT 0);");
            //Cursor D = myR.rawQuery("select * from data_base",null);
            ContentValues values = new ContentValues();
            values.put("Name", nname);
            values.put("Mob_number",mb);
            values.put("email_id",email);
            values.put("password",pass);
            SQLiteDatabase myR2 = openOrCreateDatabase("User_Details",MODE_PRIVATE,null);
            myR2.insert("data_base",null,values);
            // DATABASE CODE - END

            Toast.makeText(SignupActivity.this, "Details Save Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(SignupActivity.this, "Incorrect Details", Toast.LENGTH_LONG).show();
        }

    }
}