package com.example.ropapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ropapp.RecordDisplay.Records;

public class MainActivity extends AppCompatActivity
{
    Button records, newPatient;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        records = findViewById(R.id.patients);
        newPatient = findViewById(R.id.npatient);

        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPatient = new Intent(getApplicationContext(), NewPatient.class);
                startActivity(addPatient);
            }
        });

        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewPatients = new Intent(getApplicationContext(), Records.class);
                startActivity(viewPatients);
            }
        });
    }
}
