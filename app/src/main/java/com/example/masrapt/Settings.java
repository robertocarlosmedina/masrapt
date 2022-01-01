package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private ImageView icon_back;
    private TextView change_password, delete_account, change_themes, cancel_txt;
    private Dialog theme_selector, delete_account_dialog;
    private Button yes_button, no_button;


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

        theme_selector = new Dialog(Settings.this);
        theme_selector.setContentView(R.layout.themes_selectors);
        theme_selector.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        theme_selector.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        theme_selector.setCancelable(true);

        cancel_txt = (TextView) theme_selector.findViewById(R.id.cancel_txt);
        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme_selector.hide();
            }
        });

        change_themes = (TextView) findViewById(R.id.theme_settings);
        change_themes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme_selector.show();
            }
        });

        change_password = (TextView) findViewById(R.id.txt_changePassword);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        delete_account_dialog = new Dialog(Settings.this);
        delete_account_dialog.setContentView(R.layout.delete_account_confirmation_dialog);
        delete_account_dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        delete_account_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        delete_account_dialog.setCancelable(true);

        delete_account = (TextView) findViewById(R.id.txt_deletePassword);
        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_account_dialog.show();
            }
        });

        yes_button = (Button) delete_account_dialog.findViewById(R.id.yes_button);
        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_account_dialog.hide();
                delete_user_account();
            }
        });

        no_button = (Button) delete_account_dialog.findViewById(R.id.no_button);
        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_account_dialog.hide();
            }
        });
    }

    public void delete_user_account(){
        Toast.makeText(Settings.this, "Your Account Successfully deleted", Toast.LENGTH_SHORT).show();
    }
    public void openLoginInfo(){
        Intent intent = new Intent(Settings.this, MainActivity.class);
        startActivity(intent);
    }
}