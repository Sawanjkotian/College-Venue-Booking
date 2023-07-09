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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText, nameEditText, phoneEditText;
    private Button registerButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Get references to views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String phoneNumber = phoneEditText.getText().toString().trim();

// Validate the additional fields
                if (name.isEmpty()) {
                    Toast.makeText(sign_up.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phoneNumber.isEmpty()) {
                    Toast.makeText(sign_up.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Validate the input fields
                if (email.isEmpty()) {
                    Toast.makeText(sign_up.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(sign_up.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }



                // Create a new user with email and password
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(sign_up.this, task -> {
                            if (task.isSuccessful()) {
                                // User registration successful
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                saveUserDataToDatabase(user.getUid(), name, phoneNumber, email); // Save user data to the database
                                Toast.makeText(sign_up.this, "Registered successfully as " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                // Add your logic to navigate to the next screen or perform any other operations
                                Intent intent = new Intent(getApplicationContext(), calendar.class);
                                startActivity(intent);
                            } else {
                                // Registration failed
                                Exception exception = task.getException();
                                if (exception != null) {
                                    Toast.makeText(sign_up.this, "Registration failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(sign_up.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    private void saveUserDataToDatabase(String uid, String name, String phoneNumber, String email) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        User user = new User(name, phoneNumber, email); // Create a User object with the data

        databaseReference.child(uid).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    // Data saved successfully
                    Toast.makeText(sign_up.this, "User data saved to database", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Data saving failed
                    Toast.makeText(sign_up.this, "Failed to save user data to database", Toast.LENGTH_SHORT).show();
                });
    }
}
