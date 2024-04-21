package com.example.accidentdetectionalert.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.adapters.EmergencyContactsAdapter;
import com.example.accidentdetectionalert.adapters.UserAdapter;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.User;

import java.util.List;

public class ViewUsers extends Fragment {
    RecyclerView usersRecyclerView;
    UserAdapter userAdapter;
    DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_users, container, false);

        usersRecyclerView = view.findViewById(R.id.userListRecyclerView);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(requireContext());

        // Initialize adapter and set it to RecyclerView
        userAdapter = new UserAdapter(databaseHelper.getAllUsers(), getActivity());
        usersRecyclerView.setAdapter(userAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}