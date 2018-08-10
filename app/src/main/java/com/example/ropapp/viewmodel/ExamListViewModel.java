package com.example.ropapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.ropapp.data.Exam;
import com.example.ropapp.data.ExamRepository;

import java.util.List;

public class ExamListViewModel extends ViewModel
{

    private ExamRepository examRepository;

    public ExamListViewModel(ExamRepository examRepository)
    {
        this.examRepository = examRepository;
    }

    public LiveData<List<Exam>> getPatientsExams(String pkey)
    {
        return examRepository.viewPatientHistory(pkey);
    }

    public void deleteExam(Exam exam)
    {
        new ExamListViewModel.delete().execute(exam);
    }

    private class delete extends AsyncTask<Exam, Void, Void>
    {
        @Override
        protected Void doInBackground(Exam... item) {
            examRepository.deleteExam(item[0]);
            return null;
        }
    }

}
