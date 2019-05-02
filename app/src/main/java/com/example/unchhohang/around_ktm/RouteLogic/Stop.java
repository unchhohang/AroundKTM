package com.example.unchhohang.around_ktm.RouteLogic;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

//Class for stop made to use for Stops
public class Stop {

    //Annotation for restful api according to retrofit
    @SerializedName("stop_id")
    @Expose
    String stopId;
    @SerializedName("stop_name")
    @Expose
    public final String name;
    @SerializedName("latitude")
    @Expose
    double lat;
    @SerializedName("longitude")
    @Expose
    double lng;
    @SerializedName("route")
    @Expose
    String routeId;
    double gScore;
    double hScore;
    double fScore;
    Stop parent;
    Edge[] neighbour;

    //constructor for stop latlng and name
    public Stop(String name, double lat, double lng){
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public Stop(String stopId, String name, double lat, double lng, String routeId) {
        this.stopId = stopId;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.routeId = routeId;
    }

    public String getStopId(){ return stopId; }
    public LatLng getLatLng(){
        return (new LatLng(lat, lng));
    }
    public Double getLatitude(){
        return lat;
    }
    public Double getLongitude(){
        return lng;
    }
    public int getNoNeighbour(){
        return neighbour.length;
    }







}
