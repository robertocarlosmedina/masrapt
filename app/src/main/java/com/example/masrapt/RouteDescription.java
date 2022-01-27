package com.example.masrapt;

public class RouteDescription {
    private String name;
    private String locations;
    private String active_bus;
    private String route_timer;

    public RouteDescription(String name, String locations, String active_bus, String route_timer) {
        this.name = name;
        this.locations = locations;
        this.active_bus = active_bus;
        this.route_timer = route_timer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getActive_bus() {
        return active_bus;
    }

    public void setActive_bus(String active_bus) {
        this.active_bus = active_bus;
    }

    public String getRoute_timer() {
        return route_timer;
    }

    public void setRoute_timer(String route_timer) {
        this.route_timer = route_timer;
    }
}
