package com.kaway.app.android.kaway.service;

import com.kaway.app.android.kaway.service.servicecallback.AuthCallback;
import com.kaway.app.android.kaway.service.servicecallback.RouteListCallback;
import com.kaway.app.android.kaway.service.servicecallback.RouteStopsListCallback;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KawayService {

    @POST("http://jcgurango.com/kaway/api/authenticate")
    Call<AuthCallback> login(String email,
                             String password);

    @GET("http://jcgurango.com/kaway/api/{authKey}/routes/near/{latitude}/{longitude}")
    Call<RouteListCallback> getRoutes(@Path("authKey") float key,
                                      @Path("latitude") float lat,
                                      @Path("latitude") float lng);

    @GET("http://jcgurango.com/kaway/api/{authKey}/routes/{routeid}/stops/{latitude}/{longitude}")
    Call<RouteStopsListCallback> getStops(@Path("authKey") float key,
                                          @Path("routeid") float routeId,
                                          @Path("latitude") float lat,
                                          @Path("latitude") float lng);
}
