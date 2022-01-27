package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
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
    private TextView test;
    private AppCompatButton btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        test = (TextView) findViewById(R.id.test);
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
            Toast.makeText(TelaLogin.this, "Authenticating user", Toast.LENGTH_SHORT).show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.api_base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AuthUserOnAPI authUserOnAPI = retrofit.create(AuthUserOnAPI.class);

            AuthUserModel authUserModel =  new AuthUserModel(user_name_or_email, user_password);

            Call<AuthUserModel> call = authUserOnAPI.PostDataIntoServer(authUserModel);

            call.enqueue(new Callback<AuthUserModel>() {
                @Override
                public void onResponse(Call<AuthUserModel> call, Response<AuthUserModel> response) {
                    if (response.code() == 401){
                        Toast.makeText(TelaLogin.this, "User authentication fail", Toast.LENGTH_SHORT).show();
                    }
                    if (response.code() != 200){
                        test.setText("Check your internet connection");
                        return;
                    }
                    String res = "name_or_email: " + user_name_or_email +
                            "\n user_password: " + user_password;
                    test.setText(res);
                }

                @Override
                public void onFailure(Call<AuthUserModel> call, Throwable t) {
                    test.setText(t.getMessage());
                }
            });
            return;
        }
        Toast.makeText(TelaLogin.this, "The fields should not be empty", Toast.LENGTH_SHORT).show();
    }

    private void getAPIData() {
        Toast.makeText(TelaLogin.this, "Getting data", Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        masraptAPI masrapt_api = retrofit.create(masraptAPI.class);
        Call<userDataModel> call = masrapt_api.getData();

        test.setText("okok");

        call.enqueue(new Callback<userDataModel>() {
            @Override
            public void onResponse(Call<userDataModel> call, Response<userDataModel> response) {
                if (response.code() != 200){
                    test.setText("Check your internet connection");
                    return;
                }

                String res = "ID: " + response.body().getId() +
                        "\n user: " + response.body().getName()+
                        "\n email: " + response.body().getEmail()+
                        "\n password: " + response.body().getPassword();
                test.setText(res);
            }

            @Override
            public void onFailure(Call<userDataModel> call, Throwable t) {
                test.setText(t.getMessage());
            }
        });
    }
}