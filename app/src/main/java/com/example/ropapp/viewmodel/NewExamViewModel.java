package com.example.ropapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.ropapp.data.Exam;
import com.example.ropapp.data.ExamRepository;
import com.example.ropapp.data.PatientInfo;
import com.example.ropapp.data.PatientInfoRepository;

public class NewExamViewModel extends ViewModel
{
    private ExamRepository repository;
    private PatientInfoRepository patientInfoRepository;

    public NewExamViewModel (ExamRepository repository, PatientInfoRepository patientInfoRepository)
    {
        this.repository = repository;
        this.patientInfoRepository = patientInfoRepository;
    }

    public void NewExam(Exam exam)
    {
        new NewExamViewModel.AddExam().execute(exam);
    }

    private class AddExam extends AsyncTask<Exam, Void, Void>
    {
        @Override
        protected Void doInBackground(Exam... item)
        {
            repository.newExam(item[0]);
            return null;
        }
    }

    public LiveData<PatientInfo> viewPatient(String date)
    {
        return patientInfoRepository.getPatient(date);
    }

}
