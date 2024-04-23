package com.example.accidentdetectionalert.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.adapters.HospitalAdapter;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.Hospital;

import java.util.List;

public class ViewHospitals extends Fragment {
    Button createHospitalButton;
    private RecyclerView recyclerView;
    private HospitalAdapter adapter;
    private List<Hospital> hospitalList;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_hospitals, container, false);

        createHospitalButton = view.findViewById(R.id.createHospitalButtonAdmin);
        recyclerView = view.findViewById(R.id.adminHospitalsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(requireContext());
        hospitalList = databaseHelper.getAllHospitals();
        Log.d("ViewHospitals", "Hospital List Size: " + hospitalList.size());
        adapter = new HospitalAdapter(hospitalList, getActivity());
        recyclerView.setAdapter(adapter);

        createHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateHospital();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    void CreateHospital()
    {
        Fragment createHospital = new CreateHospital();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.adminFrameLayout, createHospital).addToBackStack(null).commit();
    }
}