package com.example.masrapt.ui.home;

import com.example.masrapt.ui.notifications.RouteJSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BusAPIRoutes {

    @GET("/bus")
    Call<BusJSONResponse> getBus();

    @GET("/bus/all_buses_stop")
    Call<BusStopJSONResponse> getBusStop();

}
