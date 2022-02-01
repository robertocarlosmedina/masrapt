package com.example.masrapt.ui.home;

public class BusDescription {

    private int id;
    private String registration_plate;
    private int current_sequence_number;
    private double longitude;
    private double latitude;
    private int state;
    private int id_route;
    private String route_color;
    private int in_a_bus_stop;
    private String passengers_number;
    private String route_name;
    private String total_seats;


    public BusDescription(String registration_plate, String passengers_number, String route_name,
                          String total_seats, String route_color) {
        this.registration_plate = registration_plate;
        this.passengers_number = passengers_number;
        this.route_name = route_name;
        this.total_seats = total_seats;
        this.route_color = route_color;
    }

    public String getRoute_name() {
        return route_name;
    }

    public String getPassengers_number() {
        return passengers_number;
    }

    public int getIn_a_bus_stop() {
        return in_a_bus_stop;
    }

    public String getTotal_seats() {
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
