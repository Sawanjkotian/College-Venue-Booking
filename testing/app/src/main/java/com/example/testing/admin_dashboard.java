package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class admin_dashboard extends AppCompatActivity {

    TextView netravati_auditorium, ground_floor_seminar_hall, first_floor_seminar_hall, second_floor_seminar_hall,front_ground, back_ground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        netravati_auditorium = findViewById(R.id.netravati_auditorium);
        ground_floor_seminar_hall = findViewById(R.id.ground_floor_seminar_hall);
        first_floor_seminar_hall = findViewById(R.id.first_floor_seminar_hall);
        second_floor_seminar_hall = findViewById(R.id.second_floor_seminar_hall);
        front_ground = findViewById(R.id.front_ground);
        back_ground = findViewById(R.id.back_ground);

        netravati_auditorium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_dashboard.this, Netravati_Events.class);
                startActivity(intent);
            }
        });

        ground_floor_seminar_hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_dashboard.this, Netravati_Events.class);
                startActivity(intent);
            }
        });

        first_floor_seminar_hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_dashboard.this, first_floor.class);
                startActivity(intent);
            }
        });

        second_floor_seminar_hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_dashboard.this, second_floor.class);
                startActivity(intent);
            }
        });

        front_ground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_dashboard.this, front_ground.class);
                startActivity(intent);
            }
        });

        back_ground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_dashboard.this, back_ground.class);
                startActivity(intent);
            }
        });





    }
}