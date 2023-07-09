package com.example.testing;

import static com.example.testing.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class calendar extends AppCompatActivity {

    private List<String> timeSlots;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinnerTimings;
    private TextView textViewSelectedDate;
    private Button profileButton, Bookingsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_calendar);
        profileButton = findViewById(R.id.profileButton);
        Bookingsbtn = findViewById(R.id.Bookingsbtn);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(calendar.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        Bookingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(calendar.this, Bookings.class);
                startActivity(intent);
            }
        });


    }

    public void showAddEventDialog(View view) {
        AddEventDialog.show(this);
    }

    private List<String> generateTimeSlots() {
        List<String> slots = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

        while (calendar.get(Calendar.HOUR_OF_DAY) < 18) {
            String timeSlot = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
            slots.add(timeSlot);
            calendar.add(Calendar.HOUR, 2);
        }

        return slots;
    }

}
