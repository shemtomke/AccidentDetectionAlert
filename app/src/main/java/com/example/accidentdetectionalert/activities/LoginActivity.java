package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.accidentdetectionalert.R;
import com.example.accidentdetectionalert.database.DatabaseHelper;
import com.example.accidentdetectionalert.models.User;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editLoginId);
        passwordEditText = findViewById(R.id.editLoginPassword);
        loginButton = findViewById(R.id.loginButton);

        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }
    // User can login in his personal account email id and password.
    public void Login()
    {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        User user = databaseHelper.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            // Login successful
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("userId", user.getUserId());
            editor.apply();

            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            // Add code here to navigate to the next activity or perform other actions
        } else {
            // Login failed
            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}