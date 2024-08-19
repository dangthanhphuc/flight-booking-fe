package com.example.flightbooking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flightbooking.databinding.ActivityFindFlightBinding;
import com.example.flightbooking.databinding.BottomSheetLayoutDepartureBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;

public class FindFlightActivity extends AppCompatActivity {
    ActivityFindFlightBinding binding;

    ActivityResultLauncher<Intent> activityResultLauncher ;

    private int selectedDay;
    private int selectedMonth;
    private int selectedYear;

    private String[] departures = {"HAN - Hà Nội, Việt Nam", "SGN - Hồ Chí Minh, Việt Nam", "DAD - Đà Nẵng,Việt Nam", "PQC - Phú Quốc, Việt Nam"};
    private String[] destinations = {"PQC - Phú Quốc, Việt Nam", "HAN - Hà Nội, Việt Nam", "HPH - Hải Phòng, Việt Nam", "VCS - Côn Đảo, Việt Nam"};

    private String selectedDeparture;
    private String selectedDestination;

    private String selectedPassenger;
    private String selectedQuantityPassenger;
    private String selectedTypePassenger;
    private String[] passengers = {"01 - Hành khách, 1 Người lớn ", "01 - Hành khách, 1 Trẻ em", "02 - Hành khách, 2 Người lớn",
                                    "02 - Hành khách, 1 Người lớn & 1 Trẻ em"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFindFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        addEvents();
    }

    private void addEvents() {
        binding.txtDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        binding.txtDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDeparture();
            }
        });

        binding.txtDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDestination();
            }
        });

        binding.lsvSelectPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetPassenger();
            }
        });

        binding.btnChangeAirport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapAirport();
            }
        });

        binding.btnFindFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to do
            }

        });
    }

    private String[] filterArray(String[] originalArray, String itemToFilterOut) {
        if (itemToFilterOut == null || itemToFilterOut.isEmpty()) {
            return originalArray;
        }

        ArrayList<String> filteredList = new ArrayList<>();
        for (String item : originalArray) {
            if (!item.equals(itemToFilterOut)) {
                filteredList.add(item);
            }
        }
        return filteredList.toArray(new String[0]);
    }

    //############# Chon noi di ###################################################################
    private void showBottomSheetDeparture() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FindFlightActivity.this);

        BottomSheetLayoutDepartureBinding bottomSheetBinding = BottomSheetLayoutDepartureBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(FindFlightActivity.this, android.R.layout.simple_list_item_1, getFilteredDepartures());
        bottomSheetBinding.lvDeparture.setAdapter(adapter);

        bottomSheetBinding.lvDeparture.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = getFilteredDepartures()[position];
            //binding.txtDeparture.setText(selectedItem);
            selectedDeparture = selectedItem;

            //String chuoi = "Hà Nội, Việt Nam (HAN)";
            String[] phanTach = selectedDeparture.split(" - ");

            String phan1 = phanTach[0]; // "HAN"
            binding.txtDepartureCode.setText(phan1);
            String phan2 = phanTach[1]; // "Hà Nội, Việt Nam"
            binding.txtDepartureCountry.setText(phan2);

            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private String[] getFilteredDepartures() {
        return filterArray(departures, selectedDeparture);
    }


    //############# Chon noi den ###################################################################
    private void showBottomSheetDestination() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FindFlightActivity.this);

        BottomSheetLayoutDepartureBinding bottomSheetBinding = BottomSheetLayoutDepartureBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(FindFlightActivity.this, android.R.layout.simple_list_item_1, getFilteredDestinations());
        bottomSheetBinding.lvDeparture.setAdapter(adapter);

        bottomSheetBinding.lvDeparture.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = getFilteredDestinations()[position];
            //binding.txtDeparture.setText(selectedItem);
            selectedDestination = selectedItem;

            //String chuoi = "Hà Nội, Việt Nam (HAN)";
            String[] phanTach = selectedDeparture.split(" - ");

            String phan1 = phanTach[0]; // "Hà Nội, Việt Nam"
            binding.txtDestinationValue.setText(phan1);
            String phan2 = phanTach[1]; // "HAN"
            binding.txtDestinationCountry.setText(phan2);

            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private String[] getFilteredDestinations() {
        return filterArray(destinations, selectedDeparture);
    }



    //############# Chon so hanh khach #############################################################
    private void showBottomSheetPassenger() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FindFlightActivity.this);

        BottomSheetLayoutDepartureBinding bottomSheetBinding = BottomSheetLayoutDepartureBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(FindFlightActivity.this, android.R.layout.simple_list_item_1, getFilteredPassenger());
        bottomSheetBinding.lvDeparture.setAdapter(adapter);

        bottomSheetBinding.lvDeparture.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = getFilteredPassenger()[position];
            selectedPassenger = selectedItem;

            String[] cutS = selectedPassenger.split(" - ");

            selectedQuantityPassenger = cutS[0]; // "01"
            binding.txtQuantityPassenger.setText(selectedQuantityPassenger);

            selectedTypePassenger = cutS[1]; // "Hành khách, 1 Người lớn"
            String[] cutK = selectedTypePassenger.split(", ");
            String txtFormater = cutK[0] + "\n" + cutK[1];
            binding.txtTypePassenger.setText(txtFormater);

            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private String[] getFilteredPassenger() {
        return filterArray(passengers, selectedPassenger);
    }

    //############# Chon ngay di ###################################################################
    private void showDatePickerDialog() {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    // Lưu giá trị ngày tháng năm đã chọn vào các biến
                    selectedDay = dayOfMonth;
                    selectedMonth = month1 + 1; // Tháng bắt đầu từ 0 nên cần +1
                    selectedYear = year1;
                    // Tạo lại đối tượng LocalDate từ day, month, year
                    LocalDate date = LocalDate.of(selectedYear, selectedMonth, selectedDay);

                    // Lấy DayOfWeek
                    DayOfWeek dayOfWeek = date.getDayOfWeek();
                    binding.txtDay.setText(String.valueOf(selectedDay));
                    String monthYearString = "Thg" + String.valueOf(selectedMonth) + "\n" + String.valueOf(selectedYear);
                    binding.txtMonthYear.setText(monthYearString);
                    binding.txtDayOfWeek.setText(dayOfWeek.toString());

                    // Ẩn TextView txtIconAddDate và txtAddText
                    findViewById(R.id.txtIconAddDate).setVisibility(View.GONE);
                    findViewById(R.id.txtAddText).setVisibility(View.GONE);
                },
                year,
                month,
                day
        );

        // Đặt ngày tối thiểu là ngày hiện tại
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    //############# Swap san bay ###################################################################
    private void swapAirport() {
        String tmpCode, tmpCountry;

        tmpCode = binding.txtDepartureCode.getText().toString();
        tmpCountry = binding.txtDepartureCountry.getText().toString();

        binding.txtDepartureCountry.setText(binding.txtDestinationCountry.getText().toString());
        binding.txtDepartureCode.setText(binding.txtDestinationValue.getText().toString());

        binding.txtDestinationCountry.setText(tmpCountry);
        binding.txtDestinationValue.setText(tmpCode);
    }

}