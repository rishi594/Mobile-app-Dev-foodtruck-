package com.rmit.tejas.foodtruck;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rmit.tejas.foodtruck.controller.FoodTruckListAdapter;
import com.rmit.tejas.foodtruck.controller.MyTrackingsAdapter;
import com.rmit.tejas.foodtruck.controller.TrackingsAdapter;
import com.rmit.tejas.foodtruck.model.FoodTruck;
import com.rmit.tejas.foodtruck.model.FoodTruckParser;
import com.rmit.tejas.foodtruck.model.Tracking;
import com.rmit.tejas.foodtruck.model.TrackingService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.rmit.tejas.foodtruck.model.Tracking.createTracking;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity ";
    private static int id;
    static int hours, mins, Y,M,D;
    Menu menu;

    public static int getId() {
        return id;
    } // getter

    public static void setId(int id) {
        MainActivity.id = id;
    } // setter

    public static List<FoodTruck> getFoodTrucks() {
        return foodTrucks;
    } // list of foodtruck objs

    public static void setFoodTrucks(List<FoodTruck> foodTrucks) {
        MainActivity.foodTrucks = foodTrucks;
    }

    public void setEditableId(int editableId) {
        this.editableId = editableId;
        Log.d(TAG,"Editable id "+this.editableId);
    }

    int editableId=0;
    private static List<FoodTruck> foodTrucks = new ArrayList<>();
    RecyclerView recyclerView, trackingRecyclerView, myTrackingsRecyclerView;
    RecyclerView.LayoutManager layoutManager, layoutManager2, layoutManager3;
    FoodTruckListAdapter adapter;
    TrackingsAdapter trackingsAdapter;
    MyTrackingsAdapter myTrackingsAdapter;
    Button trackMe, viewTrackings, routeInfoButton;
    TextView textView2;
    static List<TrackingService.TrackingInfo> trackingInfos;
    static List<TrackingService.TrackingInfo> trackableList;
    public static List<TrackingService.TrackingInfo> mytrackings;
    Tracking tracking;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sc;
    TrackingService trackingService;
    static Calendar c;
    List<String> filteredType;
    CheckBox c1,c2,c3,c4,c5,c6,c7,c8;
    List<CheckBox> checkBoxes;
    TextView routeInfo;
    EditText editText;

    public MainActivity() {
        sc = new SimpleDateFormat("dd/MM/yyyy");
    }

    public int getMins() {
        return mins;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  // homepage navigation basic switch cases.
        switch (item.getItemId()){
            case R.id.view_trackings:
                setContentView(R.layout.my_trackings_list);
                android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
                setSupportActionBar(myToolbar);

                myTrackingsRecyclerView = findViewById(R.id.trackingRecycler);
                myTrackingsAdapter = new MyTrackingsAdapter(mytrackings);
                myTrackingsRecyclerView.setAdapter(myTrackingsAdapter);
                layoutManager3 = new LinearLayoutManager(this);
                myTrackingsRecyclerView.setLayoutManager(layoutManager3);
                break;
            case R.id.home_page:
                setContentView(R.layout.food_truck_list);
                recyclerView = findViewById(R.id.recycler);
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new FoodTruckListAdapter(getFoodTrucks());
                recyclerView.setAdapter(adapter);
                trackableList.removeAll(trackableList);


                myToolbar = findViewById(R.id.my_toolbar);
                setSupportActionBar(myToolbar);
                break;

            case R.id.edit_trackings:
                setContentView(R.layout.edit_tracking);
                myToolbar = findViewById(R.id.my_toolbar);
                setSupportActionBar(myToolbar);

                break;

            case R.id.filter:
                setContentView(R.layout.filter_screen);
                myToolbar = findViewById(R.id.my_toolbar);
                setSupportActionBar(myToolbar);
                c1 = findViewById(R.id.checkAsian);
                c2 = findViewById(R.id.checkItalian);
                c3 = findViewById(R.id.checkAfrican);
                c4 = findViewById(R.id.checkArgentinian);
                c5 = findViewById(R.id.checkThai);
                c6 = findViewById(R.id.checkDesert);
                c7 = findViewById(R.id.checkVietnamese);
                c8 = findViewById(R.id.checkWestern);

                checkBoxes = Arrays.asList(c1,c2,c3,c4,c5,c6,c7,c8);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateTrackingName(View view){ // fetch adnd update tracking name.
        editText = findViewById(R.id.current_tracking_name);
        mytrackings.get(editableId).setTrackingName(String.valueOf(editText.getText()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_truck_list);


        filteredType = new ArrayList<>();
        FoodTruckParser foodTruckParser = new FoodTruckParser();
        setFoodTrucks(foodTruckParser.getTrackableList(this));


        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FoodTruckListAdapter(getFoodTrucks());
        recyclerView.setAdapter(adapter);
        trackMe = findViewById(R.id.track);
        trackingInfos = new ArrayList<>();
        trackableList = new ArrayList<>();
        trackingService = new TrackingService();
        mytrackings = new ArrayList<>();

        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        routeInfoButton = findViewById(R.id.displayRouteInfo);

    }


    public void routeInfoScreen(View view){
        setContentView(R.layout.route_information);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        routeInfo = findViewById(R.id.displayRouteInfo);
//        Log.d(TAG, String.valueOf(t.getTrackingList().get(0).trackableId));
        switch (getId()){
            case 1:
                routeInfo.setText("Festival Hall at 01:10 PM for 5 mins\nFlagstaff Gardens at 01:30 PM for 10 mins");
                break;
            case 2:
                routeInfo.setText("Sea World at 01:10 PM for 5 mins\nState Library at 01:35 PM for 10 mins");
                break;
            case 3:
                routeInfo.setText("Sea World at 01:30 PM for 10 mins\nFestival Hall at 01:55 PM for 5 mins");
                break;

        }
    }

    public void initTrackingScreen(int position){
        Log.d(TAG,"On Click: "+position);
        setId(position+1);
        Log.d(TAG,"Id set to: "+ getId());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public void trackingScreen(View view){

        setContentView(R.layout.tracking_home);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        textView2 = findViewById(R.id.displayScreen);
        Log.d(TAG,"Id in tracking screen: "+ getId());
        try {
            textView2.setText("Tracking Set to "+ getFoodTrucks().get(getId() - 1).getName() +"\n"+ getFoodTrucks().get(getId() - 1).getCusine());
        } catch (Exception e) {
            Toast.makeText(this,"Please select a trackable",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.food_truck_list);
            recyclerView = findViewById(R.id.recycler);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new FoodTruckListAdapter(getFoodTrucks());
            recyclerView.setAdapter(adapter);
        }
    }

    public void displayTrackings(View view) {
        try {
            tracking = new Tracking(sc.parse(+D+"/"+M+"/"+Y),hours,mins);
        } catch (ParseException e) {
            Log.d(TAG,"Date parse exception");
        }
        createTracking(this);
        trackingInfos = tracking.getMatched();

        if (trackingInfos.size()==0)
            Toast.makeText(this,"No trucks available",Toast.LENGTH_SHORT).show();
        else
        {
            setContentView(R.layout.trackings_list);
            android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
            setSupportActionBar(myToolbar);
            trackingRecyclerView = findViewById(R.id.trackingRecycler);
            layoutManager2 = new LinearLayoutManager(this);


            for (TrackingService.TrackingInfo t: trackingInfos){
                if (t.trackableId== getId())
                    trackableList.add(t);
            }

            Log.d(TAG,"Tracking infos size: "+trackingInfos.size());
            trackingRecyclerView.setLayoutManager(layoutManager2);
            trackingsAdapter = new TrackingsAdapter(trackableList);
            trackingRecyclerView.setAdapter(trackingsAdapter);
            layoutManager2 = new LinearLayoutManager(this);
        }
    }

    public void applyFilters(View view){
        filteredType.removeAll(filteredType);

        List<FoodTruck> filteredList = new ArrayList<>();
        filteredList.removeAll(filteredList);
        setContentView(R.layout.food_truck_list);

        FoodTruckParser foodTruckParser = new FoodTruckParser();
        setFoodTrucks(foodTruckParser.getTrackableList(this));
        for (CheckBox c:checkBoxes){
            if (c.isChecked()){
                filteredType.add(String.valueOf(c.getText()));
            }
        }
        List<String> filteredIDs = convertTypeToID(filteredType);
        Log.d(TAG,"Filter Type: "+filteredType);
        for (FoodTruck f: getFoodTrucks()){
            Log.d(TAG,"Food truck: "+ f.getUrl());
            for (String s:filteredIDs){
                Log.d(TAG,"Filter Type: "+s);
                if (f.getId().contains(s))
                    filteredList.add(f);
            }
        }


        Log.d(TAG,"Filtered List: "+filteredList);
        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FoodTruckListAdapter(filteredList);
        recyclerView.setAdapter(adapter);
        trackMe = findViewById(R.id.track);
        trackingInfos = new ArrayList<>();
        trackableList = new ArrayList<>();
        trackingService = new TrackingService();
        mytrackings = new ArrayList<>();



        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public List<String> convertTypeToID(List<String> types){
        List<String> ids = new ArrayList<>();

        for (String t:types){
            if (t.equalsIgnoreCase("Asian")) {
                ids.add("1");
                ids.add("3");
            }
            if (t.equalsIgnoreCase("Italian")) ids.add("2");
            if (t.equalsIgnoreCase("African")) ids.add("4");
            if (t.equalsIgnoreCase("Argentinian")) ids.add("5");
            if (t.equalsIgnoreCase("Thai")) ids.add("6");
            if (t.equalsIgnoreCase("Vietnamese")) ids.add("8");
            if (t.equalsIgnoreCase("Western")) ids.add("10");
            if (t.equalsIgnoreCase("Dessert")) {
                ids.add("9");
                ids.add("7");
            }
        }

        return ids;
    }
    DialogFragment timeFragment, dateFragment;

    public void showTimePickerDialog(View view){
        timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(),"timePicker");

    }

    public void showDatePickerDialog(View v) {
        dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateTime(View view) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(),"timePicker");
        try {
            String formattedHours, formattedMins;
            if (hours>12)
                hours-=12;
            if (hours>9)
                formattedHours= ""+hours;
            else formattedHours= "0"+hours;
            if (mins>9)
                formattedMins= ""+mins;
            else formattedMins= "0"+mins;
            hours+=12;
            String searchDate = "05/07/2018"+" "+formattedHours+":"+formattedMins+":00 PM";
            Log.d(TAG, "updateTime: "+searchDate);
            createTracking(this);
            if ((new Tracking(sc.parse("05/07/2018"),hours,mins).getMatched().size()==0)) {
                Toast.makeText(this, "No food trucks in this slot", Toast.LENGTH_SHORT);
                Log.d(TAG, "updateTime: Unable to add as no trucks in the slot");
            }
            else {
                mytrackings.get(editableId).date = simpleDateFormat.parse(searchDate);
                Toast.makeText(this,"Time updated!",Toast.LENGTH_SHORT);
                Log.d(TAG, "updateTime: Time updated!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Y=year;
            M=month;
            D=day;
        }

    }
    public int getHour() {
        return hours;
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            hours = i;
            mins = i1;
        }

        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState){

            return new TimePickerDialog(getActivity(),this, hours, mins, android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

    }


}
