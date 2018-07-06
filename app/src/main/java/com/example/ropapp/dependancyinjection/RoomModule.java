package com.example.ropapp.dependancyinjection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.example.ropapp.data.PatientInfoDao;
import com.example.ropapp.data.PatientInfoDatabase;
import com.example.ropapp.data.PatientInfoRepository;
import com.example.ropapp.viewmodel.CustomViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule
{
    private final PatientInfoDatabase database;

    public RoomModule(Application application) {
        this.database = Room.databaseBuilder(
                application,
                PatientInfoDatabase.class,
                "PatientInfo.db"
        ).build();
    }

    @Provides
    @Singleton
    PatientInfoRepository provideListItemRepository(PatientInfoDao patientInfoDao){
        return new PatientInfoRepository(patientInfoDao);
    }

    @Provides
    @Singleton
    PatientInfoDao provideListItemDao(PatientInfoDatabase database){
        return database.patientInfoDao();
    }

    @Provides
    @Singleton
    PatientInfoDatabase provideListItemDatabase(Application application){
        return database;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(PatientInfoRepository repository){
        return new CustomViewModelFactory(repository);
    }
}
