package com.example.fooddelivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class Foodadapter extends RecyclerView.Adapter<Foodadapter.viewHolder>{

    Context context;
    ArrayList<FoodModel> foodlist;


    public Foodadapter(Context context, ArrayList<FoodModel> foodlist) {
        this.context = context;
        this.foodlist = foodlist;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.fooddisplay, null));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        FoodModel foods = foodlist.get(position);

        holder.name.setText(String.valueOf(foods.getName()));
        holder.price.setText(String.valueOf(foods.getPrice())  +"  "+ "Tsh");

        Glide.with(context).load("https://testhamisi.000webhostapp.com/images/"+ foods.getImageurl())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name, price;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imagedisplay);
            this.name = itemView.findViewById(R.id.namedisplay);
            this.price = itemView.findViewById(R.id.pricedisplay);
        }
    }
}
