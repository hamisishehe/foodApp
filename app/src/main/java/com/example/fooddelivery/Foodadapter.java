package com.example.fooddelivery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.fooddisplay,parent, false);
        viewHolder myview = new viewHolder(view);
        myview.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailActivity.class);
                FoodModel clickedItem = foodlist.get(viewType);
                intent.putExtra("name", clickedItem.getName());
                intent.putExtra("price", clickedItem.getPrice());
                intent.putExtra("image", clickedItem.getImageurl());
                intent.putExtra("id", clickedItem.getId());
                context.startActivity(intent);

            }
        });


        return  myview;

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
