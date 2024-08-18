package com.example.flightbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.flightbooking.adapters.NavigationItemAdapter;
import com.example.flightbooking.auth.AuthManager;
import com.example.flightbooking.databinding.ActivityMainBinding;
import com.example.flightbooking.models.NavigationItem;
import com.example.flightbooking.network.HttpRequest;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AuthManager authManager;
    private NavigationItemAdapter navigationItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authManager = new AuthManager(this);

        initial();
        loadData();
        addEvent();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void initial() {
        // Action Bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Bottom Sheet
        BottomSheetBehavior<FrameLayout> sheetBehavior = BottomSheetBehavior.from(binding.sheet);
        sheetBehavior.setPeekHeight(300);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void loadData() {
        List<NavigationItem> navigationItems = new ArrayList<>(Arrays.asList(
                new NavigationItem("Trang chủ", R.drawable.baseline_home_24, "home"),
                new NavigationItem("Lịch bay", R.drawable.airplane_ticket_24, "flight_search"),
                new NavigationItem("Quản lý đặt chỗ", R.drawable.baseline_folder_24, "booking_management"),
                new NavigationItem("My Profile", R.drawable.person_24, "my_profile"),
                new NavigationItem("Đăng xuất", R.drawable.baseline_logout_24, "logout"),
                new NavigationItem("Trang chủ 5", R.drawable.baseline_home_24, "lookup")
        ));
        navigationItemAdapter = new NavigationItemAdapter(MainActivity.this, R.layout.navigation_item, navigationItems);
        binding.grdView.setAdapter(navigationItemAdapter);
    }

    private void addEvent(){
        binding.grdView.setOnItemClickListener(new AdapterView. OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavigationItem navigationItem = navigationItemAdapter.getNavigationItems().get(position);
                Intent intent = null;
                switch (navigationItem.getPath()){
                    case "logout":
                        authManager.logout();
                        break;
                    case "flight_search":
                        intent = new Intent(MainActivity.this, FlightSearchActivity.class);
                        startActivity(intent);
                        break;
                    case "booking_management":
                        intent = new Intent(MainActivity.this, BookingManagementActivity.class);
                        startActivity(intent);
                        break;
                    case "my_profile":
                        intent = new Intent(MainActivity.this, MyProfileActivity.class);
                        startActivity(intent);
                        break;
                    case "lookup":
                        intent = new Intent(MainActivity.this, LookupInformationActivity.class);
                        startActivity(intent);
                        break;

                }

            }
        });
    }

}