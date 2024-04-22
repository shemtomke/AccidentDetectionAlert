package com.example.accidentdetectionalert.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.accidentdetectionalert.models.User;

import java.util.List;

public class ViewAccidents extends Fragment {
    private RecyclerView recyclerView;
    private AccidentAdapter adapter;
    private List<Accident> accidentList;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_accidents, container, false);

        recyclerView = view.findViewById(R.id.adminAccidentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(getActivity());
        sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        User user = databaseHelper.getUser(userId);

        if (user.getRole().equals("admin")) {
            accidentList = databaseHelper.getAllAccidentsWithUserDetails(true);
        } else if(user.getRole().equals("hospital")) {
            accidentList = databaseHelper.getAccidentsForHospital(userId);
        }

        adapter = new AccidentAdapter(accidentList, getActivity());
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
}