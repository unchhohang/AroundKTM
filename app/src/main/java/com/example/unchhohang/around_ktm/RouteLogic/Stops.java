package com.example.unchhohang.around_ktm.RouteLogic;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stops {

    @SerializedName("stops")
    @Expose
    private List<Stop> stops;

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }
}

