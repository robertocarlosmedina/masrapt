package com.example.masrapt.ui.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masrapt.BusRecyclerAdapter;
import com.example.masrapt.Dashboard_activity;
import com.example.masrapt.R;
import com.example.masrapt.databinding.FragmentDashboardBinding;
import com.example.masrapt.ui.home.Bus;
import com.example.masrapt.ui.home.BusAPIRoutes;
import com.example.masrapt.ui.home.BusDescription;
import com.example.masrapt.ui.home.BusJSONResponse;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private RecyclerView bus_recycler;
    private FragmentDashboardBinding binding;
    private ImageView image_iteration, waiting_image_iteration;
    private View root;
    private ArrayList<Bus> busList;
    private ArrayList<BusDescription> busList_recycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        busList_recycler = new ArrayList<>();
        busList = new ArrayList<>();

        bus_recycler = (RecyclerView) getActivity().findViewById(R.id.recycler_view_1);
        image_iteration = (ImageView) getActivity().findViewById(R.id.image_iteration);
        waiting_image_iteration = (ImageView) getActivity().findViewById(R.id.waiting_image_iteration);

        return root;
    }

    /**
     * Method to set the bus Recycler view adapter
     * */
    private void setBusAdapter(){
        BusRecyclerAdapter bus_adapter = new BusRecyclerAdapter(busList_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        bus_recycler.setLayoutManager(layoutManager);
        bus_recycler.setItemAnimator(new DefaultItemAnimator());
        bus_recycler.setAdapter(bus_adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus_recycler = (RecyclerView) getActivity().findViewById(R.id.recycler_view_1);
        image_iteration = (ImageView) getActivity().findViewById(R.id.image_iteration);
        waiting_image_iteration = (ImageView) getActivity().findViewById(R.id.waiting_image_iteration);
        setBusAdapter();
        getAllBusInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        bus_recycler = (RecyclerView) getActivity().findViewById(R.id.recycler_view_1);
        image_iteration = (ImageView) getActivity().findViewById(R.id.image_iteration);
        waiting_image_iteration = (ImageView) getActivity().findViewById(R.id.waiting_image_iteration);
        setBusAdapter();
        getAllBusInfo();
    }

    /**
     * Method to connect to the API and get the bus info
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
                // String text;
                BusJSONResponse jsonResponse = response.body();
                busList = new ArrayList<Bus>(Arrays.asList(jsonResponse.getBus()));
                for(Bus bus: busList){
                    busList_recycler.add(new BusDescription(
                            bus.getRegistration_plate(),
                            bus.getPassengers_number() + " passengers on board",
                            bus.getRoute_name(),
                            bus.getTotal_seats() - bus.getPassengers_number() + " available seats",
                            bus.getRoute_color()
                    ));
                }
                waiting_image_iteration.setVisibility(View.INVISIBLE);
                setBusAdapter();
            }

            @Override
            public void onFailure(Call<BusJSONResponse> call, Throwable t) {
                image_iteration.setVisibility(View.VISIBLE);
                waiting_image_iteration.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}