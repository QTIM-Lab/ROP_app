package com.example.ropapp.RecordDisplay;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import com.example.ropapp.R;
import com.example.ropapp.ROPApplication;
import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.viewmodel.PatientListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PatientList extends AppCompatActivity {

    private RecyclerView list;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;

    private ArrayList<PatientInfo> listOfData;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    PatientListViewModel patientListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        ((ROPApplication) getApplication())
                .getApplicationComponent()
                .inject(this);


        patientListViewModel = ViewModelProviders.of(this, viewModelFactory).get(PatientListViewModel.class);

        patientListViewModel.getPatientList().observe(PatientList.this, new Observer<List<PatientInfo>>() {
            @Override
            public void onChanged(@Nullable List<PatientInfo> patientInfos) {
                updateList(patientInfos);


            }
        });

        listOfData = new ArrayList<>();

    }

    private void updateList(List<PatientInfo> p)
    {
        for(int i = 0; i < p.size(); i++)
            listOfData.add(0, p.get(i));


        list = findViewById(R.id.recyclerView);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(listOfData, this);


        list.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), 1);
        list.addItemDecoration(dividerItemDecoration);


    }
}
