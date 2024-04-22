package com.example.accidentdetectionalert.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.adapters.AccidentAdapter;
import com.example.accidentdetectionalert.adapters.EmergencyContactsAdapter;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.Accident;

import java.util.ArrayList;
import java.util.List;

public class PoliceHome extends Fragment {
    SearchView searchView;
    RecyclerView accidentsRecyclerView;
    DatabaseHelper databaseHelper;
    AccidentAdapter accidentAdapter;
    List<Accident> allAccidents; // Store all accidents here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_police_home, container, false);

        accidentsRecyclerView = view.findViewById(R.id.accidentLocationPoliceListRecyclerView);
        searchView = view.findViewById(R.id.searchAccidents);
        accidentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(requireContext());
        allAccidents = databaseHelper.getAllAccidentsWithUserDetails(true); // Get all accidents

        // Initialize adapter and set it to RecyclerView
        accidentAdapter = new AccidentAdapter(allAccidents, getActivity());
        accidentsRecyclerView.setAdapter(accidentAdapter);

        // Set up SearchView listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the list based on the search query
                filter(newText);
                return true;
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    private void filter(String searchText) {
        List<Accident> filteredList = new ArrayList<>();
        for (Accident accident : allAccidents) {
            // Filter logic (you can customize this based on your requirements)
            if (accident.getLocation().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(accident);
            }
        }
        // Update RecyclerView with the filtered list
        accidentAdapter.filterList(filteredList);
    }
}