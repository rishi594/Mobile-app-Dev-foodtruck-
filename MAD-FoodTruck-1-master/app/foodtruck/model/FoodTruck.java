package com.rmit.tejas.foodtruck.model;

public class FoodTruck extends AbstractTrackable implements Trackable {
    public FoodTruck(String id, String name, String type, String cusine, String url) {
        super(id, name, type, cusine, url);
    }
}
