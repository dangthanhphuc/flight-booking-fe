package com.example.flightbooking;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flightbooking.databinding.ActivityLookupInformationBinding;
import com.example.flightbooking.databinding.ItemDateBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LookupInformationActivity extends AppCompatActivity {

    ActivityLookupInformationBinding binding;
    private List<Date> dateList;
    private LinearLayoutManager layoutManager;
    private DateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLookupInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        addEvents();
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
}
