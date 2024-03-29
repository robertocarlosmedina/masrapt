package com.example.masrapt.ui.home;

public class Bus {

    private int id;
    private String registration_plate;
    private int current_sequence_number;
    private double longitude;
    private double latitude;
    private int state;
    private int id_route;
    private String route_color;
    private int in_a_bus_stop;
    private int passengers_number;
    private String route_name;
    private int total_seats;

    public String getRoute_name() {
        return route_name;
    }

    public int getPassengers_number() {
        return passengers_number;
    }

    public int getIn_a_bus_stop() {
        return in_a_bus_stop;
    }

    public int getTotal_seats() {
        return total_seats;
    }

    public String getRoute_color() {
        return route_color;
    }

    public int getId() {
        return id;
    }

    public String getRegistration_plate() {
        return registration_plate;
    }

    public int getCurrent_sequence_number() {
        return current_sequence_number;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getState() {
        return state;
    }

    public int getId_route() {
        return id_route;
    }
}
