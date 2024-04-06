package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.databinding.ActivityAdminBinding;
import com.example.accidentdetectionalert.fragments.UserProfile;
import com.example.accidentdetectionalert.fragments.ViewAllAccidents;
import com.example.accidentdetectionalert.fragments.ViewAmbulance;
import com.example.accidentdetectionalert.fragments.ViewHospitals;
import com.example.accidentdetectionalert.fragments.ViewUsers;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ViewHospitals());

        binding.adminBottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.adminHospital) {
                replaceFragment(new ViewHospitals());
            } else if (item.getItemId() == R.id.adminAmbulance) {
                replaceFragment(new ViewAmbulance());
            } else if (item.getItemId() == R.id.adminUsers) {
                replaceFragment(new ViewUsers());
            } else if (item.getItemId() == R.id.adminAccidents) {
                replaceFragment(new ViewAllAccidents());
            } else if (item.getItemId() == R.id.adminProfile) {
                replaceFragment(new UserProfile());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.adminFrameLayout, fragment);
        fragmentTransaction.commit();
    }
    // FRAGMENTS
    // 1. Admin is the only person who can manage Hospital data and provide credentials to them.

    // 2. Admin can manage Ambulance data and provide credentials to them.
    // Ambulances are also mentioned if they work independently or are owned by Hospitals.

    // 3. View all the Users registered in this system.

    // 4. View all the Accidents and details about it, can be filtered date wise.

}