package com.sergeydeveloper7.cityemergencyservice.view.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sergeydeveloper7.cityemergencyservice.R;
import com.sergeydeveloper7.cityemergencyservice.view.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = AddressMapFragment.class.getSimpleName();
    private Context             context;
    private GoogleMap           mGoogleMap;
    private double              lat;
    private double              lng;
    private String              location = "";

    @BindView(R.id.eventLocationMap) MapView eventLocationMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        setHasOptionsMenu(true);
        if(getArguments() != null){
            lat = getArguments().getDouble("lat");
            lng = getArguments().getDouble("lng");
            location = getArguments().getString("address");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_address_map, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventLocationMap = view.findViewById(R.id.eventLocationMap);
        if (eventLocationMap != null) {
            eventLocationMap.onCreate(null);
            eventLocationMap.onResume();
            eventLocationMap.getMapAsync(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null && mainActivity.getToolbar() != null) {
            mainActivity.getToolbar().setTitle(context.getString(R.string.address_map_screen_title));
            mainActivity.getToolbar().setVisibility(View.VISIBLE);
            mainActivity.getAppBarLayout().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(context);
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(location));
        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(lat, lng)).zoom(16).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
