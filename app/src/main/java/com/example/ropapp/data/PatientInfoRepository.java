package com.example.ropapp.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

public class PatientInfoRepository
{

    private final PatientInfoDao patientInfoDao;

    public PatientInfoRepository(PatientInfoDao patientInfoDao)
    {
        this.patientInfoDao = patientInfoDao;
    }

    public LiveData<List<PatientInfo>> getAllPatients()
    {
        return patientInfoDao.getAllPatientInfo();
    }

    public LiveData<PatientInfo> getPatient(String patientName)
    {
        return patientInfoDao.getPatient(patientName);
    }

    public void deletePatient(PatientInfo patient)
    {
        patientInfoDao.deletePatient(patient);
    }

    public void newPatient(PatientInfo patient)
    {
        patientInfoDao.insertNewPatient(patient);
    }
}
