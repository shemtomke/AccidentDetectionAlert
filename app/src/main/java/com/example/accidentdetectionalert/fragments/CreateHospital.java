package com.example.accidentdetectionalert.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.Hospital;
import com.example.accidentdetectionalert.models.User;

public class CreateHospital extends Fragment {
    Button createHospitalButton, deleteHospitalButton, updateHospitalButton;
    ImageButton backButton;
    EditText hospitalNameText, emailText, phoneText, passwordText;
    DatabaseHelper databaseHelper;

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
        backButton = view.findViewById(R.id.backButtonCreateHospital);

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
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToViewHospitals();
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
        databaseHelper.createUser(user);

        Hospital hospital = new Hospital(databaseHelper.getUser(user.getUserId()));
        try {
            databaseHelper.createHospital(hospital);
            Log.i("User Id", user.getUserId() + user.getFullName());
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