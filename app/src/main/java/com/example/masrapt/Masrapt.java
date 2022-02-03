package com.example.masrapt;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Masrapt extends Application {
    public  static final  String CHANNEL_1_ID = "channel1";
    public  static final  String CHANNEL_2_ID = "channel2";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel1 = new NotificationChannel(
                    CHANNEL_1_ID, "Nearby Buses Alert",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel1.setDescription("Buses Stops Proximity Alerts");

            NotificationChannel notificationChannel2 = new NotificationChannel(
                    CHANNEL_2_ID, "Bus stop alert",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel2.setDescription("Buses on Stops Alerts");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel2);
            manager.createNotificationChannel(notificationChannel1);
        }
    }
}
