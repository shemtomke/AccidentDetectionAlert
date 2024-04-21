package com.example.accidentdetectionalert.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.User;

public class CreateHospital extends Fragment {
    Button createHospitalButton, deleteHospitalButton, updateHospitalButton;
    EditText hospitalNameText, emailText, phoneText, passwordText;
    DatabaseHelper databaseHelper;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_hospital, container, false);

        hospitalNameText = view.findViewById(R.id.editFullNameUserHospital);
        emailText = view.findViewById(R.id.editEmailUserHospital);
        phoneText = view.findViewById(R.id.editPhoneUserHospital);
        passwordText = view.findViewById(R.id.editPasswordUserHospital);

        createHospitalButton = view.findViewById(R.id.createHospitalButton);
        updateHospitalButton = view.findViewById(R.id.updateHospitalButton);
        deleteHospitalButton = view.findViewById(R.id.deleteHospitalButton);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24); // Set back button icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click (e.g., go back to previous fragment)
                requireActivity().onBackPressed();
            }
        });

        databaseHelper = new DatabaseHelper(requireContext());
        createHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHospital();
            }
        });
        updateHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHospital();
            }
        });
        deleteHospitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHospital();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    void createHospital()
    {
        String hospitalName = hospitalNameText.getText().toString();
        String email = emailText.getText().toString();
        String phoneNumber = phoneText.getText().toString();
        String password = passwordText.getText().toString();

        if(hospitalName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty())
        {
            Toast.makeText(requireContext(), "Please Fill in all details!", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(hospitalName, email, password, phoneNumber, "hospital");
        try {
            databaseHelper.createUser(user);
            Toast.makeText(requireContext(), "Created hospital successfully!", Toast.LENGTH_SHORT).show();
            GoToViewHospitals();
        }catch (Exception e)
        {
            Toast.makeText(requireContext(), "Failed to create hospital", Toast.LENGTH_SHORT).show();
        }
    }
    void updateHospital()
    {
        String hospitalName = hospitalNameText.getText().toString();
        String email = emailText.getText().toString();
        String phoneNumber = phoneText.getText().toString();
        String password = passwordText.getText().toString();

        User user = new User(hospitalName, email, password, phoneNumber, "hospital");
        try {
            databaseHelper.updateUserById(user);
            Toast.makeText(requireContext(), "Updated hospital successfully!", Toast.LENGTH_SHORT).show();
            GoToViewHospitals();
        }catch (Exception e)
        {
            Toast.makeText(requireContext(), "Failed to update hospital", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteHospital()
    {
        String hospitalName = hospitalNameText.getText().toString();
        String email = emailText.getText().toString();
        String phoneNumber = phoneText.getText().toString();
        String password = passwordText.getText().toString();

        User user = new User(hospitalName, email, password, phoneNumber, "hospital");
        try {
            //databaseHelper.deleteUserById(user);
            Toast.makeText(requireContext(), "Deleted hospital successfully!", Toast.LENGTH_SHORT).show();
            GoToViewHospitals();
        }catch (Exception e)
        {
            Toast.makeText(requireContext(), "Failed to delete hospital", Toast.LENGTH_SHORT).show();
        }
    }
    void GoToViewHospitals()
    {
        Fragment viewHospitalsPage = new ViewHospitals();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.adminFrameLayout, viewHospitalsPage).addToBackStack(null).commit();
    }
}