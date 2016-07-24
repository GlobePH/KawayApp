package com.kaway.app.android.kaway.service.servicecallback;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteJson {
    @SerializedName("id")
    public long id;

    @SerializedName("name")
    public String name;

    @SerializedName("sourceId")
    public long sourceId;

    @SerializedName("destinationId")
    public long destinationId;

    @SerializedName("waypoints")
    public List<WaypointJson> waypoints;
}
