package com.example.ropapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PatientInfo.class, Exam.class}, version = 9)
public abstract class PatientInfoDatabase extends RoomDatabase
{

    public abstract PatientInfoDao patientInfoDao();

    public abstract ExamDao examDao();

}
