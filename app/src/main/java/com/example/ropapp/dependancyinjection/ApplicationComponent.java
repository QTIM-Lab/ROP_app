package com.example.ropapp.dependancyinjection;

import android.app.Application;

import com.example.ropapp.exam.ExamDetails;
import com.example.ropapp.exam.ExamRecyclerView;
import com.example.ropapp.exam.NewExam;
import com.example.ropapp.NewPatient;
import com.example.ropapp.Popup;
import com.example.ropapp.RecordDisplay.RecordDetails;
import com.example.ropapp.RecordDisplay.Records;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {

    void inject(RecordDetails recordDetails);
    void inject(Records records);
    void inject(Popup popup);
    void inject(NewPatient newPatient);
    void inject(NewExam newExam);
    void inject(ExamDetails examDetails);
    void inject(ExamRecyclerView examRecyclerView);

    Application application();

}
