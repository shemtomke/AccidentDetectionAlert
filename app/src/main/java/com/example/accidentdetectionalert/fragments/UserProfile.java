package com.example.accidentdetectionalert.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.activities.LoginActivity;
import com.example.accidentdetectionalert.activities.PoliceActivity;
import com.example.accidentdetectionalert.activities.UserActivity;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.User;

public class UserProfile extends Fragment {
    EditText userEmailId, userProfilePassword, userPhoneNumber;
    TextView userProfileName;
    Button logoutButton, updateButton;
    SharedPreferences sharedPreferences;
    int userId;
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        userProfileName = view.findViewById(R.id.userProfileName);
        userEmailId = view.findViewById(R.id.emailUserText);
        userPhoneNumber = view.findViewById(R.id.phoneNumberUserText);
        userProfilePassword = view.findViewById(R.id.passwordUserText);
        updateButton = view.findViewById(R.id.updateUserButton);
        logoutButton = view.findViewById(R.id.logOutButton);

        databaseHelper = new DatabaseHelper(requireContext());

        sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        DisplayUserDetails();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUserDetails();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    void DisplayUserDetails() {
        User user = databaseHelper.getUser(userId);
        if (user != null) {
            userProfileName.setText(user.getFullName());
            userEmailId.setText(user.getEmail());
            userProfilePassword.setText(user.getPassword());
            userPhoneNumber.setText(user.getPhoneNumber());
        } else {
            // Handle case where user does not exist
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show();
        }
    }
    void UpdateUserDetails()
    {
        String email = userEmailId.getText().toString();
        String password = userProfilePassword.getText().toString();
        String phoneNumber = userPhoneNumber.getText().toString();

        User user = databaseHelper.getUser(userId);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        databaseHelper.updateUserById(user);
    }
    void LogOut()
    {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}