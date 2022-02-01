package com.example.masrapt;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masrapt.ui.home.BusDescription;
import com.example.masrapt.ui.home.BusStop;

import java.util.ArrayList;

public class BusStopsRecyclerAdapter extends RecyclerView.Adapter<BusStopsRecyclerAdapter.MyViewHolder> {
    private ArrayList<BusStop> busStopsList;

    public BusStopsRecyclerAdapter(ArrayList<BusStop> busList) {
        this.busStopsList = busList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView route_name, route_places;
        private Button get_notified_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            route_name = itemView.findViewById(R.id.route_name);
            route_places = itemView.findViewById(R.id.route_places);
            get_notified_button = itemView.findViewById(R.id.get_notified_button);

            get_notified_button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Button: " + route_places.getText(),
                            Toast.LENGTH_LONG).show();
                    createNotificationChanel();
                    requestNotification("" + route_places.getText());
                }
            });
        }

        private void createNotificationChanel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                CharSequence name = "Simple Notification";

                NotificationChannel notificationChannel = new NotificationChannel(
                        "okgvdf df ", name, importance);
                notificationChannel.setDescription("Text Notification");

                NotificationManager notificationManager = (NotificationManager)
                        itemView.getContext().getSystemService(itemView.getContext().NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        private void requestNotification(String bus_stop) {
            Intent dashboard_activity = new Intent(itemView.getContext(), Dashboard_activity.class);
            dashboard_activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(itemView.getContext(),
                    0, dashboard_activity, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(itemView.getContext(),
                    "okokok");
            builder.setContentTitle("Bus Stop Alert");
            builder.setContentText("Your bus is nearby " + bus_stop);
            builder.setSmallIcon(IconCompat.createWithBitmap(BitmapFactory.decodeResource(itemView.getResources(), R.drawable.bus1)));
            // Set the intent that will fire when the user taps the notification
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(
                    itemView.getContext());
            managerCompat.notify(1, builder.build());
        }
    }

    @NonNull
    @Override
    public BusStopsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_stops_desc_item, parent,
                false);
        return new BusStopsRecyclerAdapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull BusStopsRecyclerAdapter.MyViewHolder holder, int position) {
        String route_name = busStopsList.get(position).getRoute_name();
        String route_color = busStopsList.get(position).getRoute_color();
        String bus_stop_name = busStopsList.get(position).getBus_stop_name();

        if (route_color.equals("blue")) {
            holder.route_name.setTextColor(Color.rgb(0, 26, 255));
        } else if (route_color.equals("red")) {
            holder.route_name.setTextColor(Color.RED);
        } else if (route_color.equals("green")) {
            holder.route_name.setTextColor(Color.GREEN);
        }

        holder.route_name.setText(route_name);
        holder.route_places.setText(bus_stop_name);
    }

    @Override
    public int getItemCount() {
        return busStopsList.size();
    }
}
