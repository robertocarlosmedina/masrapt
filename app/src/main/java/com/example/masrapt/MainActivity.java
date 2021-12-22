package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        timer.schedule(new TimerTask() {
        @Override
        public void run() {
              Intent intent = new Intent(MainActivity.this, Dashboard_activity.class);
              startActivity(intent);
              finish();
          }
        }, 500);
    }
}