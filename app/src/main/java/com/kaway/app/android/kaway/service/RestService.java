package com.kaway.app.android.kaway.service;

import com.kaway.app.android.kaway.model.Jeep;
import com.kaway.app.android.kaway.model.Route;
import com.kaway.app.android.kaway.model.RouteStop;
import com.kaway.app.android.kaway.model.User;

import java.util.List;

public interface RestService {
    List<Route> getRoutes();

    Route getRoute(long id);

    List<RouteStop> getStops();

    RouteStop getStop(long id);

    List<Jeep> getJeeps();

    Jeep getJeep(long id);

    User getUser(long id);
}
