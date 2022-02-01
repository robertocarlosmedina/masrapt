package com.example.masrapt;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masrapt.ui.home.Bus;
import com.example.masrapt.ui.home.BusAPIRoutes;
import com.example.masrapt.ui.home.BusDescription;
import com.example.masrapt.ui.home.BusJSONResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.masrapt.databinding.ActivityDashboardBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActivityDashboardBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView icon_user, close_icon;
    private Dialog unLoggedDialog, loggedDialog;
    private TextView login, coord_display;
    private FusedLocationProviderClient client_location;
    private SupportMapFragment mapFragment;
    private final int REQUEST_CODE = 111;
    private Location current_location;
    private RecyclerView bus_recycler;
    private ArrayList<Bus> busList;
    private ArrayList<BusDescription> busList_recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        busList = new ArrayList<>();
        busList_recycler = new ArrayList<>();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_dashboard);
        NavigationUI.setupWithNavController(binding.navView, navController);

        icon_user = (ImageView) findViewById(R.id.user_icon);
        icon_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginInfo();
            }
        });


        // Hide or show item
        // Menu menu = navigationView.getMenu();
        // menu.findItem(R.id.log_out).setVisible(false);

        /* drawer hooks
         * */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.drawer_nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Dialog box to the user that isn't logged
        unLoggedDialog = new Dialog(Dashboard_activity.this);
        unLoggedDialog.setContentView(R.layout.user_unlogged_dialog);
        unLoggedDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        unLoggedDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        unLoggedDialog.setCancelable(true);

        login = (TextView) unLoggedDialog.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_activity.this, TelaLogin.class);
                startActivity(intent);
            }
        });

        // Dialog box to the logged user
        loggedDialog = new Dialog(Dashboard_activity.this);
        loggedDialog.setContentView(R.layout.user_logged_dialog);
        loggedDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        loggedDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loggedDialog.setCancelable(true);

        close_icon = (ImageView) loggedDialog.findViewById(R.id.close_icon);
        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_dialog();
            }
        });
    }

    public void openLoginInfo(){
        unLoggedDialog.show();
    }

    public void close_dialog(){
        unLoggedDialog.hide();
        loggedDialog.hide();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(Dashboard_activity.this, About.class);
                startActivity(intent);
                break;
            case R.id.settings:
                Intent intent_1 = new Intent(Dashboard_activity.this, Settings.class);
                startActivity(intent_1);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}