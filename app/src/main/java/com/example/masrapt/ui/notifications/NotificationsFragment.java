package com.example.masrapt.ui.notifications;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masrapt.R;
import com.example.masrapt.RouteDescription;
import com.example.masrapt.RoutesRecyclerAdapter;
import com.example.masrapt.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private RecyclerView recyclerView;
    private FragmentNotificationsBinding binding;
    private ArrayList<Route> routesList;
    private ArrayList<RouteDescription> routesList_recycl;
    private ImageView image_iteration, waiting_image_iteration;
    private boolean data_already_get = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        routesList = new ArrayList<>();
        routesList_recycl = new ArrayList<>();

        return root;
    }

    /**
     * Method to set the routes Recycler view adapter
     * */
    private void setRouteAdapter(){
        waiting_image_iteration.setVisibility(View.INVISIBLE);
        RoutesRecyclerAdapter route_adapter = new RoutesRecyclerAdapter(routesList_recycl);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(route_adapter);
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

    /**
     * Method that connect to the API and get all the routes info
     * */
    private void setRoutesInfo() {
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
                for (Route route: routesList) {
                    routesList_recycl.add(new RouteDescription(route.getName(), route.getLocations(),
                            String.valueOf(route.getActive_bus())+ " active",
                            String.valueOf(route.getRoute_timer())+" mn"));
                }
                setRouteAdapter();
            }

            @Override
            public void onFailure(Call<RouteJSONResponse> call, Throwable t) {
                waiting_image_iteration.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Server not responding",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!data_already_get){
            recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
            image_iteration = (ImageView) getActivity().findViewById(R.id.image_iteration);
            waiting_image_iteration = (ImageView) getActivity().findViewById(R.id.waiting_image_iteration);
            setRouteAdapter();
            if (checkInternetConnection()){
                setRoutesInfo();
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
            recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
            image_iteration = (ImageView) getActivity().findViewById(R.id.image_iteration);
            waiting_image_iteration = (ImageView) getActivity().findViewById(R.id.waiting_image_iteration);
            setRouteAdapter();
            if (checkInternetConnection()){
                setRoutesInfo();
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}