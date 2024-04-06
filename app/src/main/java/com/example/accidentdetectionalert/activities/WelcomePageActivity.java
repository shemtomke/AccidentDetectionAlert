package com.example.accidentdetectionalert.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.accidentdetectionalert.R;

public class WelcomePageActivity extends AppCompatActivity {
    Button loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        loginButton = findViewById(R.id.goToLoginButton);
        signUpButton = findViewById(R.id.goToRegisterButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePageActivity.this, AdminActivity.class);
                WelcomePageActivity.this.startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePageActivity.this, HomeActivity.class);
                WelcomePageActivity.this.startActivity(intent);
            }
        });
    }
}