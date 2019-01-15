package com.rmit.tejas.foodtruck.controller;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rmit.tejas.foodtruck.model.FoodTruck;
import com.rmit.tejas.foodtruck.MainActivity;
import com.rmit.tejas.foodtruck.R;
import com.rmit.tejas.foodtruck.model.TrackingService;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.rmit.tejas.foodtruck.model.TrackingService.context;

public class MyTrackingsAdapter extends RecyclerView.Adapter<MyTrackingsAdapter.MyViewHolder> {
    private static final String TAG = "MyTrackingsAdapter";
    private List<TrackingService.TrackingInfo> trackingInfoList;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private MainActivity mainActivity = new MainActivity();

    public MyTrackingsAdapter(List<TrackingService.TrackingInfo> trackingInfos){
        Log.d(TAG, "MyTrackingsAdapter: trackinginfos size: "+trackingInfos.size());
        trackingInfoList=trackingInfos;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_trackings_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String truck_name = "Truck Name";
        for (FoodTruck foodTruck: MainActivity.getFoodTrucks()){
            if (Integer.parseInt(foodTruck.getId())==trackingInfoList.get(i).trackableId)
                truck_name = foodTruck.getName();
        }
        myViewHolder.textView.setText(truck_name+"\nTrackable Id: "+trackingInfoList.get(i).trackableId);
        myViewHolder.textView1.setText(simpleDateFormat.format(trackingInfoList.get(i).date));
        myViewHolder.textView2.setText(trackingInfoList.get(i).latitude+", "+trackingInfoList.get(i).longitude+
        "\nfor "+trackingInfoList.get(i).stopTime+" mins");
        myViewHolder.textView3.setText(trackingInfoList.get(i).getTrackingName());

    }



    @Override
    public int getItemCount() {
        return trackingInfoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        Button button;



        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.foodtruck);
            textView1 = itemView.findViewById(R.id.dateTime);
            textView2 = itemView.findViewById(R.id.coordinates);
            textView3 = itemView.findViewById(R.id.tracking_name);
            button = itemView.findViewById(R.id.delete);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Deleted!",Toast.LENGTH_SHORT).show();
                    trackingInfoList.remove(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"Editable ID set to: "+getAdapterPosition());
                    mainActivity.setEditableId(getAdapterPosition());
                    Toast.makeText(context,"Editable Id set to "+getAdapterPosition(),Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}
