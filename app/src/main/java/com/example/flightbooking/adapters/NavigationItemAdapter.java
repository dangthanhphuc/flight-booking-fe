package com.example.flightbooking.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flightbooking.R;
import com.example.flightbooking.models.NavigationItem;

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
public class NavigationItemAdapter extends BaseAdapter {

    private Activity context;
    private int itemLayout; // Kiểu hiển thị (có thể tùy chình bằng file trong drawable)
    private List<NavigationItem> navigationItems; // Danh sách item dùng để điều hướng

    @Override
    public int getCount() {
        return navigationItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navigationItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    // Phương thức này sẽ được tự động gọi để lấy dữ liệu của từng item khi setAdapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Inflate file XML thành một đối tượng View
            convertView = inflater.inflate(itemLayout, null);

            viewHolder.imvIcon = convertView.findViewById(R.id.imvIcon);
            viewHolder.txtName = convertView.findViewById(R.id.txtName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Binding data
        NavigationItem item = navigationItems.get(position);
        viewHolder.txtName.setText(item.getName());
        viewHolder.imvIcon.setImageResource(item.getIcon());

        return convertView;
    }

    private static class ViewHolder {
        ImageView imvIcon;
        TextView txtName;
    }

}
