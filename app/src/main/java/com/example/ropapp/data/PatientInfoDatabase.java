package com.example.ropapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {PatientInfo.class}, version = 1)
public abstract class PatientInfoDatabase extends RoomDatabase
{

    public abstract PatientInfoDao patientInfoDao();

}
