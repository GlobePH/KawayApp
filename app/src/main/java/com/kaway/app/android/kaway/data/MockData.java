package com.kaway.app.android.kaway.data;


import com.google.android.gms.maps.model.LatLng;
import com.kaway.app.android.kaway.model.Jeep;
import com.kaway.app.android.kaway.model.Route;
import com.kaway.app.android.kaway.model.RouteColor;
import com.kaway.app.android.kaway.model.RouteLine;
import com.kaway.app.android.kaway.model.RoutePoint;
import com.kaway.app.android.kaway.model.RouteStop;
import com.kaway.app.android.kaway.model.User;
import com.kaway.app.android.kaway.service.RestService;

import java.util.ArrayList;
import java.util.List;

public class MockData implements RestService {

    List<Route> routes = new ArrayList<>();
    List<RouteStop> routeStops = new ArrayList<>();
    List<Jeep> jeeps = new ArrayList<>();
    User user;

    public MockData() {
        routes.add(createFirstMockRoute());
        routes.add(createSecondMockRoute());
        routeStops = createRouteStops();

        jeeps.add(new Jeep(0, "Blue Jeep", routes.get(0), new RoutePoint(new LatLng(14.549660f, 121.049173f))));
        jeeps.add(new Jeep(1, "Red Jeep", routes.get(1), new RoutePoint(new LatLng(14.549203f, 121.052839f))));

        user = new User(0, "1234567890", new RoutePoint(new LatLng(14.550801f, 121.049578f)));
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public Route getRoute(long id) {
        for (Route route : routes) {
            if (route.getId() == id)
                return route;
        }

        return null;
    }

    @Override
    public List<RouteStop> getStops() {
        return routeStops;
    }

    @Override
    public RouteStop getStop(long id) {
        for (RouteStop routeStop : routeStops) {
            if (routeStop.getId() == id)
                return routeStop;
        }
        return null;
    }

    public List<RouteStop> getRouteStops() {
        return routeStops;
    }

    public List<Jeep> getJeeps() {
        return jeeps;
    }

    @Override
    public Jeep getJeep(long id) {
        for (Jeep jeep : jeeps) {
            if (jeep.getId() == id)
                return jeep;
        }
        return null;
    }

    @Override
    public User getUser(long id) {

        return user;
    }

    public List<RouteStop> createRouteStops() {
        List<RouteStop> routeStops = new ArrayList<>();
        routeStops.add(new RouteStop(1, "First Stop", new RoutePoint(14.550998f, 121.049643f)));
        routeStops.add(new RouteStop(2, "Second Stop", new RoutePoint(14.552737f, 121.053073f)));
        routeStops.add(new RouteStop(3, "Third Stop", new RoutePoint(14.549448f, 121.054716f)));
        routeStops.add(new RouteStop(4, "Fourth Stop", new RoutePoint(14.548425f, 121.049314f)));
        routeStops.add(new RouteStop(5, "Red First Stop", new RoutePoint(14.551614f, 121.047876f)));
        routeStops.add(new RouteStop(6, "Red Second Stop", new RoutePoint(14.551844f, 121.050885f)));
        routeStops.add(new RouteStop(7, "Red Third Stop", new RoutePoint(14.551239f, 121.052709f)));
        routeStops.add(new RouteStop(8, "Red Fourth Stop", new RoutePoint(14.549888f, 121.053044f)));
        routeStops.add(new RouteStop(9, "Red Fifth Stop", new RoutePoint(14.549303f, 121.051520f)));
        routeStops.add(new RouteStop(10, "Red Sixth Stop", new RoutePoint(14.549721f, 121.050197f)));
        routeStops.add(new RouteStop(11, "Red Seventh Stop", new RoutePoint(14.550416f, 121.048419f)));

        return routeStops;
    }

    private Route createFirstMockRoute() {
        Route route = new Route();
        route.setName("The Blue Route");
        route.setId(1);
        route.setRouteColor(new RouteColor(0, 0, 255)); //blue

        List<LatLng> list = new ArrayList<>();
        list.add(new LatLng(14.548506f, 121.048764f));
        list.add(new LatLng(14.553678f, 121.050452f));
        list.add(new LatLng(14.552040f, 121.055647f));
        list.add(new LatLng(14.551645f, 121.055656f));
        list.add(new LatLng(14.549715f, 121.055000f));
        list.add(new LatLng(14.549445f, 121.054751f));
        list.add(new LatLng(14.548957f, 121.054772f));
        list.add(new LatLng(14.547514f, 121.054260f));
        list.add(new LatLng(14.547400f, 121.052315f));
        list.add(new LatLng(14.548506f, 121.048764f));

        List<RouteLine> routeLines = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            RouteLine routeLine = new RouteLine();
            routeLine.setStartPoint(new RoutePoint(list.get(i)));
            routeLine.setEndPoint(new RoutePoint(list.get(i + 1)));
            routeLines.add(routeLine);
        }

        route.setRouteLines(routeLines);

        List<RouteStop> routeStops = new ArrayList<>();
        routeStops.add(new RouteStop(1, "First Stop", new RoutePoint(14.550998f, 121.049643f)));
        routeStops.add(new RouteStop(2, "Second Stop", new RoutePoint(14.552737f, 121.053073f)));
        routeStops.add(new RouteStop(3, "Third Stop", new RoutePoint(14.549448f, 121.054716f)));
        routeStops.add(new RouteStop(4, "Fourth Stop", new RoutePoint(14.548425f, 121.049314f)));
        route.setRouteStops(routeStops);

        return route;
    }


    private Route createSecondMockRoute() {
        Route route = new Route();
        route.setName("The Red Route");
        route.setId(2);
        route.setRouteColor(new RouteColor(255, 127, 127)); //red

        List<LatLng> list = new ArrayList<>();
        list.add(new LatLng(14.550803f, 121.047585f));
        list.add(new LatLng(14.552476f, 121.048133f));
        list.add(new LatLng(14.552305f, 121.048807f));
        list.add(new LatLng(14.552320f, 121.049391f));
        list.add(new LatLng(14.551028f, 121.053440f));
        list.add(new LatLng(14.548914f, 121.052740f));
        list.add(new LatLng(14.550190f, 121.048730f));
        list.add(new LatLng(14.550618f, 121.048061f));
        list.add(new LatLng(14.550803f, 121.047585f));

        List<RouteLine> routeLines = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            RouteLine routeLine = new RouteLine();
            routeLine.setStartPoint(new RoutePoint(list.get(i)));
            routeLine.setEndPoint(new RoutePoint(list.get(i + 1)));
            routeLines.add(routeLine);
        }

        route.setRouteLines(routeLines);

        List<RouteStop> routeStops = new ArrayList<>();
        routeStops.add(new RouteStop(5, "Red First Stop", new RoutePoint(14.551614f, 121.047876f)));
        routeStops.add(new RouteStop(6, "Red Second Stop", new RoutePoint(14.551844f, 121.050885f)));
        routeStops.add(new RouteStop(7, "Red Third Stop", new RoutePoint(14.551239f, 121.052709f)));
        routeStops.add(new RouteStop(8, "Red Fourth Stop", new RoutePoint(14.549888f, 121.053044f)));
        routeStops.add(new RouteStop(9, "Red Fifth Stop", new RoutePoint(14.549303f, 121.051520f)));
        routeStops.add(new RouteStop(10, "Red Sixth Stop", new RoutePoint(14.549721f, 121.050197f)));
        routeStops.add(new RouteStop(11, "Red Seventh Stop", new RoutePoint(14.550416f, 121.048419f)));
        route.setRouteStops(routeStops);

        return route;
    }
}
