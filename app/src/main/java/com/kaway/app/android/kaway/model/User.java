package com.kaway.app.android.kaway.model;

public class User {
    long id;
    String mobileNumber;
    RoutePoint location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public RoutePoint getLocation() {
        return location;
    }

    public void setLocation(RoutePoint location) {
        this.location = location;
    }
}
