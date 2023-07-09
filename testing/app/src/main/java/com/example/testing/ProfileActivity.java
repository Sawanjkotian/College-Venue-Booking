package com.example.testing;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {

    private TextView usernameTextView, phoneTextView, emailTextView;
    private EditText usernameEditText, phoneEditText, emailEditText;
    private Button editButton, updateButton, resetEmailButton;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Get current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Get references to views
        usernameTextView = findViewById(R.id.usernameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        emailTextView = findViewById(R.id.emailTextView);
        usernameEditText = findViewById(R.id.usernameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        editButton = findViewById(R.id.editButton);
        updateButton = findViewById(R.id.updateButton);
        resetEmailButton = findViewById(R.id.resetEmailButton);

        // Disable editing by default
        setEditingEnabled(false);

        // Fetch and display user data
        fetchUserData();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enable editing
                setEditingEnabled(true);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get updated user data
                String username = usernameEditText.getText().toString().trim();
                String phoneNumber = phoneEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();

                // Update user data in the database
                updateUserData(username, phoneNumber, email);
            }

        });

        resetEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void setEditingEnabled(boolean enabled) {
        usernameEditText.setEnabled(enabled);
        phoneEditText.setEnabled(enabled);
        emailEditText.setEnabled(enabled);
        updateButton.setEnabled(enabled);
    }

    private void fetchUserData() {
        // Check if the user is authenticated
        if (currentUser != null) {
            String uid = currentUser.getUid();
            databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            // Display user data
                            usernameTextView.setText(user.getName());
                            phoneTextView.setText(user.getPhoneNumber());
                            emailTextView.setText(user.getEmail());
                            // Set the EditText fields with the current user data
                            usernameEditText.setText(user.getName());
                            phoneEditText.setText(user.getPhoneNumber());
                            emailEditText.setText(user.getEmail());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                    Toast.makeText(ProfileActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateUserData(String username, String phoneNumber, String email) {
        // Check if the user is authenticated
        if (currentUser != null) {
            String uid = currentUser.getUid();
            User updatedUser = new User(username, phoneNumber, email);
            databaseReference.child(uid).setValue(updatedUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Data updated successfully
                            Toast.makeText(ProfileActivity.this, "User data updated", Toast.LENGTH_SHORT).show();
                            // Disable editing after updating
                            setEditingEnabled(false);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Data updating failed
                            Toast.makeText(ProfileActivity.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void resetPassword() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String emailAddress = user.getEmail();

            FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Password reset email sent successfully
                                Toast.makeText(ProfileActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            } else {
                                // Failed to send password reset email
                                Toast.makeText(ProfileActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }





}
