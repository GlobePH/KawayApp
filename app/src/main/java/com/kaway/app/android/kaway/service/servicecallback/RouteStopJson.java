package com.kaway.app.android.kaway.service.servicecallback;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteStopJson {
    @SerializedName("distance")
    public float distance;

    @SerializedName("latitude")
    public float latitude;

    @SerializedName("longitude")
    public float longitude;

    @SerializedName("stopCode")
    public String stopCode;

    @SerializedName("pivot")
    public List<PivotJson> pivot;
}
