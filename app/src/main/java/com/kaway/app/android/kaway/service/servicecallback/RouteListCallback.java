package com.kaway.app.android.kaway.service.servicecallback;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteListCallback {
    @SerializedName("success")
    public boolean success;

    @SerializedName("routes")
    public List<RouteJson> routes;
}
