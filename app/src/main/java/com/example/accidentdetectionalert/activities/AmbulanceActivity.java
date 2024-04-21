package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.fragments.AmbulanceHome;
import com.example.accidentdetectionalert.fragments.Notification;
import com.example.accidentdetectionalert.fragments.UserProfile;

public class AmbulanceActivity extends AppCompatActivity {
    ActivityAmbulanceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAmbulanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new AmbulanceHome());

        binding.ambulanceBottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.ambulanceHome) {
                replaceFragment(new AmbulanceHome());
            } else if (item.getItemId() == R.id.ambulanceNotifications) {
                replaceFragment(new Notification());
            } else if (item.getItemId() == R.id.ambulanceProfile) {
                replaceFragment(new UserProfile());
            }

            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ambulanceFrameLayout, fragment);
        fragmentTransaction.commit();
    }
    // The driver can see the current accident location along with the User details,
    // the driver can directly navigate through Google Maps.

    // The Driver can update the status whether he has picked/dropped the User.

    // The driver will get a notification if it is assigned a Pickup.
}