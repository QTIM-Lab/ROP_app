package com.example.ropapp.RecordDisplay;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.ropapp.MainActivity;
import com.example.ropapp.R;
import com.example.ropapp.ROPApplication;
import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.data.PatientInfoDao;
import com.example.ropapp.viewmodel.PatientListViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;

public class Records extends AppCompatActivity {

    private RecyclerView list;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> animalNames;

    private ArrayList<PatientInfo> listOfData;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    PatientListViewModel patientListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        ((ROPApplication) getApplication())
                .getApplicationComponent()
                .inject(this);


        patientListViewModel = ViewModelProviders.of(this, viewModelFactory).get(PatientListViewModel.class);

        patientListViewModel.getPatientList().observe(Records.this, new Observer<List<PatientInfo>>() {
            @Override
            public void onChanged(@Nullable List<PatientInfo> patientInfos) {
                //if(listOfData.size() == 0)
                updateList(patientInfos);


            }
        });


        animalNames = new ArrayList<>();
        listOfData = new ArrayList<>();


        FloatingActionButton f = findViewById(R.id.floatingActionButton);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientInfo p = new PatientInfo("Joe", "05/29/2018", "32", "Treat now", "path", "date", "notes", 12);
                patientListViewModel.NewPatient(p);
            }
        });

    }

    private void updateList(List<PatientInfo> p)
    {
        for(int i = 0; i < p.size(); i++)
            listOfData.add(0, p.get(i));

        animalNames.add("BREAK");

        list = findViewById(R.id.recyclerView);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(listOfData, this);

        list.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), 1);
        list.addItemDecoration(dividerItemDecoration);

    }



}
