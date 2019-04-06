package com.example.unchhohang.around_ktm.RouteLogic;

public class Edge {
    final double cost;
    final Stop target;

    public Edge(Stop name, Double cost){
        this.cost = cost;
        this.target = name;
    }
}
