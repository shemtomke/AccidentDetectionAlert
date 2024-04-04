package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.accidentdetectionalert.R;

public class AmbulanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
    }
    // The driver can see the current accident location along with the User details,
    // the driver can directly navigate through Google Maps.


    // The Driver can update the status whether he has picked/dropped the User.


    // The driver will get a notification if it is assigned a Pickup.

}