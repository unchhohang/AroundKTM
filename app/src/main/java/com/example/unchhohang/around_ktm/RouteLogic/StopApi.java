package com.example.unchhohang.around_ktm.RouteLogic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StopApi
{
    //String localhost = "192.168.1.64";
    String STOP_URL = "http://192.168.1.64/around_ktm_api/public/";

    @GET("allStops")
    Call<Stops> getStops();

    @GET("getCost")
    Call<List<Costs>> getCosts();
}
