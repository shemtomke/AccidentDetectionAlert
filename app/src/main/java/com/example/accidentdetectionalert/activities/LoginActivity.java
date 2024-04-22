package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.User;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, signUpButton;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editLoginId);
        passwordEditText = findViewById(R.id.editLoginPassword);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButtonLogin);

        databaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }
    void signUp(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        User user = databaseHelper.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            // Login successful

            try {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("userId", user.getUserId());
                editor.commit();
            }catch (Exception e){
                Log.i("Saving User ID Error", String.valueOf(e));
            }

            try {
                startAppropriateActivity(user.getRole());
            }catch (Exception e){
                Log.i("Trying to load scene", String.valueOf(e));
            }

            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            // Login failed
            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void startAppropriateActivity(String role) {
        Intent intent = null;
        switch (role) {
            case "police":
                intent = new Intent(LoginActivity.this, PoliceActivity.class);
                break;
            case "ambulance":
                intent = new Intent(LoginActivity.this, AmbulanceActivity.class);
                break;
            case "hospital":
                intent = new Intent(LoginActivity.this, HospitalActivity.class);
                break;
            case "admin":
                intent = new Intent(LoginActivity.this, AdminActivity.class);
                break;
            case "citizen":
                intent = new Intent(LoginActivity.this, UserActivity.class);
                break;
        }

        if (intent != null) {
            try {
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "Failed to start activity", Toast.LENGTH_SHORT).show();
            }
        }
    }
}