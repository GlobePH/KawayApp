package com.kaway.app.android.kaway.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kaway.app.android.kaway.R;
import com.kaway.app.android.kaway.data.MockData;
import com.kaway.app.android.kaway.helper.RouteProcessor;
import com.kaway.app.android.kaway.model.Route;
import com.kaway.app.android.kaway.model.RouteStop;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment mapFragment;
    GoogleMap map;
    OnMapReadyCallback mapReadyCallback;

    MockData mockData = new MockData();
    List<Route> routes;
    List<RouteStop> routeStops;
    MarkerOptions userMarker;

    LatLng initialLocation = new LatLng(14.550801f, 121.049578f); //Arbitrary for now
    float initialZoom = 18f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mapPlaceHolder, mapFragment);
        ft.commit();
        mapReadyCallback = new MapReadyCallback();
        mapFragment.getMapAsync(mapReadyCallback);

        initializeMapItems();
    }

    private void initializeMapItems() {
        routes = mockData.getRoutes();
        routeStops = mockData.getRouteStops();
    }

    private void drawLines() {
        for (Route _route : routes) {
            map.addPolyline(RouteProcessor.processRoute(_route));
        }
    }

    private void drawStops() {
        for (RouteStop routeStop : routeStops) {
            map.addMarker(RouteProcessor.processRouteStop(routeStop));
        }
    }

    class MapReadyCallback implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, initialZoom));

            drawLines();
            drawStops();

            //Init mock user
            userMarker = new MarkerOptions()
                    .position(initialLocation)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_flat));
            map.addMarker(userMarker);

            mockJeeps();
        }
    }

    private void mockJeeps() {
        //Init mock jeeps
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.549660f, 121.049173f))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.jeepney_icon)));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.549203f, 121.052839f))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.jeepney_icon)));
    }
}
