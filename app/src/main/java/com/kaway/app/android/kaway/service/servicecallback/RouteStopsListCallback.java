package com.kaway.app.android.kaway.service.servicecallback;

import com.google.gson.annotations.SerializedName;
import com.kaway.app.android.kaway.model.RouteStop;

import java.util.List;

public class RouteStopsListCallback {
    @SerializedName("success")
    public boolean success;

    @SerializedName("stops")
    public List<RouteStop> stops;
}
