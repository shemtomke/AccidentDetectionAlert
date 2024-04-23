package com.example.accidentdetectionalert.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.Accident;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class HospitalHome extends Fragment implements OnMapReadyCallback {
    DatabaseHelper databaseHelper;
    List<Accident> allAccidentList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_home, container, false);

        databaseHelper = new DatabaseHelper(requireContext());

        allAccidentList = databaseHelper.getAllAccidentsWithUserDetails(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.hospital_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        for (Accident accident : allAccidentList) {
            String locationString = accident.getLocation(); // Assuming getLocation() returns the location string
            String[] parts = locationString.split(",");
            double latitude = Double.parseDouble(parts[0]);
            double longitude = Double.parseDouble(parts[1]);
            LatLng location = new LatLng(latitude, longitude);

            googleMap.addMarker((new MarkerOptions().position(location).title(accident.getUser().getFullName())));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
        }
    }
}