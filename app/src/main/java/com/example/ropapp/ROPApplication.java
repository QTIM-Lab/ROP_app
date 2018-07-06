package com.example.ropapp;

import android.app.Application;

import com.example.ropapp.dependancyinjection.ApplicationComponent;
import com.example.ropapp.dependancyinjection.ApplicationModule;
import com.example.ropapp.dependancyinjection.DaggerApplicationComponent;
import com.example.ropapp.dependancyinjection.RoomModule;

public class ROPApplication extends Application
{

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
