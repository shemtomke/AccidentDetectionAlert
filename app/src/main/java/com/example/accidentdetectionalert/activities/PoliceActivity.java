package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.fragments.Notification;
import com.example.accidentdetectionalert.fragments.PoliceHome;
import com.example.accidentdetectionalert.fragments.UserProfile;

public class PoliceActivity extends AppCompatActivity {
    ActivityPoliceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPoliceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new PoliceHome());

        binding.policeBottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.policeHome) {
                replaceFragment(new PoliceHome());
            } else if (item.getItemId() == R.id.policeNotifications) {
                replaceFragment(new Notification());
            } else if (item.getItemId() == R.id.policeProfile) {
                replaceFragment(new UserProfile());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.policeFrameLayout, fragment);
        fragmentTransaction.commit();
    }
    // Police is able to see to Today’s accident specifically or filter date wise to see previous ones.
    // Police will get a notification if it is assigned a Pickup.
}