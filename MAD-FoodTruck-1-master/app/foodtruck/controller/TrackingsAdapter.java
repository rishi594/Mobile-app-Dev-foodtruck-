package com.rmit.tejas.foodtruck.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rmit.tejas.foodtruck.MainActivity;
import com.rmit.tejas.foodtruck.R;
import com.rmit.tejas.foodtruck.model.TrackingService;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.rmit.tejas.foodtruck.model.TrackingService.context;

public class TrackingsAdapter extends RecyclerView.Adapter<TrackingsAdapter.MyViewHolder> {
    private static final String TAG = "Trackings Adapter" ;
    List<TrackingService.TrackingInfo> trackingInfos;
    SimpleDateFormat sc = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    public TrackingsAdapter(List<TrackingService.TrackingInfo> trackingInfos){
        this.trackingInfos = trackingInfos;
        Log.d(TAG, "Size of tracking info: "+trackingInfos.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trackings_item,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText("ID="+trackingInfos.get(i).trackableId);
        myViewHolder.textView1.setText(sc.format(trackingInfos.get(i).date));
        myViewHolder.textView2.setText(trackingInfos.get(i).stopTime+" mins");
        myViewHolder.textView3.setText(trackingInfos.get(i).latitude+", "+trackingInfos.get(i).longitude);

    }


    @Override
    public int getItemCount() {
        return trackingInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView, textView1, textView2, textView3;
        public Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            textView1 = itemView.findViewById(R.id.time);
            textView2 = itemView.findViewById(R.id.stopTime);
            textView3 = itemView.findViewById(R.id.coordinates);
            button = itemView.findViewById(R.id.addTracking);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MainActivity.mytrackings.contains(trackingInfos.get(getPosition()))){
                        Toast.makeText(context,"You are already tracking it",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,"Tracking Added",Toast.LENGTH_SHORT).show();
                        MainActivity.mytrackings.add(trackingInfos.get(getPosition()));
                        Log.d(TAG,"My trackings size: "+MainActivity.mytrackings.size());
                    }
                }
            });
        }
    }
}
