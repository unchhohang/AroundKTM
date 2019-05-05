package com.example.unchhohang.around_ktm.RouteLogic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Costs {
    @SerializedName("cost_id")
    @Expose
    public String costId;
    @SerializedName("vehicle_no")
    @Expose
    public String vehicleNo;
    @SerializedName("type")
    @Expose
    public int type;
    @SerializedName("stop_init")
    @Expose
    public String stopInit;
    @SerializedName("stop_later")
    @Expose
    public String stopLater;
    @SerializedName("cost")
    @Expose
    public double cost;


}
