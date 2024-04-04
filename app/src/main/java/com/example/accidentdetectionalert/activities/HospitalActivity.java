package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.accidentdetectionalert.R;

public class HospitalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
    }

    // The User can see the current accident location assigned to his Hospital if any.
    // Hospital can also update the status whether the user has been admitted in the Hospital.

    // List of all the Accidents and details about it assigned to his Hospital.

    // Hospital can also manage their owned Ambulances.

    // The Hospital will get a notification if it is assigned a Pickup.

}