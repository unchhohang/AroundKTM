package com.example.unchhohang.around_ktm.RouteLogic;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class InitiatingRace {

    public static Stop gettingStart(Stop source, HashMap<String,Stop> dicStops){
        Stop startStop = null;
        Log.i("tag hashmap list", " " + dicStops.size());
        Double assignStart = 8000.0;

        for (Map.Entry<String, Stop> dicStop : dicStops.entrySet()) {
            Stop stop = dicStop.getValue();
            Double checkStart = NearestDistance.calculationByDistance(source.getLatLng(), stop.getLatLng());
            Log.i("tag distance", "The distance compared" + checkStart);
            if(checkStart <= assignStart){
                startStop = stop;
                assignStart = checkStart;

            }

        }

        return startStop;
    }

    public static Stop gettingEnd(Stop destination, HashMap<String,Stop> dicStops){
        Stop endStop = null;
        Double assignEnd = 8000.00;
        for (Map.Entry<String, Stop> dicStop : dicStops.entrySet()) {
            Stop stop = dicStop.getValue();
            Double checkEnd = NearestDistance.calculationByDistance(destination.getLatLng(), stop.getLatLng());
            if(checkEnd <= assignEnd){
                endStop = stop;
                assignEnd = checkEnd;
            }
            Log.i("tag destination", "destination details" + "start " + destination.getLatLng());

        }
        return endStop;
    }
}
