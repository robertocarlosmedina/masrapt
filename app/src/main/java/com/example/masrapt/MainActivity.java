package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    private String app_theme;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkThemeOnSharedPreferences();

        timer = new Timer();
        timer.schedule(new TimerTask() {
        @Override
        public void run() {
              Intent intent = new Intent(MainActivity.this, Dashboard_activity.class);
              startActivity(intent);
              finish();
          }
        }, 1000);
    }

    private void checkThemeOnSharedPreferences() {
        sharedPreferences = this.getSharedPreferences(
                "application", this.MODE_PRIVATE
        );

        app_theme = sharedPreferences.getString("appTheme", "Default System");
        editor = sharedPreferences.edit();

        if(app_theme.equals("Dark Theme")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putString("appTheme", "Dark Theme");
            editor.apply();
        }
        else if(app_theme.equals("Night Theme")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putString("appTheme", "Night Theme");
            editor.apply();
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            editor.putString("appTheme", "Default System");
            editor.apply();
        }
    }
}