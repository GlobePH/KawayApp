package com.kaway.app.android.kaway.model;

public class RouteLine {
    RoutePoint startPoint;
    RoutePoint endPoint;

    public RouteLine() {
    }

    public RouteLine(RoutePoint startPoint, RoutePoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public RoutePoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(RoutePoint startPoint) {
        this.startPoint = startPoint;
    }

    public RoutePoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(RoutePoint endPoint) {
        this.endPoint = endPoint;
    }
}
