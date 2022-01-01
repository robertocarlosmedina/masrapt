package com.example.masrapt;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLanguage;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView icon_user, close_icon;
    private Dialog unLoggedDialog, loggedDialog, route_selector_dialog;
    private TextView login, floating_selector;

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

        unLoggedDialog = new Dialog (Dashboard_activity.this);
        unLoggedDialog.setContentView(R.layout.user_unlogged_dialog);
        unLoggedDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        unLoggedDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        unLoggedDialog.setCancelable(true);

        close_icon = (ImageView) unLoggedDialog.findViewById(R.id.close_icon);
        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_dialog();
            }
        });

        login = (TextView) unLoggedDialog.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_activity.this, TelaLogin.class);
                startActivity(intent);
            }
        });

        loggedDialog = new Dialog (Dashboard_activity.this);
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

        route_selector_dialog = new Dialog (Dashboard_activity.this);
        route_selector_dialog.setContentView(R.layout.select_route_dialog);
        route_selector_dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        route_selector_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        route_selector_dialog.setCancelable(true);

        floating_selector = (TextView) findViewById(R.id.floating_selector);
        floating_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route_selector_dialog.show();
            }
        });

        close_icon = (ImageView) route_selector_dialog.findViewById(R.id.close_icon);
        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_dialog();
            }
        });
    }

    public void openLoginInfo(){
        // loggedDialog.show();
        unLoggedDialog.show();
        // Intent intent = new Intent(this, ChangePassword.class);
        // startActivity(intent);
    }

    public void close_dialog(){
        unLoggedDialog.hide();
        loggedDialog.hide();
        route_selector_dialog.hide();
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