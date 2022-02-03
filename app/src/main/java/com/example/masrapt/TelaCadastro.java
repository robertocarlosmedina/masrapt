package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelaCadastro extends AppCompatActivity {

    private EditText username, user_email, password, password_confirmation;
    private AppCompatButton btn_registe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.print("ok");
        setContentView(R.layout.activity_tela_cadastro);
    }

    @Override
    protected void onStart() {
        super.onStart();
        username = (EditText) findViewById(R.id.user);
        user_email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password_confirmation = (EditText) findViewById(R.id.password_confirmation);

        btn_registe = (AppCompatButton) findViewById(R.id.btn_registe);
        btn_registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

    }

    private void registerNewUser() {
        if(user_email.getText().toString().equals("") || username.getText().toString().equals("") ||
                password.getText().toString().equals("") || password_confirmation.getText().toString().equals("")){
            Toast.makeText(TelaCadastro.this, "The fields should not be empty",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!password.getText().toString().equals(password_confirmation.getText().toString())){
            Toast.makeText(TelaCadastro.this, "The passwords do not match",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            tryRegisterOnAPI();
        }
    }

    private void tryRegisterOnAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthUserOnAPI authUserOnAPI = retrofit.create(AuthUserOnAPI.class);

        AuthUserModel authUserModel =  new AuthUserModel(
                username.getText().toString(), user_email.getText().toString(),
                password.getText().toString());

        Call<AuthUserModel> call = authUserOnAPI.createNewUser(authUserModel);

        call.enqueue(new Callback<AuthUserModel>() {
            @Override
            public void onResponse(Call<AuthUserModel> call, Response<AuthUserModel> response) {
                if (response.code() == 401){
                    Toast.makeText(TelaCadastro.this, "Email or username already taken",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (response.code() != 200){
                    Toast.makeText(TelaCadastro.this, "Check your internet connection",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                saveLoggingDataOnSharePreferences(response.body().getId(), response.body().getName(),
                        response.body().getEmail());
                Toast.makeText(TelaCadastro.this, "User successfully registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AuthUserModel> call, Throwable t) {
                Toast.makeText(TelaCadastro.this, "Your Registration fail", Toast.LENGTH_SHORT).show();
            }
        });
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
        Intent intent = new Intent(TelaCadastro.this, Dashboard_activity.class);
        startActivity(intent);
    }
}