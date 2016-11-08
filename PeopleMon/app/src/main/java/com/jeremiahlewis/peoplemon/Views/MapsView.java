package com.jeremiahlewis.peoplemon.Views;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jeremiahlewis.peoplemon.MainActivity;
import com.jeremiahlewis.peoplemon.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom;


/**
 * Created by jeremiahlewis on 11/8/16.
 */

public class MapsView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, LocationListener {
    private GoogleApiClient mGoogleApiClient;

    public static final String TAG = MapsView.class.getSimpleName();

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationRequest mLocationRequest;
    private Location lastLocation;
    private LocationListener locationListener;
    private double lastLat;
    private double lastLong;

    private Context context;

    public MapsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Bind(R.id.map)
     MapView mapsView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mapsView.onCreate(((MainActivity)getContext()).savedInstanceState);
        mapsView.onResume();
        mapsView.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1*1000);


    }

    public void onMapReady(GoogleMap googleMap) {
        int STREET_LEVEL = 15;
        int BUILDING_LEVEL = 20;
        LatLng ELEVEN_FIFTY = new LatLng(38.0405837, -84.5037164);
        Place currentPlace = null;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(newLatLngZoom(ELEVEN_FIFTY, BUILDING_LEVEL));
        googleMap.addMarker(new MarkerOptions().anchor(0.0f, 1.0f).position(ELEVEN_FIFTY));



    }

    @Override
    public void onConnected(@Nullable  Bundle bundle) {
        Log.i(TAG, "Location services connected.");//This gives permission to access the location data.

            try {
                lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            } catch (SecurityException e){
                e.printStackTrace();
            }
            if(lastLocation != null){
                lastLat = lastLocation.getLatitude();
                lastLong = lastLocation.getLongitude();
                Log.d("------------------>", lastLat + "  " + lastLong);
            } else if (lastLocation == null){
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
                } catch (SecurityException e){
                    e.printStackTrace();
                }
            } else {
                handleNewLocation(lastLocation);
            }
            Log.d(">>>>>>>>>>>>>>>>>>>>", "CONNECTED");
        }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended.Please reconnect.");


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void handleNewLocation(Location location){
        Log.d(TAG, location.toString());
    }


    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(lastLocation);
    }
}
