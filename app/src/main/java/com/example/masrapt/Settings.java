package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Settings extends AppCompatActivity {

    private ImageView icon_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        icon_back = (ImageView) findViewById(R.id.back_icon);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginInfo();
            }
        });

    }
    public void openLoginInfo(){
        Intent intent = new Intent(Settings.this, MainActivity.class);
        startActivity(intent);
    }
}