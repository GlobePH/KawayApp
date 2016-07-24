package com.kaway.app.android.kaway.service;

import com.kaway.app.android.kaway.service.servicecallback.AuthCallback;
import com.kaway.app.android.kaway.service.servicecallback.RouteListCallback;
import com.kaway.app.android.kaway.service.servicecallback.RouteStopsListCallback;
import com.kaway.app.android.kaway.service.servicecallback.UserAuthJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KawayService {

    @POST("authenticate")
    Call<AuthCallback> login(@Body UserAuthJson user);

    @GET("{authKey}/routes/near/{latitude}/{longitude}")
    Call<RouteListCallback> getRoutes(@Path("authKey") String key,
                                      @Path("latitude") float lat,
                                      @Path("longitude") float lng);

    @GET("{authKey}/routes/{routeid}/stops/{latitude}/{longitude}")
    Call<RouteStopsListCallback> getStops(@Path("authKey") String key,
                                          @Path("routeid") long routeId,
                                          @Path("latitude") float lat,
                                          @Path("longitude") float lng);
}
