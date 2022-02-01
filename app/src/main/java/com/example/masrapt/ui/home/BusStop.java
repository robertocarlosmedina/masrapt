package com.example.masrapt.ui.home;

public class BusStop {
    private double longitude;
    private double latitude;
    private String route_name;
    private String route_color;
    private String bus_stop_name;
    private int sequence_number;
    private String locations;

    public String getLocations() {
        return locations;
    }

    public String getBus_stop_name() {
        return bus_stop_name;
    }

    public int getSequence_number() {
        return sequence_number;
    }

    public String getRoute_color() {
        return route_color;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getRoute_name() {
        return route_name;
    }
}
