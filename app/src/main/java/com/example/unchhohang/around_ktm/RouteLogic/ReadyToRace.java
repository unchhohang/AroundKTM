package com.example.unchhohang.around_ktm.RouteLogic;

import android.util.Log;

import com.example.unchhohang.around_ktm.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class ReadyToRace {
    //Initializing stops
    Stop s1 = new Stop("lagankhel_p", 27.667113, 85.322346);
    Stop s2 = new Stop("patan_hospital_p",27.668387, 85.321663);
    Stop s3 = new Stop("kumaripati_p",27.670801, 85.319957);
    Stop s4 = new Stop("manbhawan_p",27.672074, 85.315558);
    Stop s5 = new Stop("jawlakhel_p",27.672632, 85.313707);
    Stop s6 = new Stop("pulchowk_p",27.676070, 85.315721);
    Stop s7 = new Stop("harihar_bhawan_pulchowk_p",27.681087, 85.317402);
    Stop s8 = new Stop("jwagal_kupondol_p",27.685257, 85.318100);
    Stop s9 = new Stop("kandewatathan_kupondol_p",27.686757, 85.317133);
    Stop s10 = new Stop("thaptahali_p",27.687845, 85.316307);
    Stop s11 = new Stop("maitighar_p",27.694206, 85.319275);
    Stop s12 = new Stop("singhadurbar_p",27.694805, 85.320175);
    Stop s13 = new Stop("bhadrakali_mandir_p",27.699051, 85.317503);
    Stop s14 = new Stop("sahid_gate_p",27.699298, 85.317734);
    Stop s15 = new Stop("ratnapark_p",27.706788, 85.314730);
    Stop s16 = new Stop("bhirkutimandap_p",27.700914, 85.316609);
    Stop s17 = new Stop("bhadrakali_mandir_n",27.699298, 85.317734);
    Stop s18 = new Stop("maitighar_n",27.694088, 85.319397);
    Stop s19 = new Stop("thapathali_n",27.690458, 85.317760);
    Stop s20 = new Stop("kupondole_n",27.687641, 85.316763);
    Stop s21 = new Stop("jwagal_kupondol_n",27.685452, 85.318223);
    Stop s22 = new Stop("harihar_bhawan_pulchowk_n",27.681149, 85.317630);
    Stop s23 = new Stop("pulchow_n",27.676821, 85.316165);
    Stop s24 = new Stop("jawalkhel_n",27.672634, 85.313930);
    Stop s25 = new Stop("manbhawan_n",27.672153, 85.315956);
    Stop s26 = new Stop("kumaripati_n",27.670687, 85.320493);
    Stop s27 = new Stop("patan_hospital_n",27.669727, 85.321909);




    ArrayList<Stop> stops = new ArrayList<Stop>();
    private Stop start;
    private Stop end;

    public ReadyToRace(){

        s1.neighbour = new Edge[]{
                new Edge(s2, NearestDistance.calculationByDistance(s1.getLatLng(), s2.getLatLng()))
        };
        s2.neighbour = new Edge[]{
                new Edge(s3, NearestDistance.calculationByDistance(s2.getLatLng(), s3.getLatLng()))
        };
        s3.neighbour = new Edge[]{
                new Edge(s4, NearestDistance.calculationByDistance(s3.getLatLng(), s4.getLatLng()))
        };
        s4.neighbour = new Edge[]{
                new Edge(s5, NearestDistance.calculationByDistance(s4.getLatLng(), s5.getLatLng()))

        };
        s5.neighbour = new Edge[]{
                new Edge(s6, NearestDistance.calculationByDistance(s5.getLatLng(), s6.getLatLng()))

        };
        s6.neighbour = new Edge[]{
                new Edge(s7, NearestDistance.calculationByDistance(s6.getLatLng(), s7.getLatLng()))

        };
        s7.neighbour = new Edge[]{
                new Edge(s8, NearestDistance.calculationByDistance(s7.getLatLng(), s8.getLatLng()))

        };
        s8.neighbour = new Edge[]{
                new Edge(s9, NearestDistance.calculationByDistance(s8.getLatLng(), s9.getLatLng()))

        };
        s9.neighbour = new Edge[]{
                new Edge(s10, NearestDistance.calculationByDistance(s9.getLatLng(), s10.getLatLng())),

        };
        s10.neighbour = new Edge[]{
                new Edge(s11, NearestDistance.calculationByDistance(s10.getLatLng(), s11.getLatLng()))
        };
        s11.neighbour = new Edge[]{
                new Edge(s12, NearestDistance.calculationByDistance(s11.getLatLng(), s12.getLatLng()))
        };
        s12.neighbour = new Edge[]{
                new Edge(s13, NearestDistance.calculationByDistance(s12.getLatLng(), s13.getLatLng()))
        };
        s13.neighbour = new Edge[]{
                new Edge(s14, NearestDistance.calculationByDistance(s13.getLatLng(), s14.getLatLng()))
        };
        s14.neighbour = new Edge[]{
                new Edge(s15, NearestDistance.calculationByDistance(s14.getLatLng(), s15.getLatLng()))
        };
        s15.neighbour = new Edge[]{
                new Edge(s16, NearestDistance.calculationByDistance(s15.getLatLng(), s16.getLatLng()))
        };
        s16.neighbour = new Edge[]{
                new Edge(s17, NearestDistance.calculationByDistance(s16.getLatLng(), s17.getLatLng()))
        };
        s17.neighbour = new Edge[]{
                new Edge(s18, NearestDistance.calculationByDistance(s17.getLatLng(), s18.getLatLng()))
        };
        s18.neighbour = new Edge[]{
                new Edge(s19, NearestDistance.calculationByDistance(s18.getLatLng(), s19.getLatLng()))
        };
        s19.neighbour = new Edge[]{
                new Edge(s20, NearestDistance.calculationByDistance(s19.getLatLng(), s20.getLatLng()))
        };
        s20.neighbour = new Edge[]{
                new Edge(s21, NearestDistance.calculationByDistance(s20.getLatLng(), s21.getLatLng()))
        };
        s21.neighbour = new Edge[]{
                new Edge(s22, NearestDistance.calculationByDistance(s21.getLatLng(), s22.getLatLng()))
        };
        s22.neighbour = new Edge[]{
                new Edge(s23, NearestDistance.calculationByDistance(s22.getLatLng(), s23.getLatLng()))
        };
        s23.neighbour = new Edge[]{
                new Edge(s24, NearestDistance.calculationByDistance(s23.getLatLng(), s24.getLatLng()))
        };
        s24.neighbour = new Edge[]{
                new Edge(s25, NearestDistance.calculationByDistance(s24.getLatLng(), s25.getLatLng()))
        };
        s25.neighbour = new Edge[]{
                new Edge(s26, NearestDistance.calculationByDistance(s25.getLatLng(), s26.getLatLng()))
        };
        s26.neighbour = new Edge[]{
                new Edge(s27, NearestDistance.calculationByDistance(s26.getLatLng(), s27.getLatLng()))
        };
        s27.neighbour = new Edge[]{
                new Edge(s1, NearestDistance.calculationByDistance(s27.getLatLng(), s21.getLatLng()))
        };



        //Initializing Stops in array list
        stops.add(s1);
        stops.add(s2);
        stops.add(s3);
        stops.add(s4);
        stops.add(s5);
        stops.add(s6);
        stops.add(s7);
        stops.add(s8);
        stops.add(s9);
        stops.add(s10);
        stops.add(s11);
        stops.add(s12);
        stops.add(s13);
        stops.add(s14);
        stops.add(s15);
        stops.add(s16);
        stops.add(s17);
        stops.add(s18);
        stops.add(s19);
        stops.add(s20);
        stops.add(s21);
        stops.add(s22);
        stops.add(s23);
        stops.add(s24);
        stops.add(s25);
        stops.add(s26);
        stops.add(s27);




    }

    public List<Stop> findingPath(Stop source, Stop destination){
        start = InitiatingRace.gettingStart(source, stops);
        end = InitiatingRace.gettingEnd(destination, stops);

        Log.i("tag destination", "destination latlng:" + destination.getLatLng());


        //Implementing A* algorithm
        AStar.AStartRace(start, end);

        List<Stop> path = AStar.printPath(end);
        return path;
    }

    }

