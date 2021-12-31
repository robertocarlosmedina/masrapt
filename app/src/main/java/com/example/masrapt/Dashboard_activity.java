package com.example.masrapt;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.masrapt.databinding.ActivityDashboardBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class Dashboard_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityDashboardBinding binding;
    private FloatingActionButton floatingActionButton;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView icon_user;
    private Dialog unLogDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_selector);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard_activity.this, "Route Selector clicked", Toast.LENGTH_SHORT).show();
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

        // <!-- android:background="?attr/colorPrimary"   USE COLOR IN THEMES -->

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        unLogDialog = new Dialog (Dashboard_activity.this);
        unLogDialog.setContentView(R.layout.user_unlogged_dialog);
        unLogDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_border));
        unLogDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        unLogDialog.setCancelable(true);

        unLogDialog = new Dialog (Dashboard_activity.this);
        unLogDialog.setContentView(R.layout.user_logged_dialog);
        unLogDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_border));
        unLogDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        unLogDialog.setCancelable(true);


    }

    public void openLoginInfo(){
        unLogDialog.show();
        // Intent intent = new Intent(this, ChangePassword.class);
        // startActivity(intent);
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
        System.out.print(item.getItemId());
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