package com.example.masrapt.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

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

public class HomeFragment extends Fragment implements OnMapReadyCallback{

    private View homeViewModel;
    private GoogleMap gMap;
    private TextView coordenates;
    private FragmentHomeBinding binding;
    private Location current_location;
    private ArrayList<LatLng> lat_long = new ArrayList<>();
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client_location;
    private int REQUEST_CODE = 111;

    private ArrayList<Route> routesList;
    private ArrayList<RouteDescription> routesList_recycl;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = inflater.inflate(R.layout.fragment_home, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(
                R.id.google_maps);
        mapFragment.getMapAsync(this);

        routesList = new ArrayList<>();
        routesList_recycl = new ArrayList<>();

        client_location = LocationServices.getFusedLocationProviderClient(getActivity());

        //getRoutesInfo();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // getCurrentLocation();
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }


        
        return homeViewModel;
    }

    @Override
    public void onStart() {
        super.onStart();
        coordenates = (TextView) getActivity().findViewById(R.id.test_display);
        coordenates.setText("Okokokok");
        getRoutesInfo();
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        LatLng mindelo = new LatLng(16.886326,-24.986950);
        LatLng m0 = new LatLng(16.88606433352687, -24.98629148762652);
        LatLng m1 = new LatLng(16.88581641052961, -24.98632020492875);
        LatLng m2 = new LatLng(16.88553631093369, -24.98637987539332);
        LatLng m3 = new LatLng(16.88533401122892, -24.98643186094823);
        LatLng m4 = new LatLng(16.8852642892178, -24.98615465095456);
        LatLng m5 = new LatLng(16.88559617763656, -24.98607147102824);
        LatLng m6 = new LatLng(16.88597070890412, -24.98599768512697);
        LatLng m7 = new LatLng(16.88590797133129, -24.98553279651804);
        LatLng m8 = new LatLng(16.88585291771152, -24.98517512473801);
        LatLng m9 = new LatLng(16.88579069520526, -24.98464214963741);
        LatLng m10 = new LatLng(16.88574995479092, -24.98414048675458);
        LatLng m11 = new LatLng(16.88578203322842, -24.98373642886931);
        LatLng m12 = new LatLng(16.88568609601361, -24.98337623528259);
        LatLng m13 = new LatLng(16.88564044608639, -24.98294926679576);
        LatLng m14 = new LatLng(16.88570488281033, -24.98249928125145);
        LatLng m15 = new LatLng(16.88563831017057, -24.98202169716476);
        LatLng m16 = new LatLng(16.88558714627003, -24.98157753572666);
        LatLng m17 = new LatLng(16.88548401758224, -24.98119665572729);
        LatLng m18 = new LatLng(16.88533983441716, -24.98075353452506);
        LatLng m19 = new LatLng(16.88526209428177, -24.98033635761292);
        LatLng m20 = new LatLng(16.88529972772972, -24.9798995583594);
        LatLng m21 = new LatLng(16.88540237050413, -24.97945021004677);
        LatLng m22 = new LatLng(16.88551128443155, -24.9790685705698);
        LatLng m23 = new LatLng(16.88554121925686, -24.97875340666548);
        LatLng m24 = new LatLng(16.88595822755225, -24.97892936082148);
        LatLng m25 = new LatLng(16.88643653974191, -24.97916557852084);
        LatLng m26 = new LatLng(16.88692096373541, -24.97936736850087);
        LatLng m27 = new LatLng(16.88736685409554, -24.97960758171477);
        LatLng m28 = new LatLng(16.8878070486269, -24.97978323208909);
        LatLng m29 = new LatLng(16.88826150395278, -24.97997844643842);
        LatLng m30 = new LatLng(16.8888035247211, -24.98016315581063);
        LatLng m31 = new LatLng(16.88938646881791, -24.9804106649505);
        LatLng m32 = new LatLng(16.88999417836212, -24.98062202335055);
        LatLng m33 = new LatLng(16.8907291648407, -24.980902346896);
        LatLng m34 = new LatLng(16.89123301822837, -24.98116298873003);
        LatLng m35 = new LatLng(16.89163837120334, -24.98157596686816);
        LatLng m36 = new LatLng(16.89190934107216, -24.98198806546086);
        LatLng m37 = new LatLng(16.89212972304748, -24.98245862079255);
        LatLng m38 = new LatLng(16.8922167568488, -24.9828007310516);
        LatLng m39 = new LatLng(16.89225691027207, -24.98313422688832);
        LatLng m40 = new LatLng(16.89173061369002, -24.9833852461767);
        LatLng m41 = new LatLng(16.89129634978105, -24.98366467503281);
        LatLng m42 = new LatLng(16.89093946483473, -24.98393605795271);
        LatLng m43 = new LatLng(16.89066091671846, -24.98429388624523);
        LatLng m44 = new LatLng(16.89074888536842, -24.98472330656572);
        LatLng m45 = new LatLng(16.89115671313244, -24.98500182113498);
        LatLng m46 = new LatLng(16.89152370365731, -24.985309624424);
        LatLng m47 = new LatLng(16.8918756874504, -24.98573375765937);
        LatLng m48 = new LatLng(16.89210993385963, -24.98620898344038);
        LatLng m49 = new LatLng(16.89229603435444, -24.98652665903384);
        LatLng m50 = new LatLng(16.89242468983358, -24.98705194482634);
        LatLng m51 = new LatLng(16.89250231489389, -24.98766162191514);
        LatLng m52 = new LatLng(16.89260630086517, -24.98830968928252);
        LatLng m53 = new LatLng(16.89267817779694, -24.98901208721236);
        LatLng m54 = new LatLng(16.89278643749336, -24.98985265049949);
        LatLng m55 = new LatLng(16.89293094874364, -24.99074454443494);
        LatLng m56 = new LatLng(16.89304835435065, -24.99150497502455);
        LatLng m57 = new LatLng(16.89326860013824, -24.9922594758812);
        LatLng m58 = new LatLng(16.89292803652718, -24.99273933038166);
        LatLng m59 = new LatLng(16.89243140949482, -24.99324198585118);
        LatLng m60 = new LatLng(16.89190562334231, -24.99347722886432);
        LatLng m61 = new LatLng(16.8914325409305, -24.99380321046601);
        LatLng m62 = new LatLng(16.89080703054731, -24.99407489978546);
        LatLng m63 = new LatLng(16.89003571575983, -24.99385845500998);
        LatLng m64 = new LatLng(16.88937948990977, -24.99343087793348);
        LatLng m65 = new LatLng(16.88914869239382, -24.99274955752854);
        LatLng m66 = new LatLng(16.8889530596743, -24.99197501436225);
        LatLng m67 = new LatLng(16.8889569889635, -24.99133637302979);
        LatLng m68 = new LatLng(16.88898404469682, -24.99065012487826);
        LatLng m69 = new LatLng(16.88875038926453, -24.9899856955643);
        LatLng m70 = new LatLng(16.88826652071406, -24.98952557865027);
        LatLng m71 = new LatLng(16.88751054695956, -24.98926109566973);
        LatLng m72 = new LatLng(16.88660775508751, -24.98919713190032);
        LatLng m73 = new LatLng(16.88681332070872, -24.98886484821954);
        LatLng m74 = new LatLng(16.88669071256902, -24.9882586943791);
        LatLng m75 = new LatLng(16.88660867320069, -24.98786369709445);
        LatLng m76 = new LatLng(16.88651818608574, -24.98745598056123);
        LatLng m77 = new LatLng(16.88626012761841, -24.98723352237617);
        LatLng m78 = new LatLng(16.88615710836407, -24.98683509111975);
        LatLng m79 = new LatLng(16.88606433352687, -24.98629148762652);
        lat_long.add(m0);
        lat_long.add(m1);
        lat_long.add(m2);
        lat_long.add(m3);
        lat_long.add(m4);
        lat_long.add(m5);
        lat_long.add(m6);
        lat_long.add(m7);
        lat_long.add(m8);
        lat_long.add(m9);
        lat_long.add(m10);
        lat_long.add(m11);
        lat_long.add(m12);
        lat_long.add(m13);
        lat_long.add(m14);
        lat_long.add(m15);
        lat_long.add(m16);
        lat_long.add(m17);
        lat_long.add(m18);
        lat_long.add(m19);
        lat_long.add(m20);
        lat_long.add(m21);
        lat_long.add(m22);
        lat_long.add(m23);
        lat_long.add(m24);
        lat_long.add(m25);
        lat_long.add(m26);
        lat_long.add(m27);
        lat_long.add(m28);
        lat_long.add(m29);
        lat_long.add(m30);
        lat_long.add(m31);
        lat_long.add(m32);
        lat_long.add(m33);
        lat_long.add(m34);
        lat_long.add(m35);
        lat_long.add(m36);
        lat_long.add(m37);
        lat_long.add(m38);
        lat_long.add(m39);
        lat_long.add(m40);
        lat_long.add(m41);
        lat_long.add(m42);
        lat_long.add(m43);
        lat_long.add(m44);
        lat_long.add(m45);
        lat_long.add(m46);
        lat_long.add(m47);
        lat_long.add(m48);
        lat_long.add(m49);
        lat_long.add(m50);
        lat_long.add(m51);
        lat_long.add(m52);
        lat_long.add(m53);
        lat_long.add(m54);
        lat_long.add(m55);
        lat_long.add(m56);
        lat_long.add(m57);
        lat_long.add(m58);
        lat_long.add(m59);
        lat_long.add(m60);
        lat_long.add(m61);
        lat_long.add(m62);
        lat_long.add(m63);
        lat_long.add(m64);
        lat_long.add(m65);
        lat_long.add(m66);
        lat_long.add(m67);
        lat_long.add(m68);
        lat_long.add(m69);
        lat_long.add(m70);
        lat_long.add(m71);
        lat_long.add(m72);
        lat_long.add(m73);
        lat_long.add(m74);
        lat_long.add(m75);
        lat_long.add(m76);
        lat_long.add(m77);
        lat_long.add(m78);
        lat_long.add(m79);

        gMap.addMarker(new MarkerOptions()
                .position(mindelo)
                .title("Marker in mindelo"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mindelo, 15
        ));
        PolygonOptions route_polygon = new PolygonOptions()
                .addAll(lat_long)
                .strokeColor(Color.BLUE);
        Polygon polygon = gMap.addPolygon(route_polygon);
    }

    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            coordenates.setText(" Don't have location");
            return;
        }
        Task<Location> task = client_location.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    current_location = location;
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            LatLng latlng = new LatLng(current_location.getLatitude(), current_location.getLongitude());
                            //LatLng latlng = new LatLng(16.89, -24.98);
                            coordenates.setText("alt: "+current_location.getLatitude()+" log: "+current_location.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("You are Here");

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 5));

                            googleMap.addMarker(markerOptions).showInfoWindow();
                        }
                    });
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
                for (Route route: routesList) {
                    text = text + " " + route.getName() +" ";
                }
                coordenates.setText(text);

            }

            @Override
            public void onFailure(Call<RouteJSONResponse> call, Throwable t) {

            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}