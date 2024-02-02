package com.example.fooddelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrdersAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<OrdersModel> orderslist;

    public OrdersAdapter(Context context, ArrayList<OrdersModel> orderslist) {
        this.context = context;
        this.orderslist = orderslist;
    }

    @Override
    public int getCount() {
        return orderslist.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrdersModel m = orderslist.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_customlist, parent, false);
        }

        TextView order_details = convertView.findViewById(R.id.order_details);
        TextView order_status = convertView.findViewById(R.id.order_status);
        ImageView order_image = convertView.findViewById(R.id.order_image);

        order_details.setText(m.getName()+ " "+ "("+String.valueOf(m.getQuantity())+ ")   "   + String.valueOf(m.getTotalprice()) + " "+"TSH");

        if(m.getStatus() == 0){
            order_status.setText("Processing");
        }
        else if(m.getStatus() == 1){
            order_status.setText("Delivered");
        }


        Glide.with(context).load("https://testhamisi.000webhostapp.com/images/" + m.getImage())
                .into(order_image);

        return convertView;
    }
}
