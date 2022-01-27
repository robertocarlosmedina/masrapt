package com.example.masrapt;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthUserOnAPI {

    @POST("user/auth")
    Call<AuthUserModel> PostDataIntoServer(@Body AuthUserModel authuser);

}
