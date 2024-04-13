package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.databinding.ActivityHospitalBinding;
import com.example.accidentdetectionalert.databinding.ActivityPoliceBinding;
import com.example.accidentdetectionalert.fragments.HospitalHome;
import com.example.accidentdetectionalert.fragments.Notification;
import com.example.accidentdetectionalert.fragments.UserProfile;
import com.example.accidentdetectionalert.fragments.ViewAllAccidents;
import com.example.accidentdetectionalert.fragments.ViewAmbulance;
import com.example.accidentdetectionalert.fragments.ViewHospitalAccidents;
import com.example.accidentdetectionalert.fragments.ViewHospitalAmbulances;
import com.example.accidentdetectionalert.fragments.ViewHospitals;
import com.example.accidentdetectionalert.fragments.ViewUsers;

public class HospitalActivity extends AppCompatActivity {
    ActivityHospitalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHospitalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HospitalHome());

        binding.hospitalBottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.hospitalHome) {
                replaceFragment(new HospitalHome());
            } else if (item.getItemId() == R.id.hospitalAmbulance) {
                replaceFragment(new ViewHospitalAmbulances());
            } else if (item.getItemId() == R.id.hospitalAccidents) {
                replaceFragment(new ViewHospitalAccidents());
            } else if (item.getItemId() == R.id.hospitalNotifications) {
                replaceFragment(new Notification());
            } else if (item.getItemId() == R.id.hospitalProfile) {
                replaceFragment(new UserProfile());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.hospitalFrameLayout, fragment);
        fragmentTransaction.commit();
    }
    // The User can see the current accident location assigned to his Hospital if any.
    // Hospital can also update the status whether the user has been admitted in the Hospital.

    // List of all the Accidents and details about it assigned to his Hospital.

    // Hospital can also manage their owned Ambulances.

    // The Hospital will get a notification if it is assigned a Pickup.

}