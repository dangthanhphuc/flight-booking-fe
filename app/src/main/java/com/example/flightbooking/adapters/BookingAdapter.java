package com.example.flightbooking.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flightbooking.R;
import com.example.flightbooking.models.NavigationItem;
import com.example.flightbooking.network.responses.BookingResponse;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingAdapter extends BaseAdapter {
    private Activity context;
    private int itemLayout; // Kiểu hiển thị (có thể tùy chình bằng file trong drawable)
    private List<BookingResponse> bookings; // Danh sách item dùng để điều hướng

    @Override
    public int getCount() {
        return bookings.size();
    }

    @Override
    public Object getItem(int position) {
        return bookings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    // Phương thức này sẽ được tự động gọi để lấy dữ liệu của từng item khi setAdapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookingAdapter.BookingsViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new BookingAdapter.BookingsViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Inflate file XML thành một đối tượng View
            convertView = inflater.inflate(itemLayout, null);

            viewHolder.txtBookingCode = convertView.findViewById(R.id.txtBookingCode);
            viewHolder.txtStatus = convertView.findViewById(R.id.txtTicketStatus);
            viewHolder.txtDepartureAirportCode = convertView.findViewById(R.id.txtDepartureAirportCode);
            viewHolder.txtDepartureAirportName = convertView.findViewById(R.id.txtDepartureAirportName);
            viewHolder.txtArrivalAirportCode = convertView.findViewById(R.id.txtArrivalAirportCode);
            viewHolder.txtArrivalAirportName = convertView.findViewById(R.id.txtArrivalAirportName);
            viewHolder.txtDepartureTime = convertView.findViewById(R.id.txtDepartureTime);
            viewHolder.txtArrivalTime = convertView.findViewById(R.id.txtArrivalTime);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BookingAdapter.BookingsViewHolder) convertView.getTag();
        }

        // Binding data
        BookingResponse item = bookings.get(position);
        viewHolder.txtBookingCode.setText(item.getBookingCode());
        viewHolder.txtStatus.setText(item.getStatus());
        if(Objects.equals(item.getStatus(), "PENDING")) {
            viewHolder.txtStatus.setBackgroundColor(Color.parseColor("#FFA500"));
        } else if (Objects.equals(item.getStatus(), "PAID")) {
            viewHolder.txtStatus.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
        viewHolder.txtDepartureAirportCode.setText(item.getFlight().getFromAirport().getCountryCode());
        viewHolder.txtDepartureAirportName.setText(item.getFlight().getFromAirport().getCountry());
        viewHolder.txtArrivalAirportCode.setText(item.getFlight().getToAirport().getCountryCode());
        viewHolder.txtArrivalAirportName.setText(item.getFlight().getToAirport().getCountry());
        viewHolder.txtDepartureTime.setText(item.getFlight().getDepartureDateTime().toString());
        viewHolder.txtArrivalTime.setText(item.getFlight().getArrivalDateTime().toString());

        return convertView;
    }

    private static class BookingsViewHolder {
        ImageView imvIcon;
        TextView txtBookingCode, txtStatus, txtDepartureAirportCode, txtDepartureAirportName, txtArrivalAirportCode, txtArrivalAirportName, txtDepartureTime, txtArrivalTime;
    }
}
