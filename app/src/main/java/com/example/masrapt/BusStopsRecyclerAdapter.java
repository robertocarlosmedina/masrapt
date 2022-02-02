package com.example.masrapt;

import static com.example.masrapt.Masrapt.CHANNEL_1_ID;

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
                    requestNotification();
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        private void requestNotification() {

            Intent dashboard_activity = new Intent(itemView.getContext(), Dashboard_activity.class);
            dashboard_activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(itemView.getContext(),
                    0, dashboard_activity, 0);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(
                    itemView.getContext());

            Notification builder = new NotificationCompat.Builder(itemView.getContext(),
                    CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.bus1)
                    .setContentTitle("Bus Stop Alert")
                    .setContentIntent(pendingIntent)
                    .setContentText("Bus nearby " + route_places.getText())
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            managerCompat.notify(1, builder);

            Intent intent = new Intent(itemView.getContext(), BroadcastService.class);
            intent.putExtra("route", route_name.getText());
            intent.putExtra("bus_stop", route_places.getText());
            itemView.getContext().startService(intent);
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
