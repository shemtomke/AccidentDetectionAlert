package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.User;

import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    Button signUpButton, loginButton;
    EditText fullNameText, emailText, passwordText, phoneNumberText;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameText = findViewById(R.id.editFullNameUser);
        emailText = findViewById(R.id.editEmailUser);
        passwordText = findViewById(R.id.editPasswordUser);
        phoneNumberText = findViewById(R.id.editPhoneUser);
        signUpButton = findViewById(R.id.signUpButton);
        loginButton = findViewById(R.id.loginButtonSignUp);

        databaseHelper = new DatabaseHelper(this);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }
    void Login(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void SignUp()
    {
        String fullName = fullNameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String phoneNumber = phoneNumberText.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Creating normal users
        Random random = new Random();
        int userId = random.nextInt(Integer.MAX_VALUE);
        User user = new User(userId, fullName, email, password, phoneNumber, "citizen");
        databaseHelper.createUser(user);

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId);
        editor.apply();

        Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
        finish();

        Intent intent = new Intent(SignUpActivity.this, UserActivity.class);
        startActivity(intent);
    }
}