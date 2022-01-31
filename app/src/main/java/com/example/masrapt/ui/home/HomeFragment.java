package com.example.masrapt.ui.home;

import android.Manifest;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.masrapt.Coordinates;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private View homeViewModel;
    private GoogleMap gMap;
    private TextView coordenates, map_type;
    private FragmentHomeBinding binding;
    private Location current_location;
    private ArrayList<LatLng> lat_long = new ArrayList<>();
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client_location;
    private int REQUEST_CODE = 111;

    private ArrayList<Route> routesList;
    private ArrayList<Bus> busList;
    private ArrayList<RouteDescription> routesList_recycl;
    private ArrayList<RouteCoordinate> routeCoordinates;
    private ArrayList<ArrayList<RouteCoordinate>> all_routesCoordinates;

    public class BusTracking implements Runnable {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            try {
                synchronized (this) {
                    wait(6000);
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

        routesList = new ArrayList<>();
        routesList_recycl = new ArrayList<>();
        routeCoordinates = new ArrayList<>();
        all_routesCoordinates = new ArrayList<>();
        busList = new ArrayList<>();

        client_location = LocationServices.getFusedLocationProviderClient(getActivity());

        //getRoutesInfo();
        // getCurrentLocation();

        return homeViewModel;
    }

    private void changeMapType() {
        String current_type = map_type.getText().toString();
        if (current_type.equals("Satellite")){
            gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            map_type.setText("Normal");
        }
        else{
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map_type.setText("Satellite");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        coordenates = (TextView) getActivity().findViewById(R.id.test_display);
        coordenates.setText("okok");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        map_type = (TextView) getActivity().findViewById(R.id.map_type);
        map_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMapType();
            }
        });
        getAllBusInfo();
        // getRoutesInfo();
    }

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
                // String text;
                BusJSONResponse jsonResponse = response.body();
                busList = new ArrayList<Bus>(Arrays.asList(jsonResponse.getBus()));
                /*
                text = "";
                for (Bus bus : busList) {
                    text += "\n" +
                            "\tid: " + bus.getId() +
                            "\tcurrent_sequence_nr: " + bus.getCurrent_sequence_number() +
                            "\tlongitude: " + bus.getLongitude() +
                            "\tlatitude: " + bus.getLatitude() +
                            "\tstate: " + bus.getState() +
                            "\tid_route: " + bus.getId_route();
                }
                */
                displayAllBusOnMap();
                BusTracking busTracking = new BusTracking();
                Thread t1 = new Thread(busTracking);
                t1.start();
                // coordenates.setText(text);
                // coordenates.setText("");
            }

            @Override
            public void onFailure(Call<BusJSONResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error while updating bus info", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng mindelo = new LatLng(16.886326, -24.986950);

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindelo, 15));

        drawRoutesPolygon();

        // getRoutesInfo();
    }

    private void drawRoutesPolygon() {
        Polygon polygon;
        PolygonOptions route_polygon;
        Coordinates coordinates = new Coordinates();
        route_polygon = new PolygonOptions()
                .addAll(coordinates.getLat_long_route_1())
                .strokeColor(coordinates.getColor_route_1());
        polygon = gMap.addPolygon(route_polygon);

        route_polygon = new PolygonOptions()
                .addAll(coordinates.getLat_long_route_2())
                .strokeColor(coordinates.getColor_route_2());
        polygon = gMap.addPolygon(route_polygon);

        route_polygon = new PolygonOptions()
                .addAll(coordinates.getLat_long_route_3())
                .strokeColor(coordinates.getColor_route_3());
        polygon = gMap.addPolygon(route_polygon);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(getActivity(), vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

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

        for (int i = 0; i < busList.size(); i++) {
            gMap.addMarker(new MarkerOptions()
                    .position(new LatLng(busList.get(i).getLongitude(), busList.get(i).getLatitude()))
                    .icon(bitmapDescriptors.get(i))
                    .title("Marker of the bus")
                    .snippet("fwejfnjwef fjwe fwekj ")
            );
        }
    }

    public void getCurrentLocation() {
        coordenates.setText("geting location");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            coordenates.setText(" Don't have location");
            return;
        }
        Task<Location> task = client_location.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                coordenates.setText("oN sucess message");
                if (location != null) {
                    current_location = location;
                    //mapFragment.getMapAsync(new OnMapReadyCallback() {
                    //@Override
                    //public void onMapReady(@NonNull GoogleMap googleMap) {
                    LatLng latlng = new LatLng(current_location.getLatitude(), current_location.getLongitude());
                    //LatLng latlng = new LatLng(16.89, -24.98);
                    coordenates.setText("alt: " + current_location.getLatitude() + " log: " + current_location.getLongitude());
                    // MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("You are Here");

                    // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 5));

                    // googleMap.addMarker(markerOptions).showInfoWindow();
                    //});
                }
            }
        });
    }

    private void getRoutesInfo() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RoutesAPIRoutes routesAPIRoutes = retrofit.create(RoutesAPIRoutes.class);

        Call<RouteJSONResponse> call = routesAPIRoutes.getRoutes();

        call.enqueue(new Callback<RouteJSONResponse>() {
            @Override
            public void onResponse(Call<RouteJSONResponse> call, Response<RouteJSONResponse> response) {
                RouteJSONResponse jsonResponse = response.body();

                routesList = new ArrayList<Route>(Arrays.asList(jsonResponse.getRoutes()));
                String text = "";
                for (Route route : routesList) {
                    getRoutesCoordinates((int) route.getId());
                    text = text + " " + route.getName() + " ";
                }
                coordenates.setText(text);

            }

            @Override
            public void onFailure(Call<RouteJSONResponse> call, Throwable t) {

            }
        });
    }

    private void getRoutesCoordinates(int id_route) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoordinatesAPIRoutes coordinatesAPIRoutes = retrofit.create(
                CoordinatesAPIRoutes.class
        );

        Call<RouteCoordinatesJSONResponse> call = coordinatesAPIRoutes.getRoutesCoordinates(id_route);

        call.enqueue(new Callback<RouteCoordinatesJSONResponse>() {
            @Override
            public void onResponse(Call<RouteCoordinatesJSONResponse> call, Response<RouteCoordinatesJSONResponse> response) {
                RouteCoordinatesJSONResponse jsonResponse = response.body();

                routeCoordinates = new ArrayList<RouteCoordinate>(Arrays.asList(jsonResponse.getCoordinates()));
                all_routesCoordinates.add(routeCoordinates);
                String text = "";
                for (RouteCoordinate route : routeCoordinates) {
                    text = text + " " + route.getId_route() + " ";
                }
                // updateRoutesOnMap();
                coordenates.setText(text);
            }

            @Override
            public void onFailure(Call<RouteCoordinatesJSONResponse> call, Throwable t) {

            }
        });


    }

    public void updateRoutesOnMap() {
        LatLng coordinates;
        String text = "routes: ";
        PolygonOptions route_polygon;
        Toast.makeText(getActivity(), "Updating routes " + all_routesCoordinates.size(), Toast.LENGTH_SHORT).show();
        for (ArrayList<RouteCoordinate> arrayList : all_routesCoordinates) {
            Toast.makeText(getActivity(), "Starting list ", Toast.LENGTH_SHORT).show();
            coordinates = null;
            text = text + "   !  ";
            lat_long.clear();
            for (RouteCoordinate routeCoordinate : arrayList) {
                Toast.makeText(getActivity(), "Adding coordenates " + routeCoordinate.getId_route(), Toast.LENGTH_SHORT).show();
                coordinates = new LatLng(routeCoordinate.getLatitude(), routeCoordinate.getLongitude());
                lat_long.add(coordinates);
                text = text + " " + routeCoordinate.getId_coordinates() + " ";
            }
            route_polygon = new PolygonOptions()
                    .addAll(lat_long)
                    .strokeColor(Color.BLUE);
            Polygon polygon = gMap.addPolygon(route_polygon);
            coordenates.setText(text);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}