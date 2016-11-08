package com.jeremiahlewis.peoplemon.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.jeremiahlewis.peoplemon.MainActivity;
import com.jeremiahlewis.peoplemon.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by jeremiahlewis on 11/8/16.
 */

public class MapsView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {

    public MapsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Bind(R.id.map)
//    private MapView mapsView;
//
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        ButterKnife.bind(this);
//        mapsView.onCreate(((MainActivity) getContext()).savedInstanceState);
//        mapsView.getMapAsync(this);
//    }
//
//    public void onMapReady(GoogleMap googleMap) {
//
//
//        // Lots of code
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        return false;
//    }


}
