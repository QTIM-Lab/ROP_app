package com.example.ropapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.ropapp.data.PatientInfoRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory
{
    private final PatientInfoRepository repository;

    @Inject
    public CustomViewModelFactory(PatientInfoRepository repository) {
        this.repository = repository;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ViewInfoViewModel.class))
            return (T) new ViewInfoViewModel(repository);
        else if(modelClass.isAssignableFrom(PopupViewModel.class))
            return (T) new PopupViewModel(repository);
        else if(modelClass.isAssignableFrom(PatientListViewModel.class))
            return (T) new PatientListViewModel(repository);
        else
            throw new IllegalArgumentException("Viewmodel not found");
    }
}
