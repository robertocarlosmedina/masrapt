package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Settings extends AppCompatActivity {

    private ImageView icon_back;
    private TextView change_password, delete_account, change_themes, cancel_txt, selected_theme;
    private Dialog theme_selector, delete_account_dialog;
    private Button yes_button, no_button;
    private RadioGroup themes_radio_group;
    private int user_id;
    private String username, user_email, app_theme;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "application", this.MODE_PRIVATE
        );
        editor = sharedPreferences.edit();

        selected_theme = (TextView) findViewById(R.id.selected_theme);

        icon_back = (ImageView) findViewById(R.id.back_icon);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDashboardIntent();
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

        themes_radio_group = (RadioGroup) theme_selector.findViewById(R.id.theme_options);

        themes_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.light_theme:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor.putString("appTheme", "Night Theme");
                        editor.apply();
                        selected_theme.setText("Night Theme");
                        Toast.makeText(Settings.this, "Light themes enabled",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.dark_theme:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putString("appTheme", "Dark Theme");
                        editor.apply();
                        selected_theme.setText("Dark Theme");
                        Toast.makeText(Settings.this, "Dark themes enabled",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.system_theme:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        editor.putString("appTheme", "Default System");
                        editor.apply();
                        selected_theme.setText("Default System");
                        Toast.makeText(Settings.this, "System default themes enabled",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
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
                if(user_id == -1){
                    Toast.makeText(Settings.this, "There is no logged user",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    delete_user_account();
                }
            }
        });

        no_button = (Button) delete_account_dialog.findViewById(R.id.no_button);
        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_account_dialog.hide();
            }
        });

        getUserInfo();
        setThemeNameAccordingSharedPreferences();
    }

    private void setThemeNameAccordingSharedPreferences() {
        selected_theme.setText(app_theme);
    }


    private void getUserInfo() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("application",
                Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt("user_id", -1);
        username = sharedPreferences.getString("username", "");
        user_email = sharedPreferences.getString("user_email", "");
        app_theme = sharedPreferences.getString("appTheme", "");
    }

    public void delete_user_account(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthUserOnAPI authUserOnAPI = retrofit.create(AuthUserOnAPI.class);

        AuthUserModel authUserModel =  new AuthUserModel(user_id);

        Call<AuthUserModel> call = authUserOnAPI.deleteUserData(authUserModel);

        call.enqueue(new Callback<AuthUserModel>() {
            @Override
            public void onResponse(Call<AuthUserModel> call, Response<AuthUserModel> response) {
                Toast.makeText(Settings.this, "Account Successfully deleted",
                        Toast.LENGTH_SHORT).show();
                clearSharedPreferences();
            }

            @Override
            public void onFailure(Call<AuthUserModel> call, Throwable t) {
                Toast.makeText(Settings.this, "Account Successfully deleted",
                        Toast.LENGTH_SHORT).show();
                clearSharedPreferences();
            }
        });
    }

    private void startDashboardIntent() {
        Intent intent = new Intent(Settings.this, Dashboard_activity.class);
        startActivity(intent);
    }

    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "application", this.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user_id").commit();
        editor.remove("username").commit();
        editor.remove("user_email").commit();
        editor.apply();

        startDashboardIntent();
    }
}