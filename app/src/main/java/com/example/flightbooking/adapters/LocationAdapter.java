package com.example.flightbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flightbooking.R;
import com.example.flightbooking.models.OnItemClickListener;
import com.example.flightbooking.network.responses.AirportResponse;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>{

    private List<AirportResponse> locations;
    private Context context;
    public OnItemClickListener listener;

    public LocationAdapter(Context context, List<AirportResponse> locations) {
        this.context = context;
        this.locations = locations;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        AirportResponse location = locations.get(position);

        holder.txtLocationName.setText(location.getCountry());
        holder.imvLocation.setImageResource(R.drawable.pexels_eberhard_grossgasteiger_1366919);
//        holder.textViewLocationPrice.setText(location.getPrice());

        // Sử dụng Glide để tải ảnh từ URL
//        Glide.with(context)
//                .load("http://192.168.1.150:8080/images/pexels-eberhard-grossgasteiger-1366919.jpg")
//                .into(holder.imvLocation);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        ImageView imvLocation;
        TextView txtLocationName;
//        TextView textViewLocationPrice;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            imvLocation = itemView.findViewById(R.id.imgLocation);
            txtLocationName = itemView.findViewById(R.id.txtLocationName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
//            textViewLocationPrice = itemView.findViewById(R.id.textViewLocationPrice);
        }
    }
}
