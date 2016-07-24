package com.kaway.app.android.kaway.service.servicecallback;

import com.google.gson.annotations.SerializedName;

public class WaypointJson {

    @SerializedName("id")
    public long id;

    @SerializedName("route_id")
    public long route_id;

    @SerializedName("index")
    public long index;

    @SerializedName("latitude")
    public float latitude;

    @SerializedName("longitude")
    public float longitude;
}
