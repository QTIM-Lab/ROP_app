package com.example.ropapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.view.View;

import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.data.PatientInfoRepository;

public class PopupViewModel extends ViewModel
{
    private PatientInfoRepository repository;

    public PopupViewModel(PatientInfoRepository repository) {
        this.repository = repository;
    }

    public void NewPatient(PatientInfo patient)
    {
        new AddPatient().execute(patient);
    }
    private class AddPatient extends AsyncTask<PatientInfo, Void, Void>
    {
        @Override
        protected Void doInBackground(PatientInfo... item) {
            repository.newPatient(item[0]);
            return null;
        }
    }


}
