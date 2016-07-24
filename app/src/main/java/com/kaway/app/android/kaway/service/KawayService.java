package com.kaway.app.android.kaway.service;

import com.kaway.app.android.kaway.model.Route;
import com.kaway.app.android.kaway.model.RouteStop;
import com.kaway.app.android.kaway.service.servicecallback.AuthCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KawayService {

    @POST("http://jcgurango.com/kaway/api/authenticate")
    Call<AuthCallback> login(String email,
                             String password);

    @GET("http://jcgurango.com/kaway/api/{authKey}/routes/near/{latitude}/{longitude}")
    Call<List<Route>> getRoutes(@Path("authKey") float key,
                                @Path("latitude") float lat,
                                @Path("latitude") float lng);

    @GET("http://jcgurango.com/kaway/api/{authKey}/routes/{routeid}/stops/{latitude}/{longitude}")
    Call<List<RouteStop>> getStops(@Path("authKey") float key,
                                   @Path("routeid") float routeId,
                                   @Path("latitude") float lat,
                                   @Path("latitude") float lng);
}
