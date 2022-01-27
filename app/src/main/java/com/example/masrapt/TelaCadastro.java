package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TelaCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.print("ok");
        setContentView(R.layout.activity_tela_cadastro);
    }
    /*private void getAPIData() {
        Toast.makeText(TelaCadastro.this, "Getting data", Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.valueOf(R.string.api_base_url))
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
                        "\n password: " + response.body().getHash_password();
                test.setText(res);
            }

            @Override
            public void onFailure(Call<userDataModel> call, Throwable t) {
                test.setText("Error from the server");
            }
        });
    }*/
}