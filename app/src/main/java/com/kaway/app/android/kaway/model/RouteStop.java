package com.kaway.app.android.kaway.model;

public class RouteStop {
    long id;
    RoutePoint routePoint;
    String name;

    public RouteStop() {

    }

    public RouteStop(String name) {
        this(name, null);
    }

    public RouteStop(String name, RoutePoint routePoint) {
        this(0, name, routePoint);
    }

    public RouteStop(long id, String name, RoutePoint routePoint) {
        this.id = id;
        this.name = name;
        this.routePoint = routePoint;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoutePoint getRoutePoint() {
        return routePoint;
    }

    public void setRoutePoint(RoutePoint routePoint) {
        this.routePoint = routePoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
