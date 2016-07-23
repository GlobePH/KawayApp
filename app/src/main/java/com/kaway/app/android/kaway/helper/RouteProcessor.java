package com.kaway.app.android.kaway.helper;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kaway.app.android.kaway.R;
import com.kaway.app.android.kaway.model.Jeep;
import com.kaway.app.android.kaway.model.Route;
import com.kaway.app.android.kaway.model.RouteLine;
import com.kaway.app.android.kaway.model.RouteStop;

import java.util.List;

public class RouteProcessor {

    public static PolylineOptions processRoute(Route route) {
        List<RouteLine> routeLines = route.getRouteLines();
        PolylineOptions options = new PolylineOptions().width(5).color(route.getRouteColor().rgb()).geodesic(true);
        for (RouteLine routeLine : routeLines) {
            options.add(new LatLng(routeLine.getStartPoint().getLat(), routeLine.getStartPoint().getLng()));
            options.add(new LatLng(routeLine.getEndPoint().getLat(), routeLine.getEndPoint().getLng()));
        }

        return options;
    }

    public static MarkerOptions processRouteStop(RouteStop routeStop) {
        return new MarkerOptions()
                .position(new LatLng(routeStop.getRoutePoint().getLat(), routeStop.getRoutePoint().getLng()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.jeep_stop_flag))
                .title(routeStop.getName());
    }

    public static MarkerOptions processJeep(Jeep jeep) {
        return new MarkerOptions()
                .position(new LatLng(jeep.getLocation().getLat(), jeep.getLocation().getLng()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.jeepney_icon));
    }
}
