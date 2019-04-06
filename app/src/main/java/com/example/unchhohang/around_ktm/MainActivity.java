package com.example.unchhohang.around_ktm;

import android.Manifest;
import android.content.pm.PackageManager;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.
        google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                                sourLat = mLastKnownLocation.getLatitude();
                                sourLog = mLastKnownLocation.getLongitude();

                                source = new Stop("Source", sourLat , sourLog);
                                desti = new Stop("destination", getdestinationLocation().latitude, getdestinationLocation().longitude);

                                ReadyToRace readyToRace = new ReadyToRace();
                                List<Stop> paths = readyToRace.findingPath(source, desti);
                            for(int i = 0; i < paths.size(); i++){
                                Log.i("tag path","I am the path " + paths.get(i).name);
                            }

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


        //Stops tyring to
//        Stop s1 = new Stop("lagankhel_p", 27.667113, 85.322346);
//        Stop s2 = new Stop("patan_hospital_p",27.668387, 85.321663);
//        Stop s3 = new Stop("kumaripati_p",27.670801, 85.319957);
//        Stop s4 = new Stop("manbhawan_p",27.672074, 85.315558);
//        Stop s5 = new Stop("jawlakhel_p",27.672632, 85.313707);
//        Stop s6 = new Stop("pulchowk_p",27.676070, 85.315721);
//        Stop s7 = new Stop("harihar_bhawan_pulchowk_p",27.681087, 85.317402);
//        Stop s8 = new Stop("jwagal_kupondol_p",27.685257, 85.318100);
//        Stop s9 = new Stop("kandewatathan_kupondol_p",27.686757, 85.317133);
//        Stop s10 = new Stop("thaptahali_p",27.687845, 85.316307);
//        Stop s11 = new Stop("maitighar_p",27.694206, 85.319275);
//        Stop s12 = new Stop("singhadurbar_p",27.694805, 85.320175);
//        Stop s13 = new Stop("bhadrakali_mandir_p",27.699051, 85.317503);
//        Stop s14 = new Stop("sahid_gate_p",27.699298, 85.317734);
//        Stop s15 = new Stop("ratnapark_p",27.706788, 85.314730);
//        Stop s16 = new Stop("bhirkutimandap_p",27.700914, 85.316609);
//        Stop s17 = new Stop("bhadrakali_mandir_n",27.699298, 85.317734);
//        Stop s18 = new Stop("maitighar_n",27.694088, 85.319397);
//        Stop s19 = new Stop("thapathali_n",27.690458, 85.317760);
//        Stop s20 = new Stop("kupondole_n",27.687641, 85.316763);
//        Stop s22 = new Stop("jwagal_kupondol_n",27.685452, 85.318223);
//        Stop s23 = new Stop("harihar_bhawan_pulchowk_n",27.681149, 85.317630);
//        Stop s24 = new Stop("pulchow_n",27.676821, 85.316165);
//        Stop s25 = new Stop("jawalkhel_n",27.672634, 85.313930);
//        Stop s26 = new Stop("manbhawan_n",27.672153, 85.315956);
//        Stop s27 = new Stop("kumaripati_n",27.670687, 85.320493);
//        Stop s28 = new Stop("patan_hospital_n",27.669727, 85.321909);
//        Stop s29 = new Stop("lagankhel_n",27.667031, 85.322473);


        //markers


        //Hash map for marker
//        Map<String, String> stops = new HashMap<String, String>();
//        stops.put("lagankhel_p", "27.667113, 85.322346");
//        stops.put("patan_hospital_p", "27.668387, 85.321663");
//        stops.put("kumaripati_p", "27.670801, 85.319957");
//        stops.put("manbhawan_p", "27.672074, 85.315558");
//        stops.put("jawlakhel_p", "27.672632, 85.313707");
//        stops.put("pulchowk_p", "27.676070, 85.315721");
//        stops.put("harihar_bhawan_pulchowk_p", "27.681087, 85.317402");
//        stops.put("jwagal_kupondol_p", "27.685257, 85.318100");
//        stops.put("kandewatathan_kupondol_p", "27.686757, 85.317133");
//        stops.put("thaptahali_p", "27.687845, 85.316307");
//        stops.put("maitighar_p", "27.694206, 85.319275");
//        stops.put("singhadurbar_p", "27.694805, 85.320175");
//        stops.put("bhadrakali_mandir_p", "27.699051, 85.317503");
//        stops.put("sahid_gate_p", "27.699298, 85.317734");
//        stops.put("ratnapark_p", "27.706788, 85.314730");
//        stops.put("bhirkutimandap_p", "27.700914, 85.316609");
//        stops.put("bhadrakali_mandir_n", "27.699298, 85.317734");
//        stops.put("maitighar_n", "27.694088, 85.319397");
//        stops.put("thapathali_n", "27.690458, 85.317760");
//        stops.put("kupondole_n", "27.687641, 85.316763");
//        stops.put("jwagal_kupondol_n", "27.685452, 85.318223");
//        stops.put("harihar_bhawan_pulchowk_n", "27.681149, 85.317630");
//        stops.put("pulchow_n", "27.676821, 85.316165");
//        stops.put("jawalkhel_n", "27.672634, 85.313930");
//        stops.put("manbhawan_n", "27.672153, 85.315956");
//        stops.put("kumaripati_n", "27.670687, 85.320493");
//        stops.put("patan_hospital_n", "27.669727, 85.321909");
//        stops.put("lagankhel_n", "27.667031, 85.322473");
//
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
                .draggable(true));
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
        Log.i("tag destination", "destination latlng the hustler:" + getdestinationLocation().latitude +" " + getdestinationLocation());
    }

}

