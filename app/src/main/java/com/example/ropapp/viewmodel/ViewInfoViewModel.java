package com.example.ropapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.data.PatientInfoRepository;

public class ViewInfoViewModel extends ViewModel
{
    private PatientInfoRepository repository;

    public ViewInfoViewModel(PatientInfoRepository repository) {
        this.repository = repository;
    }

    public LiveData<PatientInfo> viewPatient(String date)
    {
        return repository.getPatient(date);
    }
}
