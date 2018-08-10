package com.example.ropapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PatientInfoDao
{
    @Query("SELECT * FROM PatientInfo")
    LiveData<List<PatientInfo>> getAllPatientInfo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertNewPatient(PatientInfo newInfo);

    @Query("SELECT * FROM PatientInfo WHERE date = :date")
    LiveData<PatientInfo> getPatient(String date);

    @Delete
    void deletePatient(PatientInfo toDelete);
}
