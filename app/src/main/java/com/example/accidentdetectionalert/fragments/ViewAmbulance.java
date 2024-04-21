package com.example.accidentdetectionalert.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.adapters.AccidentAdapter;
import com.example.accidentdetectionalert.adapters.AmbulanceAdapater;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.Accident;
import com.example.accidentdetectionalert.models.Ambulance;
import com.example.accidentdetectionalert.models.User;

import java.util.List;

public class ViewAmbulance extends Fragment {
    private RecyclerView recyclerView;
    Button createAmbulanceButton;
    private AmbulanceAdapater adapter;
    private List<Ambulance> ambulanceList;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    int userId;
    User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_ambulance, container, false);

        recyclerView = view.findViewById(R.id.ambulanceListRecyclerView);
        createAmbulanceButton = view.findViewById(R.id.createAmbualnceAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(getActivity());

        sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        user = databaseHelper.getUser(userId);

        // Check if admin or Hospital that has logged In - Role (Check With Role)
        if(user.getRole().equals("admin"))
        {
            adapter = new AmbulanceAdapater(databaseHelper.getAllAmbulances(), getActivity());
        }
        else if(user.getRole().equals("hospital"))
        {
            adapter = new AmbulanceAdapater(databaseHelper.getAmbulancesForHospital(userId), getActivity());
        }

        recyclerView.setAdapter(adapter);

        createAmbulanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAmbulancePage();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    void createAmbulancePage()
    {
        Fragment createAmbulancePage = new CreateAmbulance();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

        if(user.getRole().equals("admin"))
        {
            fm.replace(R.id.adminFrameLayout, createAmbulancePage).addToBackStack(null).commit();
        }
        else if(user.getRole().equals("hospital"))
        {
            fm.replace(R.id.hospitalFrameLayout, createAmbulancePage).addToBackStack(null).commit();
        }
    }
}