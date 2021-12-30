package com.example.masrapt;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.masrapt.databinding.ActivityDashboardBinding;
import com.google.android.material.navigation.NavigationView;

public class Dashboard_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityDashboardBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView icon_user;

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

        // Hidde or show item
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
    }
    public void openLoginInfo(){
        Intent intent = new Intent(this, TelaLogin.class);
        startActivity(intent);
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