package com.example.accidentdetectionalert.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.accidentdetectionalert.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AmbulanceHome extends Fragment implements OnMapReadyCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ambulance_home, container, false);

//        SupportMapFragment mapFragment = (SupportMapFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.ambulance_map);
//        mapFragment.getMapAsync(this);

        return view;
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Get Ambulance Location Coordinates
        // Get all users' location coordinates that have had accidents assigned to a hospital/ambulance
//        LatLng location = new LatLng(-0.680482, 34.777061);
//        googleMap.addMarker((new MarkerOptions().position(location).title("Kisii")));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
    }
}