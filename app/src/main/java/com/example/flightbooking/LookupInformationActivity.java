package com.example.flightbooking;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightbooking.adapters.LookupInformationAdapter;
import com.example.flightbooking.databinding.ActivityLookupInformationBinding;
import com.example.flightbooking.databinding.ItemDateBinding;
import com.example.flightbooking.network.HttpRequest;

import com.example.flightbooking.network.api.FlightService;
import com.example.flightbooking.network.models.Response;
import com.example.flightbooking.network.responses.FlightResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class LookupInformationActivity extends AppCompatActivity {

    ActivityLookupInformationBinding binding;
    private List<Date> dateList;
    private LinearLayoutManager layoutManager;
    private DateAdapter adapter;

    private FlightService flightService;
    private List<FlightResponse> flightResponses;

    private List<FlightResponse> filterFlightResponses;

    private LookupInformationAdapter lookupInformationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLookupInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        flightService = HttpRequest.createService(FlightService.class, this);

        dateList = new ArrayList<>();
        populateDateList();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerView);

        adapter = new DateAdapter(dateList);
        binding.recyclerView.setAdapter(adapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        binding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                // Add spacing to the right of each item except the last one
                int itemPosition = parent.getChildAdapterPosition(view);
                int itemCount = state.getItemCount();
                if (itemPosition < itemCount - 1) {
                    outRect.right = spacingInPixels;
                }
            }
        });

        getFlights();

        Intent intent = getIntent();
        String departure =  intent.getStringExtra("departure");
        String destination = intent.getStringExtra("destination");
        String departureTime = intent.getStringExtra("departureTime");

        binding.txtDeparture.setText(departure);
        binding.txtDestination.setText(destination);
        binding.txtDepartureTime.setText(departureTime);

        //B1 : Lọc lấy flightResponses theo yêu cầu bỏ vào filterFlightResponses
        // B2 : đưa dữ liệu đã lọc vào adapters
        // //                    lookupInformationAdapter = new LookupInformationAdapter(LookupInformationActivity.this, // Tự tạo giao diện hiển thị cho item , filterFlightResponses);

        // Khi chuyển ngày thì lặp lại b1 và b2

        addEvents();
        getFlights(); // Lấy chuyến bay từ server
        updateNavigationButtons();
    }

    private void addEvents() {
        binding.imvExit.setOnClickListener(view -> {
            Intent intent = new Intent(LookupInformationActivity.this, FlightSearchActivity.class);
            startActivity(intent);
        });

        binding.imvLeft.setOnClickListener(view -> scrollToPreviousItems());
        binding.imvRight.setOnClickListener(view -> scrollToNextItems());
        binding.imvLeft.setOnTouchListener((v, event) -> handleScrollOnTouch(event, -3));
        binding.imvRight.setOnTouchListener((v, event) -> handleScrollOnTouch(event, 3));
    }

    private void populateDateList() {
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 30; i++) { // Show 30 days
            dateList.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private void scrollToPreviousItems() {
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int targetPosition = Math.max(firstVisibleItemPosition - 3, 0);
        binding.recyclerView.smoothScrollToPosition(targetPosition);
        updateNavigationButtons();
    }

    private void scrollToNextItems() {
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int targetPosition = Math.min(lastVisibleItemPosition + 3, dateList.size() - 1);
        binding.recyclerView.smoothScrollToPosition(targetPosition);
        updateNavigationButtons();
    }

    private boolean handleScrollOnTouch(MotionEvent event, int scrollBy) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (scrollBy < 0) {
                scrollToPreviousItems();
            } else {
                scrollToNextItems();
            }
            return true;
        }
        return false;
    }

    private void updateNavigationButtons() {
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        binding.imvLeft.setEnabled(firstVisibleItemPosition > 0);
        binding.imvRight.setEnabled(lastVisibleItemPosition < dateList.size() - 1);
    }

    private class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
        private final List<Date> dateList;
        private int selectedPosition = RecyclerView.NO_POSITION; // No position selected by default

        public DateAdapter(List<Date> dateList) {
            this.dateList = dateList;
        }

        @Override
        public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemDateBinding binding = ItemDateBinding.inflate(getLayoutInflater(), parent, false);
            return new DateViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(DateViewHolder holder, int position) {
            Date date = dateList.get(position);
            holder.binding.dayOfWeek.setText(getDayOfWeek(date));
            holder.binding.date.setText(getDate(date));

            // Update the text color based on the selected position
            if (selectedPosition == position) {
                holder.binding.dayOfWeek.setTextColor(getResources().getColor(R.color.selectedTextColor)); // Selected color
                holder.binding.date.setTextColor(getResources().getColor(R.color.selectedTextColor)); // Selected color
            } else {
                holder.binding.dayOfWeek.setTextColor(getResources().getColor(android.R.color.black)); // Default color
                holder.binding.date.setTextColor(getResources().getColor(android.R.color.black)); // Default color
            }

            // Handle item click
            holder.binding.getRoot().setOnClickListener(view -> {
                int previousPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(previousPosition); // Refresh the previous selected item
                notifyItemChanged(selectedPosition); // Refresh the current selected item
            });
        }

        @Override
        public int getItemCount() {
            return dateList.size();
        }

        public class DateViewHolder extends RecyclerView.ViewHolder {
            ItemDateBinding binding;

            public DateViewHolder(ItemDateBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        private String getDayOfWeek(Date date) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("vi"));
            return dayFormat.format(date);
        }

        private String getDate(Date date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi"));
            return dateFormat.format(date);
        }
    }

    private void getFlights() {

        // Khởi tạo adapter
        lookupInformationAdapter = new LookupInformationAdapter(LookupInformationActivity.this, R.layout.lookup_item, filterFlightResponses);

        Call<Response<List<FlightResponse>>> call = flightService.getFlights();
        call.enqueue(new Callback<Response<List<FlightResponse>>>() {

            @Override
            public void onResponse(@NonNull Call<Response<List<FlightResponse>>> call, @NonNull retrofit2.Response<Response<List<FlightResponse>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    flightResponses = response.body().getData();
                } else {
                    Log.e("Error", "Request failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response<List<FlightResponse>>> call, @NonNull Throwable throwable) {
                call.timeout();
            }
        });
    }
}
