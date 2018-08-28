package com.example.ropapp.dependancyinjection;

import android.app.Application;

import com.example.ropapp.RecordDisplay.PatientList;
import com.example.ropapp.exam.ExamView;
import com.example.ropapp.exam.ExamList;
import com.example.ropapp.exam.NewExam;
import com.example.ropapp.NewPatient;
import com.example.ropapp.PatientView;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {

    void inject(PatientList patientList);
    void inject(PatientView patientView);
    void inject(NewPatient newPatient);
    void inject(NewExam newExam);
    void inject(ExamView examDetails);
    void inject(ExamList examList);

    Application application();

}
