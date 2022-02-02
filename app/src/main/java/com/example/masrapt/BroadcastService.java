package com.example.masrapt;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.security.Provider;
import java.util.List;
import java.util.Map;

public class BroadcastService extends Service {
    private String routes_name;
    private String name_bus_stop;

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
    public void onCreate() {
        super.onCreate();
        for(int i = 0; i < 40; i++){
            Log.d("qwqw", "Result: "+getRoutes_name());
        }
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        String route = intent.getStringExtra("route");
        setRoutes_name(intent.getExtras()+" ok");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String route = intent.getStringExtra("route");
        setRoutes_name(intent.getExtras()+" ok");
        for(int i = 0; i < 40; i++){
            Log.d("ddd", "Result_1: " + route);
        }
        return null;
    }
}
