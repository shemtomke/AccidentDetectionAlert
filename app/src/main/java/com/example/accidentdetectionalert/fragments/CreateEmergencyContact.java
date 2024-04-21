package com.example.accidentdetectionalert.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.example.accidentdetectionalert.activities.SignUpActivity;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.EmergencyContact;
import com.example.accidentdetectionalert.models.User;

public class CreateEmergencyContact extends Fragment {
    DatabaseHelper databaseHelper;
    Button createContactButton, updateContactButton, deleteContactButton;
    EditText contactFullName, contactPhoneNumber;
    private SharedPreferences sharedPreferences;
    int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_emergency_contact, container, false);

        createContactButton = view.findViewById(R.id.createContactButton);
        updateContactButton = view.findViewById(R.id.updateContactButton);
        deleteContactButton = view.findViewById(R.id.deleteContactButton);
        contactFullName = view.findViewById(R.id.contactFullName);
        contactPhoneNumber = view.findViewById(R.id.contactPhoneNumber);

        databaseHelper = new DatabaseHelper(requireContext());
        sharedPreferences = requireActivity().getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        // When we click the item in the recycler view
        Bundle args = getArguments();
        if (args != null) {
            int emergencyContactId = args.getInt("emergencyContactId", -1);
            // Use the emergencyContactId to fetch the details of the selected emergency contact
            contactFullName.setText(databaseHelper.getEmergencyContactById(emergencyContactId).getFullName());
            contactPhoneNumber.setText(databaseHelper.getEmergencyContactById(emergencyContactId).getPhoneContact());
        }
        createContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateContact();
            }
        });

        updateContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateContact();
            }
        });

        deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteContact();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    void CreateContact()
    {
        String fullName = contactFullName.getText().toString();
        String phoneNumber = contactPhoneNumber.getText().toString();

        User user = databaseHelper.getUser(userId);

        EmergencyContact emergencyContact = new EmergencyContact(user, fullName, phoneNumber);

        if(fullName.isEmpty() || phoneNumber.isEmpty())
        {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            databaseHelper.createEmergencyContact(emergencyContact);
            Toast.makeText(requireContext(), "Contact created successfully", Toast.LENGTH_SHORT).show();
            GoToViewEmergencyContacts();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Failed to create contact", Toast.LENGTH_SHORT).show();
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    void UpdateContact()
    {
        String fullName = contactFullName.getText().toString();
        String phoneNumber = contactPhoneNumber.getText().toString();

        User user = new User();
        EmergencyContact emergencyContact = new EmergencyContact(user, fullName, phoneNumber);

        if (fullName.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            databaseHelper.updateEmergencyContact(emergencyContact);
            Toast.makeText(requireContext(), "Contact updated successfully", Toast.LENGTH_SHORT).show();
            GoToViewEmergencyContacts();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Failed to update contact", Toast.LENGTH_SHORT).show();
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    void DeleteContact()
    {
        String fullName = contactFullName.getText().toString();
        String phoneNumber = contactPhoneNumber.getText().toString();
    }
    void GoToViewEmergencyContacts()
    {
        Fragment viewEmergencyPage = new ViewEmergencyContacts();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.userFrameLayout, viewEmergencyPage).addToBackStack(null).commit();
    }
}