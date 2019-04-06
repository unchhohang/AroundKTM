package com.example.unchhohang.around_ktm.RouteLogic;

import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar {
    public static void AStartRace(Stop start, Stop end){
        Set<Stop> explored = new HashSet<Stop>();
        PriorityQueue<Stop> queue = new PriorityQueue<Stop>(100,
                new Comparator<Stop>(){
                    //override compare method
                    public int compare(Stop i, Stop j){
                        if(i.fScore > j.fScore){
                            return 1;
                        }

                        else if (i.fScore < j.fScore){
                            return -1;
                        }

                        else{
                            return 0;
                        }
                    }

                }
        );

        //cost from start
        start.gScore= 0;

        queue.add(start);

        boolean found = false;

        while((!queue.isEmpty())&&(!found)){

            //the Stop in having the lowest f_score value
            Stop current = queue.poll();

            explored.add(current);
            Log.i("Tag A*", "This is current of A* : " + current.name);
            //goal found
            if(current.name.equals(end.name)){
                found = true;
            }

            Log.i("tag neighbour :" , "neighbour :" + current.neighbour);

            //check every child of current Stop
            for(Edge e : current.neighbour){
                Stop child = e.target;
                double cost = e.cost;
                double temp_g_scores = current.gScore + cost;
                double temp_f_scores = temp_g_scores + child.hScore;


                                /*if child Stop has been evaluated and
                                the newer f_score is higher, skip*/

                if((explored.contains(child)) &&
                        (temp_f_scores >= child.fScore)){
                    continue;
                }

                                /*else if child Stop is not in queue or
                                newer f_score is lower*/

                else if((!queue.contains(child)) ||
                        (temp_f_scores < child.fScore)){

                    child.parent = current;
                    child.gScore = temp_g_scores;
                    child.fScore = temp_f_scores;

                    if(queue.contains(child)){
                        queue.remove(child);
                    }

                    queue.add(child);

                }

            }

        }

    }

    public static List<Stop> printPath(Stop target){
        List<Stop> path = new ArrayList<Stop>();

        for(Stop stop = target; stop!=null; stop = stop.parent){
            path.add(stop);
            Log.i("tag paths", "trying to get paths" + stop);
        }

        Collections.reverse(path);

        return path;
    }

    }

