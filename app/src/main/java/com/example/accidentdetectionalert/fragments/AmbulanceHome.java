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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AmbulanceHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmbulanceHome extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoogleMap googleMap;
    public AmbulanceHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AmbulanceHome.
     */
    // TODO: Rename and change types and number of parameters
    public static AmbulanceHome newInstance(String param1, String param2) {
        AmbulanceHome fragment = new AmbulanceHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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