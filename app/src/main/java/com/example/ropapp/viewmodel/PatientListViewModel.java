package com.example.ropapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.data.PatientInfoRepository;

import java.util.List;

public class PatientListViewModel extends ViewModel
{

    private PatientInfoRepository repository;

    public PatientListViewModel(PatientInfoRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<PatientInfo>> getPatientList()
    {
        return repository.getAllPatients();
    }

    public void deletePatient(PatientInfo patient)
    {
        DeleteItemTask del = new DeleteItemTask();
        del.execute(patient);
    }

    private class DeleteItemTask extends AsyncTask<PatientInfo, Void, Void> {

        @Override
        protected Void doInBackground(PatientInfo... item) {
            repository.deletePatient(item[0]);
            return null;
        }
    }

    public void NewPatient(PatientInfo patient)
    {
        new PatientListViewModel.AddPatient().execute(patient);
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
