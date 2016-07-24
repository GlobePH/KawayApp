package com.kaway.app.android.kaway.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.android.gms.maps.model.LatLng;
import com.kaway.app.android.kaway.R;
import com.kaway.app.android.kaway.data.MockData;
import com.kaway.app.android.kaway.helper.RouteProcessor;
import com.kaway.app.android.kaway.model.Jeep;
import com.kaway.app.android.kaway.model.Route;
import com.kaway.app.android.kaway.model.RouteStop;
import com.kaway.app.android.kaway.model.User;
import com.kaway.app.android.kaway.service.KawayService;
import com.kaway.app.android.kaway.service.RestService;
import com.kaway.app.android.kaway.service.servicecallback.AuthCallback;
import com.kaway.app.android.kaway.service.servicecallback.ResultsProcessor;
import com.kaway.app.android.kaway.service.servicecallback.RouteListCallback;
import com.kaway.app.android.kaway.service.servicecallback.RouteStopsListCallback;
import com.kaway.app.android.kaway.service.servicecallback.UserAuthJson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

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

    //Mock User-Pass
    String userEmail = "katsu.kare.raisu@gmail.com";
    String userPassword = "12345abc";
    String apiKey = "";

    Retrofit retrofit;
    KawayService kawayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initializeMapItems();
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

    private void dismissRouteList() {
        routeListIsShowing = false;
        final Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_exit);
        routeList.setAnimation(slideDown);
        routeList.setVisibility(View.GONE);
        resetMapGestures();
    }

    private void init() {
        kawayButton = findViewById(R.id.kawayButton);
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
        }

        mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mapPlaceHolder, mapFragment);
        ft.commit();
        mapReadyCallback = new MapReadyCallback();
        mapFragment.getMapAsync(mapReadyCallback);
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
            map.addMarker(RouteProcessor.processJeep(jeep));
        }
    }

    private void drawUser() {
        map.addMarker(RouteProcessor.processUser(user));
    }

    class MapReadyCallback implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            map.getUiSettings().setMapToolbarEnabled(false);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, initialZoom));
            resetMapGestures();

            initConnection();
        }
    }

    private void initConnection() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://jcgurango.com/kaway/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        kawayService = retrofit.create(KawayService.class);

        UserAuthJson userAuthJson = new UserAuthJson(userEmail, userPassword);
        Call<AuthCallback> authCall = kawayService.login(userAuthJson);
        authCall.enqueue(new Callback<AuthCallback>() {
            @Override
            public void onResponse(Call<AuthCallback> call, Response<AuthCallback> response) {
                if (response.code() == 200) {
                    AuthCallback authCallback = response.body();
                    apiKey = authCallback.key;
                    downloadMapItems();
                }
            }

            @Override
            public void onFailure(Call<AuthCallback> call, Throwable t) {
                Toast.makeText(MainActivity.this, "DEBUG: Login Failed, Reload the App", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadMapItems() {
        downloadRoutes();
        downloadStops();

//        drawLines();
//        drawStops();
        drawJeeps();
        drawUser();
    }

    private void downloadRoutes() {
        Call<RouteListCallback> routeListCallbackCall = kawayService.getRoutes(apiKey, user.getLocation().getLat(), user.getLocation().getLng());

        routeListCallbackCall.enqueue(new Callback<RouteListCallback>() {
            @Override
            public void onResponse(Call<RouteListCallback> call, Response<RouteListCallback> response) {
                Log.e("MainActivity", "Routes: " + response.code());
                if (response.code() == 200) {
                    Toast.makeText(MainActivity.this, "Routes Success", Toast.LENGTH_SHORT).show();
                    routes = ResultsProcessor.processRoutesResult(response.body());
                    drawLines();
                }
            }

            @Override
            public void onFailure(Call<RouteListCallback> call, Throwable t) {

            }
        });
    }

    private void downloadStops() {

    }

    private void initializeMapItems() {
//        routes = mockData.getRoutes();
//        routeStops = mockData.getStops();
        jeeps = mockData.getJeeps();
        user = mockData.getUser(0);
        initialLocation = new LatLng(user.getLocation().getLat(), user.getLocation().getLng());

        listAdapter = new RouteListAdapter();
        listAdapter.setRoutes(routes);
        listAdapter.notifyDataSetChanged();
//        routeList.setLayoutManager(new LinearLayoutManager(this));
//        routeList.setAdapter(listAdapter);
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
                showKaway();
            });
        }
    }

    private void showKaway() {
        final Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_enter);
        kawayButton.setVisibility(View.VISIBLE);
        kawayButton.setAnimation(slideUp);
        if (map != null) {
            map.getUiSettings().setAllGesturesEnabled(false);
        }
    }

    private void hideKaway() {
        isRoutePicked = false;
        final Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_exit);
        kawayButton.setAnimation(slideDown);
        kawayButton.setVisibility(View.GONE);
        resetMapGestures();
    }
}
