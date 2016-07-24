package com.kaway.app.android.kaway.service.servicecallback;

import com.kaway.app.android.kaway.model.Route;
import com.kaway.app.android.kaway.model.RouteColor;
import com.kaway.app.android.kaway.model.RouteLine;
import com.kaway.app.android.kaway.model.RoutePoint;
import com.kaway.app.android.kaway.model.RouteStop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResultsProcessor {

    public static List<Route> processRoutesResult(RouteListCallback cb) {
        List<Route> routeList = new ArrayList<>();

        for (RouteJson routeJson : cb.routes) {
            routeList.add(processRouteResult(routeJson));
        }

        return routeList;
    }

    public static Route processRouteResult(RouteJson routeJson) {
        Route route = new Route();
        route.setId(routeJson.id);
        route.setName(routeJson.name);
        Random rand = new Random(routeJson.id);
        int r = rand.nextInt(255) + 1;
        int g = rand.nextInt(255) + 1;
        int b = rand.nextInt(255) + 1;
        RouteColor color = new RouteColor(r, g, b);
        route.setRouteColor(color);
        route.setRouteLines(new ArrayList<>());
        for (int i = 0; i < routeJson.waypoints.size()-1; i++) {
            WaypointJson wjStart = routeJson.waypoints.get(i);
            RoutePoint startPoint = new RoutePoint(wjStart.latitude, wjStart.longitude);

            WaypointJson wjEnd = routeJson.waypoints.get(i + 1);
            RoutePoint endPoint = new RoutePoint(wjEnd.latitude, wjEnd.longitude);

            RouteLine routeLine = new RouteLine(startPoint, endPoint);
            route.getRouteLines().add(routeLine);
        }
        route.setRouteStops(new ArrayList<>());

        return route;
    }

    public static List<RouteStop> processRouteStopsResult() {
        List<RouteStop> routeStops = new ArrayList<>();

        return routeStops;
    }

    public static RouteStop processRouteStopResult(RouteStopJson routeStopJson) {
        RouteStop routeStop = new RouteStop();
        RoutePoint stopPoint = new RoutePoint(routeStopJson.latitude, routeStopJson.longitude);
        routeStop.setRoutePoint(stopPoint);
        return null;
    }
}
