package com.example.masrapt;

import retrofit2.Call;
import retrofit2.http.GET;

public interface masraptAPI {

    // http://localhost:3001/

    @GET("user/12")
    Call<userDataModel> getData();
}
