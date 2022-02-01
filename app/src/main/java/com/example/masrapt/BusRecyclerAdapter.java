package com.example.masrapt;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masrapt.ui.home.BusDescription;

import java.util.ArrayList;

public class BusRecyclerAdapter  extends RecyclerView.Adapter<BusRecyclerAdapter.MyViewHolder> {
    private ArrayList<BusDescription> busList;

    public BusRecyclerAdapter(ArrayList<BusDescription> busList) {
        this.busList = busList;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView bus_plate, route_line, total_bus_seat, total_passengers;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bus_plate = itemView.findViewById(R.id.bus_plate);
            route_line = itemView.findViewById(R.id.route_line);
            total_bus_seat = itemView.findViewById(R.id.total_bus_seat);
            total_passengers = itemView.findViewById(R.id.total_passengers);
        }
    }

    @NonNull
    @Override
    public BusRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_information_items, parent,
                false);
        return new BusRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  BusRecyclerAdapter.MyViewHolder holder, int position) {
        String bus_plate = busList.get(position).getRegistration_plate();
        String bus_route_line = busList.get(position).getRoute_name();
        String total_seats = busList.get(position).getTotal_seats();
        String passengers_on_board = busList.get(position).getPassengers_number();
        String route_color = busList.get(position).getRoute_color();

        if (route_color.equals("blue")){
            holder.route_line.setTextColor(Color.rgb(0, 26, 255));
        }
        else if (route_color.equals("red")){
            holder.route_line.setTextColor(Color.RED);
        }
        else if (route_color.equals("green")){
            holder.route_line.setTextColor(Color.GREEN);
        }

        holder.bus_plate.setText(bus_plate);
        holder.route_line.setText(bus_route_line);
        holder.total_bus_seat.setText(total_seats);
        holder.total_passengers.setText(passengers_on_board);
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }
}
