package com.example.masrapt.ui.home;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.masrapt.Coordinates;
import com.example.masrapt.Dashboard_activity;
import com.example.masrapt.R;
import com.example.masrapt.RouteDescription;
import com.example.masrapt.databinding.FragmentHomeBinding;
import com.example.masrapt.ui.notifications.Route;
import com.example.masrapt.ui.notifications.RouteJSONResponse;
import com.example.masrapt.ui.notifications.RoutesAPIRoutes;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private View homeViewModel;
    private GoogleMap gMap;
    private TextView coordinates, map_type, floating_selector;
    private ImageView my_location_icon;
    private Dialog route_selector_dialog;
    private FragmentHomeBinding binding;
    private Location current_location;
    private SupportMapFragment mapFragment;
    private ArrayList<CardView> routes_choice_buttons = new ArrayList<>();
    private FusedLocationProviderClient client_location;
    private int REQUEST_CODE = 111;

    private ArrayList<Bus> busList;
    private ArrayList<RouteDescription> routesList_recycl;
    private ArrayList<BusStop> all_buses_stops = new ArrayList<>();
    private LatLng my_location;
    private Boolean showLocation = false;

    /**
     * Class Runnable that will execute some Bus
     * background tasks. Each 6 secs this background task
     * will call the getAllBusInfo method, what will update the
     * buses positions on the map.
     * */
    public class BusTracking implements Runnable {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            try {
                synchronized (this) {
                    wait(5000);
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void run() {
                                getAllBusInfo();
                            }
                        });
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = inflater.inflate(R.layout.fragment_home, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(
                R.id.google_maps);
        mapFragment.getMapAsync(this);

        routesList_recycl = new ArrayList<>();
        busList = new ArrayList<>();

        // getCurrentLocation();

        return homeViewModel;
    }

    /**
     * Method that handle the map type changing
     * */
    private void changeMapType() {
        String current_type = map_type.getText().toString();
        if (current_type.equals("Satellite")) {
            gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            map_type.setText("Normal");
        } else {
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map_type.setText("Satellite");
        }
    }

    @Override
    public void onStart() {
        coordinates = (TextView) getActivity().findViewById(R.id.test_display);
        super.onStart();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

        // Dialog to select route to display
        route_selector_dialog = new Dialog(getActivity());
        route_selector_dialog.setContentView(R.layout.select_route_dialog);
        route_selector_dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.dialog_bg));
        route_selector_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        route_selector_dialog.setCancelable(true);

        floating_selector = (TextView) getActivity().findViewById(R.id.floating_selector);
        floating_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route_selector_dialog.show();
            }
        });

        my_location_icon = (ImageView) getActivity().findViewById(R.id.my_location);
        my_location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShowHideLocation();
            }
        });

        handleRouteSwitchDisplay();

        ImageView close_icon = (ImageView) route_selector_dialog.findViewById(R.id.close_icon);
        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_dialog();
            }
        });
        map_type = (TextView) getActivity().findViewById(R.id.map_type);
        map_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMapType();
            }
        });
        getAllBusInfo();
        getAllBusStops();
    }

    /**
     * Method to handle the route selector cardView onCLick event
     * */
    private void handleRouteSwitchDisplay() {
        routes_choice_buttons.add(route_selector_dialog.findViewById(R.id.all_routes));
        routes_choice_buttons.add(route_selector_dialog.findViewById(R.id.route_1));
        routes_choice_buttons.add(route_selector_dialog.findViewById(R.id.route_2));
        routes_choice_buttons.add(route_selector_dialog.findViewById(R.id.route_3));

        for (CardView cardView : routes_choice_buttons) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = cardView.toString().split("/")[1];
                    text = text.substring(0, text.length() - 1);
                    updateFloatingSelectorValue(text);
                }
            });
        }
    }

    /**
     * Method to Update the FLoading selector value
     * @param text
     * */
    private void updateFloatingSelectorValue(String text) {
        if (text.equals("all_routes")) {
            floating_selector.setText("All Routes");
        } else if (text.equals("route_1")) {
            floating_selector.setText("Route L1");
        } else if (text.equals("route_2")) {
            floating_selector.setText("Route L2");
        } else if (text.equals("route_3")) {
            floating_selector.setText("Route L3");
        }
        route_selector_dialog.hide();
        displayAllBusOnMap();
    }

    private void close_dialog() {
        route_selector_dialog.hide();
    }

    /**
     * Method to get all the Buses stop from the API
     * */
    private void getAllBusStops() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BusAPIRoutes busAPIRoutes = retrofit.create(BusAPIRoutes.class);
        Call<BusStopJSONResponse> call = busAPIRoutes.getBusStop();

        call.enqueue(new Callback<BusStopJSONResponse>() {
            @Override
            public void onResponse(Call<BusStopJSONResponse> call, Response<BusStopJSONResponse> response) {
                BusStopJSONResponse jsonResponse = response.body();
                all_buses_stops = new ArrayList<BusStop>(Arrays.asList(jsonResponse.getBusStop()));
                displayBusesStopsOnMap();
            }

            @Override
            public void onFailure(Call<BusStopJSONResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error while updating bus info", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method to display all the buses stop on the maps
     * */
    private void displayBusesStopsOnMap() {
        ArrayList<BitmapDescriptor> bitmapDescriptors = new ArrayList<>();

        for (BusStop busStop : all_buses_stops) {
            bitmapDescriptors.add(bitmapDescriptorFromVector(R.drawable.ic_baseline_brightness_blue_1_24));
        }
        if (floating_selector.getText().equals("All Routes")) {
            for (int i = 0; i < all_buses_stops.size(); i++) {
                gMap.addMarker(new MarkerOptions()
                        .position(new LatLng(all_buses_stops.get(i).getLongitude(), all_buses_stops.get(i).getLatitude()))
                        .icon(bitmapDescriptors.get(i))
                        .title("Bus Stop: " + all_buses_stops.get(i).getRoute_name())
                        .snippet(all_buses_stops.get(i).getBus_stop_name())
                );
            }
        } else {
            for (int i = 0; i < all_buses_stops.size(); i++) {
                if (floating_selector.getText().equals(all_buses_stops.get(i).getRoute_name())) {
                    gMap.addMarker(new MarkerOptions()
                            .position(new LatLng(all_buses_stops.get(i).getLongitude(), all_buses_stops.get(i).getLatitude()))
                            .icon(bitmapDescriptors.get(i))
                            .title("Bus Stop: " + all_buses_stops.get(i).getRoute_name())
                            .snippet(all_buses_stops.get(i).getBus_stop_name())
                    );
                }
            }
        }
    }

    /**
     * Method to get all the buses info
     * */
    private void getAllBusInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BusAPIRoutes busAPIRoutes = retrofit.create(BusAPIRoutes.class);
        Call<BusJSONResponse> call = busAPIRoutes.getBus();

        call.enqueue(new Callback<BusJSONResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<BusJSONResponse> call, Response<BusJSONResponse> response) {
                BusJSONResponse jsonResponse = response.body();
                busList = new ArrayList<Bus>(Arrays.asList(jsonResponse.getBus()));
                displayAllBusOnMap();
                BusTracking busTracking = new BusTracking();
                Thread t1 = new Thread(busTracking);
                t1.start();
            }

            @Override
            public void onFailure(Call<BusJSONResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error while updating bus info", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method to handle the show hide location buttons clicks
     * */
    private void handleShowHideLocation() {
        showLocation = !showLocation;
        if (showLocation) {
            getCurrentLocation();
        } else {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            gMap.setMyLocationEnabled(false);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng mindelo = new LatLng(16.886326, -24.986950);

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindelo, 15));

        drawRoutesPolygon();
    }

    /**
     * Method to display all the routes polygons on the map
     * */
    private void drawRoutesPolygon() {
        Polygon polygon;
        PolygonOptions route_polygon;
        Coordinates coordinates = new Coordinates();

        if(floating_selector.getText().equals("All Routes")){
            for(int i = 0; i < coordinates.getAll_routes_coordinates().size(); i++){
                route_polygon = new PolygonOptions()
                        .addAll(coordinates.getAll_routes_coordinates().get(i))
                        .strokeColor(coordinates.getColor_array().get(i));
                polygon = gMap.addPolygon(route_polygon);
            }
        }
        else{
            for(int i = 0; i < coordinates.getAll_routes_coordinates().size(); i++){
                if(floating_selector.getText().equals(coordinates.getRoutes_names().get(i))){
                    route_polygon = new PolygonOptions()
                            .addAll(coordinates.getAll_routes_coordinates().get(i))
                            .strokeColor(coordinates.getColor_array().get(i));
                    polygon = gMap.addPolygon(route_polygon);
                }
            }
        }
        if(my_location != null && showLocation){
            gMap.addMarker(new MarkerOptions()
                    .position(my_location)
                    .title("My location")
            );
        }
    }

    /**
     * Method to convert vector assets into BitmapDescriptor objects
     * @param vectorResId
     * @return BitmapDescriptor
     * */
    private BitmapDescriptor bitmapDescriptorFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(getActivity(), vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /**
     * Method to display all the buses on the map
     * */
    private void displayAllBusOnMap() {
        ArrayList<BitmapDescriptor> bitmapDescriptors = new ArrayList<>();

        for (Bus bus: busList){
            switch (bus.getRoute_color()){
                case "blue":
                    bitmapDescriptors.add(bitmapDescriptorFromVector(R.drawable.ic_baseline_location_blue_on_24));
                case "red":
                    bitmapDescriptors.add(bitmapDescriptorFromVector(R.drawable.ic_baseline_location_red_on_24));
                case "green":
                    bitmapDescriptors.add(bitmapDescriptorFromVector(R.drawable.ic_baseline_location_green_on_24));
            }
        }

        gMap.clear();
        drawRoutesPolygon();
        displayBusesStopsOnMap();

        if(floating_selector.getText().equals("All Routes")){
            for (int i = 0; i < busList.size(); i++) {
                gMap.addMarker(new MarkerOptions()
                        .position(new LatLng(busList.get(i).getLongitude(), busList.get(i).getLatitude()))
                        .icon(bitmapDescriptors.get(i))
                        .title(busList.get(i).getRegistration_plate().toString())
                        .snippet(busList.get(i).getTotal_seats() - busList.get(i).getPassengers_number()
                                + " available seats")
                );
            }
        }
        else{
            for (int i = 0; i < busList.size(); i++) {
                if(floating_selector.getText().equals(busList.get(i).getRoute_name())){
                    gMap.addMarker(new MarkerOptions()
                            .position(new LatLng(busList.get(i).getLongitude(), busList.get(i).getLatitude()))
                            .icon(bitmapDescriptors.get(i))
                            .title(busList.get(i).getRegistration_plate().toString())
                            .snippet(busList.get(i).getPassengers_number() + " Passengers on board")
                    );
                }
            }
        }
    }

    /**
     * Method to get user current location
     * */
    public void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            }
            return;
        }
        else{
            client_location = LocationServices.getFusedLocationProviderClient(getActivity());
            Task<Location> task = client_location.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        current_location = location;
                        my_location = new LatLng(current_location.getLatitude(),
                                current_location.getLongitude());
                        gMap.addMarker(new MarkerOptions()
                                .position(my_location)
                                .title("My location")
                        );
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(my_location, 16));
                    }
                }
            });
            gMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}