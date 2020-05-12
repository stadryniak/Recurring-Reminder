package com.mako.recurringreminder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ReminderViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;


    ReminderViewModelFactory(Application application) {
        mApplication = application;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ReminderViewModel(mApplication);
    }
}
