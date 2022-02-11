package com.example.masrapt;

import static com.example.masrapt.Masrapt.CHANNEL_1_ID;
import static com.example.masrapt.Masrapt.CHANNEL_2_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.masrapt.ui.home.Bus;
import com.example.masrapt.ui.home.BusAPIRoutes;
import com.example.masrapt.ui.home.BusJSONResponse;
import com.example.masrapt.ui.home.BusStop;
import com.example.masrapt.ui.home.BusStopJSONResponse;
import com.example.masrapt.ui.home.HomeFragment;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BroadcastService extends Service {
    private String routes_name;
    private String name_bus_stop;
    private ArrayList<Bus> busList;
    private  boolean hadFindNearbyBus = false;
    private ArrayList<BusStop> all_buses_stops = new ArrayList<>();

    public class BusTracking implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (this) {
                    wait(10000);
                    getAllBusInfo();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendBusProximityNotification(String registration_plate) {
        Intent dashboard_activity = new Intent(this, HomeFragment.class);
        dashboard_activity.putExtra("route_name", routes_name);
        dashboard_activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, dashboard_activity, 0);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        Notification builder = new NotificationCompat.Builder(this,
                CHANNEL_1_ID)
                .setSmallIcon(R.drawable.bus1)
                .setContentTitle("Bus Stop Alert at "+getRoutes_name())
                .setContentIntent(pendingIntent)
                .setContentText("Your bus is close to reaching the stop " + getName_bus_stop()+
                        ". Registration Plate: "+registration_plate
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        managerCompat.notify(2, builder);
    }

    private void sendBusOnStopNotification() {
        Intent dashboard_activity = new Intent(this, Dashboard_activity.class);
        dashboard_activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, dashboard_activity, 0);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        Notification builder = new NotificationCompat.Builder(this,
                CHANNEL_2_ID)
                .setSmallIcon(R.drawable.bus1)
                .setContentTitle("Bus Stop Alert at "+getRoutes_name())
                .setContentIntent(pendingIntent)
                .setContentText("Your bus is already at the stop " + getName_bus_stop())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        managerCompat.notify(1, builder);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        getNotificationData();

        getAllBusInfo();
        creatingNewThread();
    }

    private BusStop filterBusStopBYName() {
        BusStop busStop = null;

        for(BusStop busStop1: all_buses_stops){
            if(busStop1.getBus_stop_name().equals(getName_bus_stop())){
                busStop = busStop1;
            }
        }
        return busStop;
    }

    public void controlNearbyBusTOSendNotification(){
        BusStop busStop = filterBusStopBYName();
        Boolean busOnStops = false;
        if(busStop != null){
            for(Bus bus: busList){
                if(!hadFindNearbyBus && bus.getCurrent_sequence_number() >= busStop.getSequence_number() - 3
                && bus.getRoute_name().equals(getRoutes_name())){
                    sendBusProximityNotification(bus.getRegistration_plate());
                    hadFindNearbyBus = true;
                }

                if(bus.getCurrent_sequence_number() == busStop.getSequence_number()
                        && bus.getRoute_name().equals(getRoutes_name())){
                    sendBusOnStopNotification();
                    busOnStops = true;
                    hadFindNearbyBus = true;
                }
            }
        }
        if(!hadFindNearbyBus || !busOnStops){
            creatingNewThread();
        }
    }

    /**
     * Method to get all the Buses stop from the API
     * */
    private void getAllBusStops() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BusAPIRoutes busAPIRoutes = retrofit.create(BusAPIRoutes.class);
        Call<BusStopJSONResponse> call = busAPIRoutes.getBusStop();

        call.enqueue(new Callback<BusStopJSONResponse>() {
            @Override
            public void onResponse(Call<BusStopJSONResponse> call, Response<BusStopJSONResponse> response) {
                BusStopJSONResponse jsonResponse = response.body();
                all_buses_stops = new ArrayList<BusStop>(Arrays.asList(jsonResponse.getBusStop()));
                controlNearbyBusTOSendNotification();
            }

            @Override
            public void onFailure(Call<BusStopJSONResponse> call, Throwable t) {

            }
        });
    }

    /**
     * Method to get all the buses info
     * */
    private void getAllBusInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BusAPIRoutes busAPIRoutes = retrofit.create(BusAPIRoutes.class);
        Call<BusJSONResponse> call = busAPIRoutes.getBus();

        call.enqueue(new Callback<BusJSONResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<BusJSONResponse> call, Response<BusJSONResponse> response) {
                BusJSONResponse jsonResponse = response.body();
                busList = new ArrayList<Bus>(Arrays.asList(jsonResponse.getBus()));
                getAllBusStops();
            }

            @Override
            public void onFailure(Call<BusJSONResponse> call, Throwable t) {

            }
        });
    }

    private void creatingNewThread() {
        BroadcastService.BusTracking busTracking = new BroadcastService.BusTracking();
        Thread t1 = new Thread(busTracking);
        t1.start();
    }

    private void getNotificationData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("application",
                Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("route_name", "");
        String bus_stop_name = sharedPreferences.getString("bus_route_name", "");
        setRoutes_name(name);
        setName_bus_stop(bus_stop_name);
    }


    public String getRoutes_name() {
        return routes_name;
    }

    public void setRoutes_name(String routes_name) {
        this.routes_name = routes_name;
    }

    public String getName_bus_stop() {
        return name_bus_stop;
    }

    public void setName_bus_stop(String name_bus_stop) {
        this.name_bus_stop = name_bus_stop;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
