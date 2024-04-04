package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.accidentdetectionalert.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
    // FRAGMENTS
    // 1. Admin is the only person who can manage Hospital data and provide credentials to them.

    // 2. Admin can manage Ambulance data and provide credentials to them.
    // Ambulances are also mentioned if they work independently or are owned by Hospitals.

    // 3. View all the Users registered in this system.

    // 4. View all the Accidents and details about it, can be filtered date wise.

}