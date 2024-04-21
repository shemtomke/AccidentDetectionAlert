package com.example.accidentdetectionalert.fragments;

import android.content.Context;
import android.content.Intent;
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
import com.example.accidentdetectionalert.adapters.EmergencyContactsAdapter;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.EmergencyContact;
import com.example.accidentdetectionalert.models.User;

import java.util.ArrayList;
import java.util.List;

public class ViewEmergencyContacts extends Fragment {
    Button createContactButton;
    RecyclerView emergencyContactsRecyclerView;
    DatabaseHelper databaseHelper;
    EmergencyContactsAdapter emergencyContactsAdapter;
    private SharedPreferences sharedPreferences;
    int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_emergency_contacts, container, false);

        createContactButton = view.findViewById(R.id.createEmergencyContactButton);
        emergencyContactsRecyclerView = view.findViewById(R.id.emergencyContactsListRecyclerView);
        emergencyContactsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(requireContext());
        sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        // Initialize adapter and set it to RecyclerView
        emergencyContactsAdapter = new EmergencyContactsAdapter(databaseHelper.getEmergencyContactsByUserId(userId), getActivity());
        emergencyContactsRecyclerView.setAdapter(emergencyContactsAdapter);
        createContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEmergencyContact();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    // Go to the emergency contact create page
    void createEmergencyContact()
    {
        Fragment createEmergencyPage = new CreateEmergencyContact();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.userFrameLayout, createEmergencyPage).addToBackStack(null).commit();
    }
}