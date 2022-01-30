package com.example.masrapt.ui.home;

public class Bus {

    private int id;
    private String registration_plate;
    private int current_sequence_number;
    private double longitude;
    private double latitude;
    private int state;
    private int id_route;

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
