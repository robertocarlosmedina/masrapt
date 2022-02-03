package com.example.masrapt;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AuthUserOnAPI {

    @POST("user/auth")
    Call<AuthUserModel> authenticateUser(@Body AuthUserModel authUser);

    @POST("user/create")
    Call<AuthUserModel> createNewUser(@Body AuthUserModel authUser);

    @PUT("user/edit")
    Call<AuthUserModel> editUserPassword(@Body AuthUserModel authUser);

    @POST("user/delete")
    Call<AuthUserModel> deleteUserData(@Body AuthUserModel authUser);

}
