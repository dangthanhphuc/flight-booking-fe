package com.example.flightbooking;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.flightbooking.adapters.NavigationItemAdapter;
import com.example.flightbooking.databinding.ActivityMainBinding;
import com.example.flightbooking.models.NavigationItem;
import com.example.flightbooking.network.HttpRequest;
import com.example.flightbooking.network.models.Specialty;
import com.example.flightbooking.network.api.TestService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<Specialty> specialties;
    private ArrayAdapter<String> specialtiesArrayAdapter;

    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    private NavigationItemAdapter navigationItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initial();
        loadData();

        TestService testService = HttpRequest.createService(TestService.class);

//        getSpecialties();

//        eventForBottomSheetBehavior();
    }

//    private void eventForBottomSheetBehavior() {
//        LinearLayout linearLayout = findViewById(binding.lnBottomSheet.getId());
//        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
//
//        // State
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//
//        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });
//
//    }


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
                new NavigationItem("Trang chủ", R.drawable.baseline_home_24, "Path"),
                new NavigationItem("Lịch bay", R.drawable.airplane_ticket_24, "Path"),
                new NavigationItem("Quản lý đặt chỗ", R.drawable.baseline_folder_24, "Path"),
                new NavigationItem("My Profile", R.drawable.person_24, "Path"),
                new NavigationItem("Trang chủ 4", R.drawable.baseline_home_24, "Path"),
                new NavigationItem("Trang chủ 5", R.drawable.baseline_home_24, "Path")
        ));
        navigationItemAdapter = new NavigationItemAdapter(MainActivity.this, R.layout.navigation_item, navigationItems);
        binding.grdView.setAdapter(navigationItemAdapter);
    }

//    private void getSpecialties() {
//        Call<List<Specialty>> specialtiesCall = testService.getSpecialties();
//        specialtiesCall.enqueue(
//                new Callback<List<Specialty>>() {
//                    @Override
//                    public void onResponse(@NonNull Call<List<Specialty>> call, @NonNull Response<List<Specialty>> response) {
//                        if (response.isSuccessful()){
//                            specialties = response.body();
//                            loadData();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<List<Specialty>> call, @NonNull Throwable throwable) {
//                        System.out.println(throwable);
//                    }
//                }
//        );
//    }






}