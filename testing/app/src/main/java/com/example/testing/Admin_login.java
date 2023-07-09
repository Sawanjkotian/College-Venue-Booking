package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_login extends AppCompatActivity {

    Button adminLoginbtn;
    EditText adminphoneNo, adminPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminphoneNo = findViewById(R.id.phone);
        adminPassword = findViewById(R.id.passwordSign);
        adminLoginbtn = findViewById(R.id.adminLogin);


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://testing-9fbbc-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Admin");

        adminLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_login.this, admin_dashboard.class);
                startActivity(intent);
            }
        });


    }
}