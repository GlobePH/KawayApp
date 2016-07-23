package com.kaway.app.android.kaway.model;

public class Jeep {
    long id;
    String name; //optional
    Route route;
    RoutePoint location;

    public Jeep(long id, String name, Route route, RoutePoint location) {
        this.id = id;
        this.name = name;
        this.route = route;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public RoutePoint getLocation() {
        return location;
    }

    public void setLocation(RoutePoint location) {
        this.location = location;
    }
}
