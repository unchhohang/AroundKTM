package com.example.unchhohang.around_ktm.RouteLogic;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

//Class for stop made to use for Stops
public class Stop {
    public final String name;
    double lat;
    double lng;
    double gScore;
    double hScore;
    double fScore;
    Stop parent;
    Edge[] neighbour;

    //constructor for stop latlng and name
    public Stop(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;

    }

    public LatLng getLatLng(){
        return (new LatLng(lat, lng));
    }



    public int getNoNeighbour(){
        return neighbour.length;
    }







}
