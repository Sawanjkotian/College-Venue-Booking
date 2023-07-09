package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bookings extends AppCompatActivity {

    private LinearLayout bookingsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        bookingsLayout = findViewById(R.id.bookingsLayout);

        // Get the current user's ID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        // Get a reference to the database path where the user's bookings are stored
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("venues");

        // Read the data from the database
        bookingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookingsLayout.removeAllViews(); // Clear the existing views before populating

                for (DataSnapshot venueSnapshot : dataSnapshot.getChildren()) {
                    String venueName = venueSnapshot.getKey();

                    for (DataSnapshot dateSnapshot : venueSnapshot.getChildren()) {
                        String date = dateSnapshot.getKey();

                        for (DataSnapshot slotSnapshot : dateSnapshot.getChildren()) {
                            String slot = slotSnapshot.getKey();

                            for (DataSnapshot bookingSnapshot : slotSnapshot.getChildren()) {
                                String userId = bookingSnapshot.getKey();
                                String eventName = bookingSnapshot.child("eventName").getValue(String.class);
                                String eventType = bookingSnapshot.child("eventype").getValue(String.class);
                                String purpose = bookingSnapshot.child("purpose").getValue(String.class);

                                // Inflate the booking_item.xml layout
                                View bookingView = LayoutInflater.from(Bookings.this).inflate(R.layout.booking_item, null);

                                // Set the values for the booking view
                                TextView textViewDate = bookingView.findViewById(R.id.textViewDate);
                                TextView eventNameTextView = bookingView.findViewById(R.id.textViewEventName);
                                TextView slotTextView = bookingView.findViewById(R.id.textViewSlot);
                                TextView venueTextView = bookingView.findViewById(R.id.textViewVenue);

                                textViewDate.setText(date);
                                eventNameTextView.setText(eventName);
                                slotTextView.setText(slot);
                                venueTextView.setText(venueName);

                                // Add the booking view to the bookingsLayout
                                bookingsLayout.addView(bookingView);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur during the database operation
                Log.e("Bookings", "Error fetching booking details: " + databaseError.getMessage());
            }
        });
    }
}
