package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePassword extends AppCompatActivity {

    private EditText current_password, new_password, confirm_password;
    private AppCompatButton btn_update_password;
    private ImageView icon_back;
    private int user_id;
    private String username, user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        icon_back = (ImageView) findViewById(R.id.back_icon);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingsIntent();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        current_password = (EditText) findViewById(R.id.Current_password);
        new_password = (EditText) findViewById(R.id.New_password);
        confirm_password = (EditText) findViewById(R.id.Confirm_password);

        btn_update_password = (AppCompatButton) findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInformation();
            }
        });

        getUserInfo();
    }

    private void getUserInfo() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("application",
                Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt("user_id", -1);
        username = sharedPreferences.getString("username", "");
        user_email = sharedPreferences.getString("user_email", "");
    }

    private void checkInformation() {
        if(current_password.getText().toString().equals("") || new_password.getText().toString().equals("") ||
                confirm_password.getText().toString().equals("")){
            Toast.makeText(ChangePassword.this, "The fields should not be empty",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!new_password.getText().toString().equals(confirm_password.getText().toString())){
            Toast.makeText(ChangePassword.this, "The passwords do not match",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            if(user_id == -1){
                Toast.makeText(ChangePassword.this, "There is no logged user",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                authenticatingUser();
            }
        }
    }

    private void authenticatingUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthUserOnAPI authUserOnAPI = retrofit.create(AuthUserOnAPI.class);

        AuthUserModel authUserModel =  new AuthUserModel(username, current_password.getText().toString());

        Call<AuthUserModel> call = authUserOnAPI.authenticateUser(authUserModel);

        call.enqueue(new Callback<AuthUserModel>() {
            @Override
            public void onResponse(Call<AuthUserModel> call, Response<AuthUserModel> response) {
                if (response.code() == 401){
                    Toast.makeText(ChangePassword.this, "User authentication fail",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                else if (response.code() != 200){
                    Toast.makeText(ChangePassword.this, "Error while making the Authentication",
                            Toast.LENGTH_LONG).show();

                    return;
                }
                changingUserPassword();
            }

            @Override
            public void onFailure(Call<AuthUserModel> call, Throwable t) {
                Toast.makeText(ChangePassword.this, "Your Authentication fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changingUserPassword() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthUserOnAPI authUserOnAPI = retrofit.create(AuthUserOnAPI.class);

        AuthUserModel authUserModel =  new AuthUserModel(user_id, new_password.getText().toString());

        Call<AuthUserModel> call = authUserOnAPI.editUserPassword(authUserModel);

        call.enqueue(new Callback<AuthUserModel>() {
            @Override
            public void onResponse(Call<AuthUserModel> call, Response<AuthUserModel> response) {
                if (response.code() == 401){
                    Toast.makeText(ChangePassword.this, "User authentication fail",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (response.code() != 200){
                    Toast.makeText(ChangePassword.this, "Error while making the Authentication",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ChangePassword.this, "Password successfully changed",
                        Toast.LENGTH_SHORT).show();
                startSettingsIntent();
            }

            @Override
            public void onFailure(Call<AuthUserModel> call, Throwable t) {
                Toast.makeText(ChangePassword.this, "Your Authentication fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startSettingsIntent() {
        Intent intent = new Intent(ChangePassword.this, Settings.class);
        startActivity(intent);
    }
}