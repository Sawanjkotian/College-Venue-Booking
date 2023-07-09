package com.example.testing;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddEventDialog {
    //sawan9kotian@gmail.com
    public static void show(Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_event, null);
        dialogBuilder.setView(dialogView);
        final EditText editTextEventName = dialogView.findViewById(R.id.editTextEventName);
        final EditText editTextPurpose = dialogView.findViewById(R.id.editTextPurpose);
        final RadioGroup radioGroupEventType = dialogView.findViewById(R.id.radioGroupEventType);
        final Spinner spinnerEventVenue = dialogView.findViewById(R.id.spinnerEventVenue);
        final TextView editTextSelectedDate = dialogView.findViewById(R.id.textViewSelectedDate);

        // Generate and set up the spinner for time slot
        List<String> timeSlots = generateTimeSlots();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, timeSlots);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinnerTimings = dialogView.findViewById(R.id.spinnerTimings);
        spinnerTimings.setAdapter(spinnerAdapter);

        List<String> venueSlots = generateVenueSlots();
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, venueSlots);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEventVenue.setAdapter(spinnerAdapter1);

        // Set the selected date to the current date
//        String currentDate = getCurrentDate();
//        editTextSelectedDate.setText(currentDate);

        String currentDate = getCurrentDate();
        editTextSelectedDate.setText(currentDate);
        final Calendar selectedCalendar = Calendar.getInstance();
        editTextSelectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(context, selectedCalendar, editTextSelectedDate);
            }
        });


        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle positive button click
                // Get the selected values from the dialog and perform actions
                String eventName = editTextEventName.getText().toString();
                String purpose = editTextPurpose.getText().toString();
                int eventType = radioGroupEventType.getCheckedRadioButtonId();
                String selectedTimings = spinnerTimings.getSelectedItem().toString();
                String selectedVenue = spinnerEventVenue.getSelectedItem().toString();
                String selectedDate = editTextSelectedDate.getText().toString();


                DatabaseReference venueRef = FirebaseDatabase.getInstance().getReference("venues").child(selectedVenue);
                DatabaseReference dateRef = venueRef.child(selectedDate);

                dateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.child(selectedTimings).exists()) {
                                Toast.makeText(context, "SLOT NOT AVAILABLE", Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String userId = user.getUid();
                                dateRef.child(selectedTimings).setValue(userId);
                            }
                        } else {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = user.getUid();
                            dateRef.setValue(userId);
                            String eventtype;
                            dateRef.child(selectedTimings).setValue(userId);
                            dateRef.child(selectedTimings).child(userId).child("eventName").setValue(eventName);
                            dateRef.child(selectedTimings).child(userId).child("purpose").setValue(purpose);
                            if(eventType==1){
                                eventtype ="teacher";
                            }
                            else eventtype ="student";
                           dateRef.child(selectedTimings).child(userId).child("eventype").setValue(eventtype);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {



                    }
                });



            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle negative button click or dismiss the dialog
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
//
    private static void showDatePickerDialog(Context context, Calendar selectedCalendar, final TextView editTextSelectedDate) {
        int year = selectedCalendar.get(Calendar.YEAR);
        int month = selectedCalendar.get(Calendar.MONTH);
        int dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedCalendar.set(year, month, dayOfMonth);
                String selectedDate = formatDate(selectedCalendar.getTime());
                editTextSelectedDate.setText(selectedDate);
            }
        }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    private static List<String> generateTimeSlots() {
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

    private static List<String> generateVenueSlots() {
        List<String> slots = new ArrayList<>();
        slots.add("Netravati Auditorium");
        slots.add("Ground floor seminar hall");
        slots.add("First floor seminar hall");
        slots.add("Second floor seminar hall");
        slots.add("Front ground");
        slots.add("Back ground");
        // Add more venue slots as needed

        return slots;
    }

    private static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    private static String formatDate(java.util.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }



}
