package com.example.unchhohang.around_ktm.RouteLogic;

import java.util.ArrayList;

//Class for stop made to use for nodes
public class Stop {
    String name;
    double lat;
    double lng;
    ArrayList<Stop> neighbour;


    public Stop(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public void addNeighbour(Stop stop){
        neighbour.add(stop);
    }

    public int getNoNeighbour(){
        return neighbour.size();
    }

}
