package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class About extends AppCompatActivity {

    private ImageView icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        icon_back = (ImageView) findViewById(R.id.back_icon);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginInfo();
            }
        });

    }
    public void openLoginInfo(){
        Intent intent = new Intent(About.this, MainActivity.class);
        startActivity(intent);
    }
}