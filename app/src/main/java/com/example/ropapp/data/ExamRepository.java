package com.example.ropapp.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

public class ExamRepository
{

    private final ExamDao examDao;

    public ExamRepository(ExamDao d)
    {
        examDao = d;
    }

    public void newExam(Exam exam)
    {
        examDao.newExam(exam);
    }

    public void deleteExam(Exam exam)
    {
        examDao.delExam(exam);
    }

    public LiveData<List<Exam>> viewPatientHistory(String pkey)
    {
        return examDao.getPatientsExams(pkey);
    }

    public LiveData<Exam> viewOneExam(String time)
    {
        return examDao.getExam(time);
    }
}
