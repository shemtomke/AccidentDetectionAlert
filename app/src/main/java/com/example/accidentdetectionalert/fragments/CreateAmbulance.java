package com.example.accidentdetectionalert.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.Ambulance;
import com.example.accidentdetectionalert.models.Hospital;
import com.example.accidentdetectionalert.models.User;

import java.util.ArrayList;
import java.util.List;

public class CreateAmbulance extends Fragment {
    EditText driverFullNameText, emailText, phoneNumberText, passwordText;
    ImageButton backButton;
    CheckBox isHospitalCheckBox;
    Spinner hospitalsSpinner;
    Button createHospitalButton, updateHospitalButton, deleteHospitalButton;
    DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    int userId;
    User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ambulance, container, false);

        driverFullNameText = view.findViewById(R.id.editFullNameUserAmbulance);
        emailText = view.findViewById(R.id.editEmailUserAmbulance);
        phoneNumberText = view.findViewById(R.id.editPhoneUserAmbulance);
        passwordText = view.findViewById(R.id.editPasswordUserAmbulance);
        backButton = view.findViewById(R.id.backButtonCreateAmbulance);
        isHospitalCheckBox = view.findViewById(R.id.checkBoxAmbulance);
        hospitalsSpinner = view.findViewById(R.id.hospitalsSpinnerAmbulance);
        createHospitalButton = view.findViewById(R.id.createAmbulanceButton);
        updateHospitalButton = view.findViewById(R.id.updateAmbulanceButton);
        deleteHospitalButton = view.findViewById(R.id.deleteAmbulanceButton);

        databaseHelper = new DatabaseHelper(requireContext());

        sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        user = databaseHelper.getUser(userId);

        populateSpinner();
        checkBoxStatus();

        createHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAmbulance();
            }
        });
        updateHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAmbulance();
            }
        });
        deleteHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAmbulance();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    void createAmbulance()
    {
        String fullName = driverFullNameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String phoneNumber = phoneNumberText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        User ambulanceUser = new User(fullName, email, password, phoneNumber, "ambulance");

        int hospitalId = -1;
        if(isHospitalCheckBox.isChecked()) {
            Hospital selectedHospital = (Hospital) hospitalsSpinner.getSelectedItem();
            hospitalId = selectedHospital.getHospitalId();
        }

        Ambulance newAmbulance = new Ambulance(ambulanceUser, new Hospital(hospitalId), "Ambulance Location");

        if (fullName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            databaseHelper.createAmbulance(newAmbulance);
            GoToViewAmbulances();
            Toast.makeText(requireContext(), "Ambulance created successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Failed to create ambulance", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    void updateAmbulance()
    {
        String fullName = driverFullNameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String phoneNumber = phoneNumberText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        User ambulanceUser = new User(fullName, email, password, phoneNumber, "ambulance");

        int hospitalId = -1;
        if(isHospitalCheckBox.isChecked()) {
            Hospital selectedHospital = (Hospital) hospitalsSpinner.getSelectedItem();
            hospitalId = selectedHospital.getHospitalId();
        }

        Ambulance updatedAmbulance = new Ambulance(ambulanceUser, new Hospital(hospitalId), "Ambulance Location");

        databaseHelper.updateAmbulance(updatedAmbulance);
        GoToViewAmbulances();
    }
    void deleteAmbulance()
    {
        // Assuming you have an ambulanceId variable to store the ID of the ambulance to delete
        int ambulanceId = 0; // get the ambulance ID from somewhere

        databaseHelper.deleteAmbulance(ambulanceId);
        GoToViewAmbulances();
    }
    void checkBoxStatus()
    {
        isHospitalCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hospitalsSpinner.setEnabled(isChecked);
            }
        });
    }
    void populateSpinner()
    {
        List<Hospital> hospitals = databaseHelper.getAllHospitals();

        List<String> hospitalNames = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            hospitalNames.add(hospital.getUser().getFullName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, hospitalNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        hospitalsSpinner.setAdapter(adapter);
    }
    void GoToViewAmbulances()
    {
        Fragment viewAmbulancePage = new ViewAmbulance();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();

        if(user.getRole().equals("admin"))
        {
            fm.replace(R.id.adminFrameLayout, viewAmbulancePage).addToBackStack(null).commit();
        } else if(user.getRole().equals("hospital")){
            fm.replace(R.id.hospitalFrameLayout, viewAmbulancePage).addToBackStack(null).commit();
        }
    }
}