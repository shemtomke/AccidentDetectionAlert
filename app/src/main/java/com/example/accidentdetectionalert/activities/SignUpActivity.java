package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.User;

public class SignUpActivity extends AppCompatActivity {
    Button signUpButton;
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

        databaseHelper = new DatabaseHelper(this);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
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

        User user = new User(fullName, email, password, phoneNumber, "Citizen");
        databaseHelper.createUser(user);

        Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}