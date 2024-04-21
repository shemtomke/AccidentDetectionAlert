package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.databinding.ActivityUserBinding;
import com.example.accidentdetectionalert.fragments.ViewEmergencyContacts;
import com.example.accidentdetectionalert.fragments.UserHistory;
import com.example.accidentdetectionalert.fragments.UserHome;
import com.example.accidentdetectionalert.fragments.UserProfile;

public class UserActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    int userId;
    ActivityUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        
        replaceFragment(new UserHome());

        binding.userBottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.userHome) {
                replaceFragment(new UserHome());
            } else if (item.getItemId() == R.id.userEmergencyContacts) {
                replaceFragment(new ViewEmergencyContacts());
            } else if (item.getItemId() == R.id.userHistory) {
                replaceFragment(new UserHistory());
            } else if (item.getItemId() == R.id.userProfile) {
                replaceFragment(new UserProfile());
            }

            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}