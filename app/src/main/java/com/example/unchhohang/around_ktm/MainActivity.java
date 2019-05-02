package com.example.unchhohang.around_ktm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.unchhohang.around_ktm.RouteLogic.InitiatingRace;
import com.example.unchhohang.around_ktm.RouteLogic.ReadyToRace;
import com.example.unchhohang.around_ktm.RouteLogic.Stop;
import com.example.unchhohang.around_ktm.RouteLogic.StopApi;
import com.example.unchhohang.around_ktm.RouteLogic.Stops;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.
        google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private DrawerLayout drawerLayout;

    private static final String TAG = "Main activity";
    GoogleMap m_map;
    boolean mapReady = false;


    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION= 1;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //variable for source position
    Task<Location> locationResult;
    public Location mLastKnownLocation;
    public Location lastLocation;
    public static final int DEFAULT_ZOOM = 15;

    //variable for marker destination posistion
    private Marker destination;
    private LatLng positionOfDestination;

    Stop source;
    Stop desti;
    Stop start;
    Stop end;

    Double sourLat;
    Double sourLog;
    //for path
    List<Stop> paths;

    //for direction
    private static final int overview = 0;

    //for hashmap of stop
    HashMap<String, Stop> dicStops = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initiating the default destination marker position
        positionOfDestination = new LatLng(27.660372, 85.322682);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //problem
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        System.out.println("Thsis is testing" + actionbar);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Construct a GeoDataClient.
        //mGeoDataClient = Places.getGeoDataClient(this, null);
        // Construct a PlaceDetectionClient.
        //mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Button btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        Button btnSatellite = (Button) findViewById(R.id.btnSatellite);
        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        Button btnHybrid = (Button) findViewById(R.id.btnHybrid);
        btnHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Drawer layout switching
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.nav_hotline:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.layout_main, new fragment_hotline()).commit();
                                break;

                            case R.id.nav_add_route:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.layout_main, new fragment_addRoute()).commit();
                                break;

                            case R.id.nav_feedback:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.layout_main, new fragment_feedback()).commit();
                                break;

                            case R.id.nav_help:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.layout_main, new fragment_help()).commit();
                                break;
//                            default:
//                                MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
                        }
                        return true;
                    }
                }
        );


        //Running Retrofit
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.64/around_ktm_api/public/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();
        StopApi stopApi = retrofit.create(StopApi.class);
        Call<Stops> call = stopApi.getStops();
        call.enqueue(new Callback<Stops>() {
            @Override
            public void onResponse(Call<Stops> call, Response<Stops> response) {
                List<Stop> stops = response.body().getStops();

                for(Stop stop : stops){

                    dicStops.put(stop.getStopId(), stop);
                    Log.i("Retrofit","" + dicStops);
                }

            }

            @Override
            public void onFailure(Call<Stops> call, Throwable t) {
                Log.i("Retrofit", " " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (m_map == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                m_map.setMyLocationEnabled(true);
                m_map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                m_map.setMyLocationEnabled(false);
                m_map.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {

        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        Log.i("tag getDevice","I am the get Device button");
        try {
            if (mLocationPermissionGranted) {

                locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.i("tag source", "I am first location");
                        if (task.isSuccessful()) {
                            Log.i("tag source", "I am first location vitra xire");
                            mLastKnownLocation = (Location) task.getResult();
                            if(mLastKnownLocation != null){
                                Log.i("tag source", "I am first location ajhai vitra xire");

                                // Set the map's camera position to the current location of the device.

                                m_map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                                source = new Stop("Source", mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                                desti = new Stop("destination", getdestinationLocation().latitude, getdestinationLocation().longitude);

                                ReadyToRace readyToRace = new ReadyToRace(dicStops);
                                paths = readyToRace.findingPath(source, desti);
                                for(int i = 0; i < paths.size(); i++){
                                    Log.i("tag path","I am the path " + paths.get(i).name);
                                    m_map.addMarker(new MarkerOptions()
                                            .position(paths.get(i).getLatLng())
                                            .title(paths.get(i).name)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
                                }
                                //for getting the first stop in direction api
                                Stop firstStop = paths.get(0);
                                //get direction from source to start
                                String sourceAddress =  getCompleteAddressString(source.getLatitude(), source.getLongitude());
                                String firstStopAddress = getCompleteAddressString(firstStop.getLatitude(), firstStop.getLongitude());

                                DirectionsResult results = getDirectionsDetails(sourceAddress, firstStopAddress);
                                Log.i("tag directions", "Hope got direction");

//                                if (results != null) {
//                                    addPolyline(results, m_map);
////                                    positionCamera(results.routes[overview], m_map);
//                                Log.i("tag polylines", "going into polyline section");
//                                }
//
//                                Log.i("tag address", "source address :" + sourceAddress);
//                                Log.i("tag address", "destination address :" + firstStopAddress);
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            //m_map.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            m_map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapReady = true;
        m_map = map;
        m_map.setOnInfoWindowClickListener(this);

        // Do other setup activities here too, as described elsewhere in this tutorial.
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();


//        //Add marker on the map and add data object
//        for (Map.Entry<String, String> entry : stops.entrySet()) {
//            System.out.println(entry.getKey() + " = " + entry.getValue());
//            String[] parts = entry.getValue().split(",\\s");
//            Double part1 = Double.parseDouble(parts[0]);
//            Double part2 = Double.parseDouble(parts[1]);
//            System.out.println(part2);
//
//            //Array lis for stroing markers
//            ArrayList<Marker> markers = new ArrayList<>();
//            markers.add(m_map.addMarker(new MarkerOptions()
//                    .position(new LatLng(part1, part2))
//                    .title(entry.getKey())));
//        }

        destination = m_map.addMarker(new MarkerOptions()
                .position(new LatLng(27.667113,85.322346))
                .title("Set destination")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
        destination.showInfoWindow();

        m_map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                positionOfDestination = marker.getPosition();

            }
        });
    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public Location getSourceLocation(){
        return lastLocation;
    }
    public LatLng getdestinationLocation() { return positionOfDestination; };

    @Override
    public void onInfoWindowClick(Marker marker) {
        getDeviceLocation();
    }

    //setting geo context for navigation polylines
    public GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        String apiKey = "AIzaSyAg8TI4ZatrTjTgrSFUOoFaMXnEfKVlT3o";
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey(apiKey)
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    public DirectionsResult getDirectionsDetails(String  origin, String destination) {
        DateTime now = new DateTime();
        try {
            Log.i("tag direction", "initiating direction request");
            return DirectionsApi.newRequest(getGeoContext())
                    .mode(TravelMode.WALKING)
                    .origin(origin)
                    .destination(destination)
                    .await();

        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPolyline(DirectionsResult results, GoogleMap m_map) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());
        m_map.addPolyline(new PolylineOptions().addAll(decodedPath));
    }

    //method for returning string address from coordinates location
    public String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

}

