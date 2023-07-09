package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Get references to views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate the input fields
                if (email.isEmpty()) {
                    Toast.makeText(login.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sign in user with email and password
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, task -> {
                            if (task.isSuccessful()) {
                                // User login successful
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Toast.makeText(login.this, "Logged in as " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                // Add your logic to navigate to the next screen or perform any other operations
                                Intent intent = new Intent(getApplicationContext(), calendar.class);
                                startActivity(intent);
                            } else {
                                // Login failed
                                Toast.makeText(login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                }
        });
    }
}
