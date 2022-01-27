package com.example.masrapt.ui.notifications;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RoutesAPIRoutes {

    @GET("/routes")
    Call<RouteJSONResponse> getRoutes();
}
