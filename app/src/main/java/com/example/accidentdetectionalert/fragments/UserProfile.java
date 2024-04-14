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

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.activities.LoginActivity;
import com.example.accidentdetectionalert.activities.PoliceActivity;
import com.example.accidentdetectionalert.activities.UserActivity;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText userEmailId, userProfilePassword, userPhoneNumber;
    TextView userProfileName;
    Button logoutButton, updateButton;
    SharedPreferences sharedPreferences;
    int userId;
    DatabaseHelper databaseHelper;

    public UserProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfile newInstance(String param1, String param2) {
        UserProfile fragment = new UserProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        databaseHelper = new DatabaseHelper(requireContext());

        userProfileName = view.findViewById(R.id.userProfileName);
        userEmailId = view.findViewById(R.id.emailUserText);
        userPhoneNumber = view.findViewById(R.id.phoneNumberUserText);
        userProfilePassword = view.findViewById(R.id.passwordUserText);
        updateButton = view.findViewById(R.id.updateUserButton);
        logoutButton = view.findViewById(R.id.logOutButton);

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
    void DisplayUserDetails()
    {
        User user = databaseHelper.getUser(userId);

        userProfileName.setText(user.getFullName());
        userEmailId.setText(user.getEmail());
        userProfilePassword.setText(user.getPassword());
        userPhoneNumber.setText(user.getPhoneNumber());
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