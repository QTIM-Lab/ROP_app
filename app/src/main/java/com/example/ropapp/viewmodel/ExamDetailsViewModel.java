package com.example.ropapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.ropapp.data.Exam;
import com.example.ropapp.data.ExamRepository;

public class ExamDetailsViewModel extends ViewModel
{
    private ExamRepository repository;

    public ExamDetailsViewModel(ExamRepository repository) { this.repository = repository; }

    public LiveData<Exam> ViewExam(String key)
    {
        return repository.viewOneExam(key);
    }
}
