package com.example.masrapt;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masrapt.ui.home.BusAPIRoutes;
import com.example.masrapt.ui.home.BusStop;
import com.example.masrapt.ui.home.BusStopJSONResponse;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusStops#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusStops extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<BusStop> all_buses_stops = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView image_iteration, waiting_image_iteration;
    private boolean data_already_get = false;

    public BusStops() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusStops.
     */
    // TODO: Rename and change types and number of parameters
    public static BusStops newInstance(String param1, String param2) {
        BusStops fragment = new BusStops();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public boolean checkInternetConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!data_already_get){
            recyclerView = (RecyclerView) getActivity().findViewById(R.id.bus_stops_recycler_view);
            image_iteration = (ImageView) getActivity().findViewById(R.id.image_iteration);
            waiting_image_iteration = (ImageView) getActivity().findViewById(R.id.waiting_image_iteration);
            setBusStopsAdapterInfo();
            if (checkInternetConnection()){
                getAllBusStops();
                data_already_get = true;
            }
            else{
                image_iteration.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Please check your internet connection",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!data_already_get){
            recyclerView = (RecyclerView) getActivity().findViewById(R.id.bus_stops_recycler_view);
            image_iteration = (ImageView) getActivity().findViewById(R.id.image_iteration);
            waiting_image_iteration = (ImageView) getActivity().findViewById(R.id.waiting_image_iteration);
            setBusStopsAdapterInfo();
            if (checkInternetConnection()){
                getAllBusStops();
                data_already_get = true;
            }
            else{
                image_iteration.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Please check your internet connection",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setBusStopsAdapterInfo() {
        waiting_image_iteration.setVisibility(View.INVISIBLE);

        BusStopsRecyclerAdapter route_adapter = new BusStopsRecyclerAdapter(all_buses_stops);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(route_adapter);
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
                setBusStopsAdapterInfo();
            }

            @Override
            public void onFailure(Call<BusStopJSONResponse> call, Throwable t) {
                waiting_image_iteration.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Server not responding",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bus_stops, container, false);
    }
}