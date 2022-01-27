package com.example.masrapt;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masrapt.ui.notifications.Route;

import java.util.ArrayList;

public class RoutesRecyclerAdapter extends RecyclerView.Adapter<RoutesRecyclerAdapter.MyViewHolder> {
    private ArrayList<RouteDescription> routesList;


    public RoutesRecyclerAdapter(ArrayList<RouteDescription> routesList){
        this.routesList = routesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView route_name, route_places, active_bus, timer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            route_name = itemView.findViewById(R.id.route_name);
            route_places = itemView.findViewById(R.id.route_places);
            active_bus = itemView.findViewById(R.id.active_bus);
            timer = itemView.findViewById(R.id.timer);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_des_item, parent,
            false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = routesList.get(position).getName();
        String locations = routesList.get(position).getLocations();
        String route_timer = String.valueOf(routesList.get(position).getRoute_timer());
        String nr_bus = String.valueOf(routesList.get(position).getActive_bus());

        holder.route_name.setText(name);
        holder.active_bus.setText(nr_bus);
        holder.route_places.setText(locations);
        holder.timer.setText(route_timer);
    }

    @Override
    public int getItemCount() {
        return routesList.size();
    }
}
