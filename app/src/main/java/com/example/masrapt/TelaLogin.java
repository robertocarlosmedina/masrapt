package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TelaLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        getSupportActionBar().hide(); // p tra kel barra k t fca la dcima kel nome
    }
}