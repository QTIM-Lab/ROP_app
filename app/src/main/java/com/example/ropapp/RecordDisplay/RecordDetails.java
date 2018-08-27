package com.example.ropapp.RecordDisplay;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ropapp.R;
import com.example.ropapp.ROPApplication;
import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.viewmodel.ViewInfoViewModel;

import javax.inject.Inject;

public class RecordDetails extends AppCompatActivity {

    TextView write;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ViewInfoViewModel viewInfoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        ((ROPApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        write = findViewById(R.id.infoView);

        viewInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ViewInfoViewModel.class);


    }
}
