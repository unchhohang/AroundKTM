package com.example.unchhohang.around_ktm.RouteLogic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.unchhohang.around_ktm.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;



public class ReadyToRace {
    ArrayList<Stop> stops = new ArrayList<Stop>();
    private Stop start;
    private Stop end;
    HashMap<String, Stop> dicStops;

    public ReadyToRace(HashMap<String, Stop> dicStops) {
        this.dicStops = dicStops;
        getNeighbourHoods();

    }

    public List<Stop> findingPath(Stop source, Stop destination) {
        start = InitiatingRace.gettingStart(source, dicStops);
        end = InitiatingRace.gettingEnd(destination, dicStops);
        AStar.AStartRace(start, end);
        List<Stop> path = AStar.printPath(end);
        return path;
    }

    public void getNeighbourHoods(){
        dicStops.get("s1").neighbour = new Edge[]{
                new Edge(dicStops.get("s2"), NearestDistance.calculationByDistance(dicStops.get("s1").getLatLng(), dicStops.get("s2").getLatLng()))
        };
        dicStops.get("s2").neighbour = new Edge[]{
                new Edge(dicStops.get("s3"), NearestDistance.calculationByDistance(dicStops.get("s2").getLatLng(), dicStops.get("s3").getLatLng())),
                new Edge(dicStops.get("s1"), NearestDistance.calculationByDistance(dicStops.get("s2").getLatLng(), dicStops.get("s1").getLatLng()))
        };
        dicStops.get("s3").neighbour = new Edge[]{
                new Edge(dicStops.get("s4"), NearestDistance.calculationByDistance(dicStops.get("s3").getLatLng(), dicStops.get("s4").getLatLng())),
                new Edge(dicStops.get("s2"), NearestDistance.calculationByDistance(dicStops.get("s3").getLatLng(), dicStops.get("s2").getLatLng()))
        };
        dicStops.get("s4").neighbour = new Edge[]{
                new Edge(dicStops.get("s3"), NearestDistance.calculationByDistance(dicStops.get("s4").getLatLng(), dicStops.get("s3").getLatLng())),
                new Edge(dicStops.get("s5"), NearestDistance.calculationByDistance(dicStops.get("s4").getLatLng(), dicStops.get("s5").getLatLng()))

        };

        dicStops.get("s5").neighbour = new Edge[]{
                new Edge(dicStops.get("s4"), NearestDistance.calculationByDistance(dicStops.get("s5").getLatLng(), dicStops.get("s4").getLatLng())),
                new Edge(dicStops.get("s6"), NearestDistance.calculationByDistance(dicStops.get("s5").getLatLng(), dicStops.get("s6").getLatLng()))

        };
        dicStops.get("s6").neighbour = new Edge[]{
                new Edge(dicStops.get("s5"), NearestDistance.calculationByDistance(dicStops.get("s6").getLatLng(), dicStops.get("s5").getLatLng())),
                new Edge(dicStops.get("s7"), NearestDistance.calculationByDistance(dicStops.get("s6").getLatLng(), dicStops.get("s7").getLatLng()))

        };
        dicStops.get("s7").neighbour = new Edge[]{
                new Edge(dicStops.get("s6"), NearestDistance.calculationByDistance(dicStops.get("s7").getLatLng(), dicStops.get("s6").getLatLng())),
                new Edge(dicStops.get("s8"), NearestDistance.calculationByDistance(dicStops.get("s7").getLatLng(), dicStops.get("s8").getLatLng()))

        };
        dicStops.get("s8").neighbour = new Edge[]{
                new Edge(dicStops.get("s7"), NearestDistance.calculationByDistance(dicStops.get("s8").getLatLng(), dicStops.get("s7").getLatLng())),
                new Edge(dicStops.get("s9"), NearestDistance.calculationByDistance(dicStops.get("s8").getLatLng(), dicStops.get("s9").getLatLng()))

        };
        dicStops.get("s9").neighbour = new Edge[]{
                new Edge(dicStops.get("s8"), NearestDistance.calculationByDistance(dicStops.get("s9").getLatLng(), dicStops.get("s8").getLatLng())),
                new Edge(dicStops.get("s10"), NearestDistance.calculationByDistance(dicStops.get("s9").getLatLng(), dicStops.get("s10").getLatLng()))

        };
        dicStops.get("s10").neighbour = new Edge[]{
                new Edge(dicStops.get("s9"), NearestDistance.calculationByDistance(dicStops.get("s10").getLatLng(), dicStops.get("s9").getLatLng())),
                new Edge(dicStops.get("s11"), NearestDistance.calculationByDistance(dicStops.get("s10").getLatLng(), dicStops.get("s11").getLatLng()))

        };
        dicStops.get("s11").neighbour = new Edge[]{
                new Edge(dicStops.get("s10"), NearestDistance.calculationByDistance(dicStops.get("s11").getLatLng(), dicStops.get("s10").getLatLng())),
                new Edge(dicStops.get("s12"), NearestDistance.calculationByDistance(dicStops.get("s11").getLatLng(), dicStops.get("s12").getLatLng()))

        };
        dicStops.get("s12").neighbour = new Edge[]{
                new Edge(dicStops.get("s11"), NearestDistance.calculationByDistance(dicStops.get("s12").getLatLng(), dicStops.get("s11").getLatLng())),
                new Edge(dicStops.get("s13"), NearestDistance.calculationByDistance(dicStops.get("s12").getLatLng(), dicStops.get("s13").getLatLng()))

        };
        dicStops.get("s13").neighbour = new Edge[]{
                new Edge(dicStops.get("s12"), NearestDistance.calculationByDistance(dicStops.get("s13").getLatLng(), dicStops.get("s12").getLatLng())),
                new Edge(dicStops.get("s14"), NearestDistance.calculationByDistance(dicStops.get("s13").getLatLng(), dicStops.get("s14").getLatLng()))

        };
        dicStops.get("s14").neighbour = new Edge[]{

                new Edge(dicStops.get("s15"), NearestDistance.calculationByDistance(dicStops.get("s14").getLatLng(), dicStops.get("s15").getLatLng()))

        };
        dicStops.get("s15").neighbour = new Edge[]{
                new Edge(dicStops.get("s16"), NearestDistance.calculationByDistance(dicStops.get("s15").getLatLng(), dicStops.get("s16").getLatLng())),

        };
        dicStops.get("s16").neighbour = new Edge[]{
                new Edge(dicStops.get("s13"), NearestDistance.calculationByDistance(dicStops.get("s16").getLatLng(), dicStops.get("s13").getLatLng()))
        };


    }

    public double getTotalDistance(List<Stop> paths){
        double totalDistance = 0.0;
        for (int i=1;i<paths.size();i++){
            totalDistance += NearestDistance.calculationByDistance(paths.get(i).getLatLng(),
                    paths.get(i-1).getLatLng());

        }
        return  totalDistance;
    }
}

