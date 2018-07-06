package com.example.ropapp.dependancyinjection;

import android.app.Application;
import android.support.v4.app.ListFragment;

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

    Application application();

}
