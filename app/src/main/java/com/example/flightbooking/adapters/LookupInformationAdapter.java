package com.example.flightbooking.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flightbooking.models.LookupInformationItem;
import com.example.flightbooking.models.NavigationItem;
import com.example.flightbooking.network.responses.FlightResponse;

import java.util.List;

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
public class LookupInformationAdapter extends BaseAdapter {
    private Activity context;
    private int itemLayout; // Kiểu hiển thị (có thể tùy chình bằng file trong drawable)
    private List<FlightResponse> flightResponses;

    @Override
    public int getCount() {
        return flightResponses.size();
    }

    @Override
    public Object getItem(int position) {
        return flightResponses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    // Phương thức này sẽ được tự động gọi để lấy dữ liệu của từng item khi setAdapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LookupInformationAdapter.LookupInformationViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new LookupInformationAdapter.LookupInformationViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Inflate file XML thành một đối tượng View
            convertView = inflater.inflate(itemLayout, null);

            // Thay vào view holder tương ứng
//            viewHolder.imvIcon = convertView.findViewById(R.id.imvIcon);
//            viewHolder.txtName = convertView.findViewById(R.id.txtName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LookupInformationAdapter.LookupInformationViewHolder) convertView.getTag();
        }

        // Binding data
        FlightResponse item = flightResponses.get(position);
        // Thay vào view holder tương ứng
//        viewHolder.txtName.setText(item.getName());
//        viewHolder.imvIcon.setImageResource(item.getIcon());

        return convertView;
    }

    // Đổi lại đối tượng hiện thị trong view holder của LookupInformation
    private static class LookupInformationViewHolder {
        ImageView imvIcon;
        TextView txtName;
    }
}
