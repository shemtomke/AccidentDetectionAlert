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
import android.widget.Button;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.adapters.EmergencyContactsAdapter;
import com.example.accidentdetectionalert.adapters.HistoryAdapter;
import com.example.accidentdetectionalert.database.DatabaseHelper;

public class UserHistory extends Fragment {
    RecyclerView userHistoryRecyclerView;
    DatabaseHelper databaseHelper;
    HistoryAdapter historyAdapter;
    private SharedPreferences sharedPreferences;
    int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_history, container, false);

        userHistoryRecyclerView = view.findViewById(R.id.userHistoryRecyclerView);
        userHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(requireContext());
        sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        // Initialize adapter and set it to RecyclerView
        historyAdapter = new HistoryAdapter(databaseHelper.getHistoryAlertsForUser(userId), getActivity());
        userHistoryRecyclerView.setAdapter(historyAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}