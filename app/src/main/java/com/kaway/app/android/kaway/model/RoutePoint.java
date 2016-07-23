package com.kaway.app.android.kaway.model;

import com.google.android.gms.maps.model.LatLng;

public class RoutePoint {
    float lat;
    float lng;

    public RoutePoint() {

    }

    public RoutePoint(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public RoutePoint(LatLng latLng) {
        this.lat = (float) latLng.latitude;
        this.lng = (float) latLng.longitude;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
