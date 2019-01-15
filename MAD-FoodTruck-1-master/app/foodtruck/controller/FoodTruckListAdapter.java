package com.rmit.tejas.foodtruck.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rmit.tejas.foodtruck.model.FoodTruck;
import com.rmit.tejas.foodtruck.MainActivity;
import com.rmit.tejas.foodtruck.R;

import java.util.List;

public class FoodTruckListAdapter extends RecyclerView.Adapter<FoodTruckListAdapter.MyViewHolder> {

    private List<FoodTruck> foodTrucks;
    MainActivity mainActivity ;
    String TAG = "Adapter";


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_truck_item,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder customViewHolder, int i) {
        customViewHolder.textView.setText(foodTrucks.get(i).getName());
        customViewHolder.textView1.setText(foodTrucks.get(i).getType());
        customViewHolder.textView2.setText(foodTrucks.get(i).getCusine());
        customViewHolder.textView3.setText(foodTrucks.get(i).getUrl());

    }

    @Override
    public int getItemCount() {
        return foodTrucks.size();
    }

    public FoodTruckListAdapter(List<FoodTruck> foodTrucks){
        this.foodTrucks = foodTrucks;
        mainActivity = new MainActivity();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            textView1 = itemView.findViewById(R.id.type);
            textView2 = itemView.findViewById(R.id.cusine);
            textView3 = itemView.findViewById(R.id.url);

            Linkify.addLinks(textView3, Linkify.WEB_URLS);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.setId(getPosition());
                    Log.d(TAG, String.valueOf(getPosition()));
                    mainActivity.initTrackingScreen(getPosition());
                }
            });

        }

    }
}
