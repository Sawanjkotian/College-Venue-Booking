package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Netravati_Events extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netravati_events);

        // Assuming you have a reference to your Firebase database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        // Get reference to the venueNetravati LinearLayout
        LinearLayout venueNetravatiLayout = findViewById(R.id.venueNetravati);

        // Retrieve the bookings for the Netravati venue
        DatabaseReference venueBookingsRef = databaseRef.child("venues").child("Netravati Auditorium");
        venueBookingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterate through the bookings
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String date = dateSnapshot.getKey();
                    for (DataSnapshot timeSnapshot : dateSnapshot.getChildren()) {
                        String time = timeSnapshot.getKey();
                        for (DataSnapshot userSnapshot : timeSnapshot.getChildren()) {
                            String userId = userSnapshot.getKey();
                            String eventName = userSnapshot.child("eventName").getValue(String.class);
                            String eventType = userSnapshot.child("eventType").getValue(String.class);
                            String purpose = userSnapshot.child("purpose").getValue(String.class);

                            // Retrieve user information from "users" node
                            DatabaseReference userRef = databaseRef.child("users").child(userId);
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String userName = dataSnapshot.child("name").getValue(String.class);
                                    String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);

                                    // Inflate the venue_item.xml layout for each booking
                                    View venueItemView = getLayoutInflater().inflate(R.layout.venue_item, null);

                                    // Get references to the TextViews in the venue_item.xml layout
                                    TextView textViewDate = venueItemView.findViewById(R.id.textViewDate);
                                    TextView textViewEventName = venueItemView.findViewById(R.id.textViewEventName);
                                    TextView textViewSlot = venueItemView.findViewById(R.id.textViewSlot);
                                    TextView textViewUser = venueItemView.findViewById(R.id.bookeduser);
                                    TextView textViewPhone = venueItemView.findViewById(R.id.bookeduserphone);

                                    // Set the fetched values to the TextViews
                                    textViewDate.setText(date);
                                    textViewEventName.setText(eventName);
                                    textViewSlot.setText(time);
                                    textViewUser.setText(userName);
                                    textViewPhone.setText(phoneNumber);

                                    // Add the populated venue_item.xml layout to the venueNetravati LinearLayout
                                    venueNetravatiLayout.addView(venueItemView);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle any errors during database retrieval
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors during database retrieval
            }
        });
    }
}
