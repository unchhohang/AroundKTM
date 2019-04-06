package com.example.unchhohang.around_ktm.RouteLogic;

import android.util.Log;

import java.util.ArrayList;

public class InitiatingRace {

    public static Stop gettingStart(Stop source, ArrayList<Stop> stops ){
        Stop startStop = null;

        Double assignStart = 8000.0;
        for (Stop stop : stops) {
            Double checkStart = NearestDistance.calculationByDistance(source.getLatLng(), stop.getLatLng());
            Log.i("tag distance", "The distance compared" + checkStart);
            if(checkStart <= assignStart){
                startStop = stop;
                assignStart = checkStart;

            }

        }
        Log.i("tag Initializing start", "I am the start loop " + "start " + startStop.name);
        return startStop;
    }

    public static Stop gettingEnd(Stop destination, ArrayList<Stop> stops ){
        Stop endStop = null;
        Double assignEnd = 8000.00;
        for (Stop stop : stops) {
            Double checkEnd = NearestDistance.calculationByDistance(destination.getLatLng(), stop.getLatLng());
            if(checkEnd <= assignEnd){
                endStop = stop;
                assignEnd = checkEnd;
            }
            Log.i("tag destination", "destination details" + "start " + destination.getLatLng());

        }
        Log.i("tag Initializing start", "I am the end loop nearest " + "end " + endStop.name);

        return endStop;
    }
}
