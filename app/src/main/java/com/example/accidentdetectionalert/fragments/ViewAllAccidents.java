package com.example.accidentdetectionalert.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.adapters.AccidentAdapter;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.Accident;

import java.util.List;

public class ViewAllAccidents extends Fragment {
    private RecyclerView recyclerView;
    private AccidentAdapter adapter;
    private List<Accident> accidentList;
    private DatabaseHelper databaseHelper;
    // Add a boolean flag to determine if the adapter should show all accidents or only those assigned to the hospital
    private boolean showAllAccidents;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_all_accidents, container, false);

        recyclerView = view.findViewById(R.id.adminAccidentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(getActivity());
        if (showAllAccidents) {
            accidentList = databaseHelper.getAllAccidentsWithUserDetails();
        } else {
            // Assuming you have a way to get the current user's hospital ID
            String hospitalId = getCurrentUserHospitalId();
            accidentList = databaseHelper.getAccidentsForHospital(hospitalId);
        }

        adapter = new AccidentAdapter(accidentList, getActivity());
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
    private String getCurrentUserHospitalId() {
        // Implement your logic to get the current user's hospital ID
        return "your_hospital_id_here";
    }
}