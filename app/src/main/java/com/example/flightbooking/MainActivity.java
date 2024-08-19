package com.example.flightbooking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.flightbooking.adapters.BannerAdapter;
import com.example.flightbooking.adapters.LocationAdapter;
import com.example.flightbooking.adapters.NavigationItemAdapter;
import com.example.flightbooking.auth.AuthManager;
import com.example.flightbooking.databinding.ActivityMainBinding;
import com.example.flightbooking.models.NavigationItem;
import com.example.flightbooking.models.OnItemClickListener;
import com.example.flightbooking.network.HttpRequest;
import com.example.flightbooking.network.api.AirportService;
import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.responses.AirportResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private AirportService airportService;

    private ActivityMainBinding binding;
    private AuthManager authManager;
    private NavigationItemAdapter navigationItemAdapter;

    private BannerAdapter bannerAdapter;
    private int currentPage = 0;
    private Handler handler;
    private Runnable runnable;

    private LocationAdapter locationAdapter;
    private List<AirportResponse> locationList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authManager = new AuthManager(this);
        airportService = HttpRequest.createService(AirportService.class, this);

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
        sheetBehavior.setPeekHeight(320);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void loadData() {
        List<NavigationItem> navigationItems = new ArrayList<>(Arrays.asList(
                new NavigationItem("Trang chủ", R.drawable.baseline_home_24, "home"),
                new NavigationItem("Lịch bay", R.drawable.airplane_ticket_24, "flight_search"),
                new NavigationItem("Quản lý đặt chỗ", R.drawable.baseline_folder_24, "booking_management"),
                new NavigationItem("My Profile", R.drawable.person_24, "my_profile"),
                new NavigationItem("Đăng xuất", R.drawable.baseline_logout_244, "logout")
        ));
        navigationItemAdapter = new NavigationItemAdapter(MainActivity.this, R.layout.navigation_item, navigationItems);
        binding.grdView.setAdapter(navigationItemAdapter);

        List<Integer> bannerImages = Arrays.asList(
                R.drawable.banner_1,
                R.drawable.banner_2,
                R.drawable.banner_3,
                R.drawable.banner_4,
                R.drawable.banner_5
        );
        bannerAdapter = new BannerAdapter(bannerImages);
        binding.viewPagerBanner.setAdapter(bannerAdapter);
        // Handler để tự động chuyển trang
        handler = new Handler(Looper.getMainLooper());
        // Tự động chuyển trang
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == bannerImages.size()) {
                    currentPage = 0;
                }
                binding.viewPagerBanner.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // 3 giây chuyển trang
            }
        };
        // Bắt đầu tự động chuyển trang
        handler.postDelayed(runnable, 3000);


        binding.recyclerViewLocations.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        locationAdapter = new LocationAdapter(this, locationList);
        binding.recyclerViewLocations.setAdapter(locationAdapter);
        locationAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AirportResponse clickedLocation = locationList.get(position);
                Toast.makeText(MainActivity.this, "Clicked: " + clickedLocation.getCountry(), Toast.LENGTH_SHORT).show();
            }
        });
        getAirports();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private void getAirports() {
        Call<Response<List<AirportResponse>>> call = airportService.getAirports();
        call.enqueue(new Callback<Response<List<AirportResponse>>>() {
            @Override
            public void onResponse(@NonNull Call<Response<List<AirportResponse>>> call, @NonNull retrofit2.Response<Response<List<AirportResponse>>> response) {
                if(response.isSuccessful()) {
                    locationList.addAll(response.body().getData());
                    locationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response<List<AirportResponse>>> call, @NonNull Throwable throwable) {
                Toast.makeText(MainActivity.this, "Failled", Toast.LENGTH_SHORT).show();
            }
        });
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

                }

            }
        });

    }



}