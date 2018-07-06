package com.example.ropapp.dependancyinjection;

import android.app.Application;

import com.example.ropapp.ROPApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule
{
    private final ROPApplication application;
    public ApplicationModule(ROPApplication application) {
        this.application = application;
    }

    @Provides
    ROPApplication provideRoomDemoApplication(){
        return application;
    }

    @Provides
    Application provideApplication(){
        return application;
    }
}
