package com.example.ropapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExamDao
{

    @Insert
    void newExam(Exam nExam);

    @Delete
    void delExam(Exam toDelete);

    @Query("SELECT * FROM Exam WHERE pkey=:pkey")
    LiveData<List<Exam>> getPatientsExams(String pkey);

    @Query("SELECT * FROM Exam WHERE date = :date")
    LiveData<Exam> getExam(String date);

}
