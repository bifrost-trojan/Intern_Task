package com.example.intern_task;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddNoteActivity extends AppCompatActivity {

    EditText addnote_title, addnote_detials;
    ImageView addnote_imageview;
    Button addnote_save,addnote_addimage;

    final int REQUEST_CODE_GALLERY = 999;

    public static AddNoteActivity_SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        init();

        sqLiteHelper = new AddNoteActivity_SQLiteHelper(this, "DTA.sqlite",null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS DTA(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title Varchar, description Varchar, image BLOG)");

        addnote_addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddNoteActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        addnote_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = addnote_title.getText().toString().trim();
                String detial = addnote_detials.getText().toString().trim();
                Integer c1=0;
                Integer c2=0;
                if(title.length()<5 || title.length()>100){
                    addnote_title.setError("invalid");
                }
                else{
                    c1=1;
                }

                if(detial.length()<100 || detial.length()>1000 ){
                    addnote_detials.setError("invalid");
                }
                else{
                    c2=1;
                }
                if(c1==1 && c2==1){
                    try{
                        sqLiteHelper.insertData(
                                addnote_title.getText().toString().trim(),
                                addnote_detials.getText().toString().trim(),
                                imageViewToByte(addnote_imageview)
                        );
                        Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();


                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    Intent inten2t = new Intent(AddNoteActivity.this, HomeActivity.class);
                    startActivity(inten2t);
                }

            }
        });
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            return;
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                addnote_imageview.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void init(){
        addnote_title = findViewById(R.id.addnote_title);
        addnote_detials = findViewById(R.id.addnote_description);
        addnote_imageview = findViewById(R.id.addnote_imageview);
        addnote_save = findViewById(R.id.addnote_save);
        addnote_addimage =findViewById(R.id.addnote_addimage);
    }


}