package com.kaway.app.android.kaway.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.kaway.app.android.kaway.model.Jeep;
import com.kaway.app.android.kaway.model.Route;
import com.kaway.app.android.kaway.model.RouteStop;
import com.kaway.app.android.kaway.model.User;
import com.kaway.app.android.kaway.service.RestService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String IS_JEEP_FLAG = "IS_JEEP_FLAG";
    SupportMapFragment mapFragment;
    GoogleMap map;
    OnMapReadyCallback mapReadyCallback;
    RecyclerView routeList;
    Button pickRouteButton;
    View kawayButton;

    RestService mockData = new MockData();
    List<Route> routes;
    List<RouteStop> routeStops;
    List<Jeep> jeeps;
    User user;

    LatLng initialLocation; //Arbitrary for now
    float initialZoom = 18f;

    boolean routeListIsShowing = false;
    boolean isRoutePicked = false;

    RouteListAdapter listAdapter;

    Route chosenRoute;

    boolean isDriver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                isDriver = false;
            } else {
                isDriver = extras.getBoolean(IS_JEEP_FLAG);
            }
        }

        init();
    }

    @Override
    public void onBackPressed() {
        if (routeListIsShowing) {
            dismissRouteList();
        } else if (isRoutePicked) {
            hideKaway();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isDriver) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                showClusters();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dismissRouteList() {
        routeListIsShowing = false;
        final Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_exit);
        routeList.setAnimation(slideDown);
        routeList.setVisibility(View.GONE);
        resetMapGestures();
    }

    private void init() {
        kawayButton = findViewById(R.id.kawayButton);
        if (kawayButton != null) {
            kawayButton.setOnClickListener(v -> {
                if (chosenRoute != null) {
                    Intent intent = new Intent(this, DoKawayActivity.class);
                    startActivity(intent);
                }
                hideKaway();
            });
        }
        routeList = (RecyclerView) findViewById(R.id.routeList);
        pickRouteButton = (Button) findViewById(R.id.pickRouteButton);
        if (pickRouteButton != null) {
            pickRouteButton.setOnClickListener(v -> {
                routeListIsShowing = true;
                final Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_enter);
                routeList.setVisibility(View.VISIBLE);
                routeList.setAnimation(slideUp);
                if (isRoutePicked) {
                    hideKaway();
                }
                if (map != null) {
                    map.getUiSettings().setAllGesturesEnabled(false);
                }
            });

            if (isDriver) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Kaway - Jeep Mode");
                }
                kawayButton.setVisibility(View.GONE);
                pickRouteButton.setVisibility(View.GONE);
            } else {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Kaway - Passenger");
                }
            }
        }

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
        routeStops = mockData.getStops();
        jeeps = mockData.getJeeps();
        user = mockData.getUser(0);

        if (!isDriver)
            initialLocation = new LatLng(user.getLocation().getLat(), user.getLocation().getLng());
        else
            initialLocation = new LatLng(14.549660f, 121.049173f);

        listAdapter = new RouteListAdapter();
        listAdapter.setRoutes(routes);
        listAdapter.notifyDataSetChanged();
        routeList.setLayoutManager(new LinearLayoutManager(this));
        routeList.setAdapter(listAdapter);
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

    private void drawJeeps() {
        for (Jeep jeep : jeeps) {
            if (jeep.getId() != 0)
                map.addMarker(RouteProcessor.processJeep(jeep));
        }
    }

    private void drawUser() {
        map.addMarker(RouteProcessor.processUser(user));
    }

    private void drawDriver() {
        map.addMarker(new MarkerOptions()
                .position(initialLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.jeepney_user)));
    }

    private void showClusters() {
        if (map != null && isDriver) {
            mockClusters();
        }
    }

    private void mockClusters() {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.551024, 121.049633))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cluster_icon)));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.551060, 121.049652))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cluster_icon)));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.551091, 121.049636))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cluster_icon)));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.550406, 121.048438))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cluster_icon)));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.550364, 121.048435))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cluster_icon)));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.550400, 121.048456))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cluster_icon)));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.549734, 121.050139))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cluster_icon)));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.549760, 121.050111))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cluster_icon)));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(14.549734, 121.050100))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.cluster_icon)));
    }

    class MapReadyCallback implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            map.getUiSettings().setMapToolbarEnabled(false);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, initialZoom));
            resetMapGestures();

            drawLines();
            drawStops();
            drawJeeps();
            if (!isDriver)
                drawUser();
            else
                drawDriver();
        }
    }

    private void resetMapGestures() {
        if (map == null)
            return;
        map.getUiSettings().setAllGesturesEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);
    }

    public class RouteListAdapter extends RecyclerView.Adapter<RouteItemViewHolder> {
        List<Route> routes = new ArrayList<>();

        public void setRoutes(List<Route> routes) {
            this.routes = routes;
        }

        @Override
        public RouteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.list_item_route, parent, false);
            RouteItemViewHolder holder = new RouteItemViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RouteItemViewHolder holder, int position) {
            holder.bind(routes.get(position));
        }

        @Override
        public int getItemCount() {
            return routes.size();
        }
    }

    public class RouteItemViewHolder extends RecyclerView.ViewHolder {
        View view;
        View routeColorView;
        TextView routeText;

        public RouteItemViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            routeColorView = view.findViewById(R.id.routeColorView);
            routeText = (TextView) view.findViewById(R.id.routeNameView);
        }

        public void bind(Route route) {
            routeColorView.setBackgroundColor(route.getRouteColor().rgb());
            routeText.setText(route.getName());
            view.setOnClickListener(v -> {
                dismissRouteList();
                isRoutePicked = true;
                Toast.makeText(MainActivity.this, "Route: " + route.getName() + " is Picked", Toast.LENGTH_SHORT).show();
                showKaway(route);
            });
        }
    }

    private void showKaway(Route route) {
        chosenRoute = route;
        final Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_enter);
        kawayButton.setVisibility(View.VISIBLE);
        kawayButton.setAnimation(slideUp);
        if (map != null) {
            map.getUiSettings().setAllGesturesEnabled(false);
        }
    }

    private void hideKaway() {
        chosenRoute = null;
        isRoutePicked = false;
        final Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_exit);
        kawayButton.setAnimation(slideDown);
        kawayButton.setVisibility(View.GONE);
        resetMapGestures();
    }
}
