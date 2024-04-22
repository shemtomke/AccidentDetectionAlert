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
import android.widget.ListView;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.adapters.EmergencyContactsAdapter;
import com.example.accidentdetectionalert.adapters.NotificationAdapter;
import com.example.accidentdetectionalert.database.DatabaseHelper;

import java.util.List;

public class Notification extends Fragment {
    RecyclerView notificationRecyclerView;
    NotificationAdapter notificationAdapter;
    DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(requireContext());
        sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        // Initialize adapter and set it to RecyclerView
        notificationAdapter = new NotificationAdapter(databaseHelper.getAllAccidentsWithUserDetails(true), getActivity(), sharedPreferences);
        notificationRecyclerView.setAdapter(notificationAdapter);
        // Inflate the layout for this fragment
        return view;
    }
}