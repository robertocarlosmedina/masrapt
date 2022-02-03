package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelaLogin extends AppCompatActivity {

    private TextView signUp;
    private EditText name_or_email, password;
    private AppCompatButton btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        btn_login = (AppCompatButton) findViewById(R.id.btn_login);
        name_or_email = (EditText) findViewById(R.id.name_or_email);
        password = (EditText) findViewById(R.id.password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authUser();
                // getAPIData();
            }
        });

        signUp = (TextView) findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, TelaCadastro.class);
                startActivity(intent);
            }
        });
    }

    private void authUser(){
        String user_name_or_email = this.name_or_email.getText().toString();
        String user_password = this.password.getText().toString();

        if(!user_name_or_email.equals("") && !user_password.equals("")){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.api_base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AuthUserOnAPI authUserOnAPI = retrofit.create(AuthUserOnAPI.class);

            AuthUserModel authUserModel =  new AuthUserModel(user_name_or_email, user_password);

            Call<AuthUserModel> call = authUserOnAPI.authenticateUser(authUserModel);

            call.enqueue(new Callback<AuthUserModel>() {
                @Override
                public void onResponse(Call<AuthUserModel> call, Response<AuthUserModel> response) {
                    if (response.code() == 401){
                        Toast.makeText(TelaLogin.this, "User authentication fail",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (response.code() != 200){
                        Toast.makeText(TelaLogin.this, "Check your internet connection",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    saveLoggingDataOnSharePreferences(response.body().getId(), response.body().getName(),
                            response.body().getEmail());
                    Toast.makeText(TelaLogin.this, "User successfully logged", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<AuthUserModel> call, Throwable t) {
                    Toast.makeText(TelaLogin.this, "Your Authentication fail", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        Toast.makeText(TelaLogin.this, "The fields should not be empty",
                Toast.LENGTH_SHORT).show();
    }

    private void saveLoggingDataOnSharePreferences(int user_id, String username, String email) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "application", this.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_id", user_id);
        editor.putString("username", username);
        editor.putString("user_email", email);
        editor.apply();
        openDashboardActivity();
    }

    private void openDashboardActivity() {
        Intent intent = new Intent(TelaLogin.this, Dashboard_activity.class);
        startActivity(intent);
    }

}