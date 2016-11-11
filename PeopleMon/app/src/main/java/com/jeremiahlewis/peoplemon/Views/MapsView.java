package com.jeremiahlewis.peoplemon.Views;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jeremiahlewis.peoplemon.MainActivity;
import com.jeremiahlewis.peoplemon.Models.Account;
import com.jeremiahlewis.peoplemon.Models.User;
import com.jeremiahlewis.peoplemon.Network.RestClient;
import com.jeremiahlewis.peoplemon.PeopleMonApplication;
import com.jeremiahlewis.peoplemon.R;
import com.jeremiahlewis.peoplemon.Stages.ListCaughtStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jeremiahlewis on 11/8/16.
 */

public class MapsView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {

    private GoogleApiClient mGoogleApiClient;

    public static final String TAG = MapsView.class.getSimpleName();

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationRequest mLocationRequest;
    private Location lastLocation;
    private LocationListener locationListener;
    private double lastLat;
    private double lastLong;
    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private Handler checkNear;
    private Handler checkingIn;
    private String UserId;
    private String name;
    private String UserName;
    private String Created;
    private String AvatarBase64;



     LatLng Home = new LatLng(latitude, longitude);

    private Context context;

    public MapsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Bind(R.id.map)
    MapView mapsView;

    @Bind(R.id.view_caught_button)
    FloatingActionButton viewCaught;

    @Bind(R.id.checkIn_Button)
    FloatingActionButton checkIn;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mapsView.onCreate(((MainActivity) getContext()).savedInstanceState);
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
                .setFastestInterval(1 * 1000);


//        ((MainActivity)context).showMenuItem(true);


    }

    public void onMapReady(GoogleMap googleMap) {
//        int STREET_LEVEL = 15;
//        int BUILDING_LEVEL = 20;
//        LatLng currentLocation = new LatLng(38.0405837, -84.5037164);
//        Place currentPlace = null;
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        googleMap.moveCamera(newLatLngZoom(currentLocation, BUILDING_LEVEL));
//        googleMap.addMarker(new MarkerOptions().anchor(0.0f, 1.0f).position(currentLocation));
        mMap = googleMap;
        mMap.getUiSettings().isMyLocationButtonEnabled();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.clear();

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {

        }
        checkNear = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                checkNearby();
                checkNear.postDelayed(this, 5000);
            }
        };
        checkNear.postDelayed(r, 5000);

        checkingIn = new Handler();
        final Runnable ci = new Runnable() {
            public void run() {
                checkIn();
                checkingIn.postDelayed(this, 5000);

            }
        };
        checkingIn.postDelayed(ci, 5000);

        mMap.clear();
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        GroundOverlayOptions radar = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.radar))
                .position(Home, 200f, 200f);
        GroundOverlay imageOverlay = mMap.addGroundOverlay(radar);

        final Circle circle = mMap.addCircle(new CircleOptions().center(Home)
                .strokeColor(Color.BLUE).radius(80));
        ValueAnimator vAnimator = new ValueAnimator();
        vAnimator.setRepeatCount(ValueAnimator.INFINITE);
        vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
        vAnimator.setIntValues(80, 0);
        vAnimator.setDuration(2500);
        vAnimator.setEvaluator(new IntEvaluator());
        vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                // Log.e("", "" + animatedFraction);
                circle.setRadius(animatedFraction * 80);
            }
        });
        vAnimator.start();


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Location services connected.");//This gives permission to access the location data.

        try {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (lastLocation != null) {
            lastLat = lastLocation.getLatitude();
            lastLong = lastLocation.getLongitude();
            Log.d("------------------>", lastLat + "  " + lastLong);
        } else if (lastLocation == null) {
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
            } catch (SecurityException e) {
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


    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());


//
    }


    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(loc));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));

            GroundOverlayOptions radar = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.mipmap.radar))
                    .position(loc, 200f, 200f);
            GroundOverlay imageOverlay = mMap.addGroundOverlay(radar);

            final Circle circle = mMap.addCircle(new CircleOptions().center(loc)
                    .strokeColor(Color.BLUE).radius(80));
            ValueAnimator vAnimator = new ValueAnimator();
            vAnimator.setRepeatCount(ValueAnimator.INFINITE);
            vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
            vAnimator.setIntValues(80, 0);
            vAnimator.setDuration(2500);
            vAnimator.setEvaluator(new IntEvaluator());
            vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    circle.setRadius(animatedFraction * 80);
                }
            });
            vAnimator.start();

        }
    };

    @OnClick(R.id.view_caught_button)
    public void viewCaught() {

        Flow flow = PeopleMonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new ListCaughtStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    public void checkIn() {
        Account account = new Account(lastLong, lastLat);
        RestClient restClient = new RestClient();
        restClient.getApiService().checkin(account).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: I AM CHECKING IN ");
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "onFailure: **********MESSED UP*******");
                Toast.makeText(context, "Check In Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void checkNearby() {
        RestClient restClient = new RestClient();
        restClient.getApiService().findUsersNearby(100).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    for (User user : response.body()) {

                        if(user.getAvatarBase64() == null || user.getAvatarBase64().length()<=100) {
                            lastLat = user.getLatitude();
                            lastLong = user.getLongitude();
                            LatLng userpos = new LatLng(lastLat, lastLong);
                            mMap.addMarker(new MarkerOptions().title(user.getUserName())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.user))
                                    .snippet(user.getUserId())
                                    .position(userpos));
                        } else{
                            String encodedImage = user.getAvatarBase64();
                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            try {
                                decodedByte = Bitmap.createScaledBitmap(decodedByte, 120, 120, false);

                                lastLat = user.getLatitude();
                                lastLong = user.getLongitude();
                                LatLng userpos = new LatLng(lastLat, lastLong);
                                mMap.addMarker(new MarkerOptions().title(user.getUserName())
                                        .icon(BitmapDescriptorFactory.fromBitmap(decodedByte))
                                        .snippet(user.getUserId())
                                        .position(userpos));
                            }catch(Exception e){
                                Log.e(MapsView.class.getSimpleName(),e.toString());
                            }
                        }

                    }
                } else {
                    Toast.makeText(context, "you messesd up", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context, "you messed up", Toast.LENGTH_LONG).show();


            }

        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                name = marker.getTitle();
                UserId = marker.getSnippet();
                catchThem();
                return false;
            }
        });
    }

    public void catchThem() {
        RestClient restClient = new RestClient();
        restClient.getApiService().catchThem(UserId, 500).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }


}
