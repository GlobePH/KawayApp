package com.kaway.app.android.kaway.model;

import java.util.List;

public class Route {
    long id;
    String name;
    RouteColor routeColor;
    List<RouteLine> routeLines;
    List<RouteStop> routeStops;

    public Route() {
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

    public List<RouteLine> getRouteLines() {
        return routeLines;
    }

    public void setRouteLines(List<RouteLine> routeLines) {
        this.routeLines = routeLines;
    }

    public RouteColor getRouteColor() {
        return routeColor;
    }

    public void setRouteColor(RouteColor routeColor) {
        this.routeColor = routeColor;
    }

    public List<RouteStop> getRouteStops() {
        return routeStops;
    }

    public void setRouteStops(List<RouteStop> routeStops) {
        this.routeStops = routeStops;
    }
}
