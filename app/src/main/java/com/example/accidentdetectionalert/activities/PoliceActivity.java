package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.accidentdetectionalert.R;

public class PoliceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
    }

    // Police is able to see to Today’s accident specifically or filter date wise to see previous ones.

    // Police will get a notification if it is assigned a Pickup.

}