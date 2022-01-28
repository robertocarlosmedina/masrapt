package com.example.masrapt.ui.home;

import com.example.masrapt.ui.notifications.RouteJSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoordinatesAPIRoutes {

    @GET("routes/route_coordinates/{id_route}")
    Call<RouteCoordinatesJSONResponse> getRoutesCoordinates(
            @Path("id_route") int id_route
    );
}
