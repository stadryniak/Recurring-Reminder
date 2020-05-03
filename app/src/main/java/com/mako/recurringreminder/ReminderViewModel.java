package com.mako.recurringreminder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import androidx.lifecycle.ViewModelStoreOwner;

import com.mako.recurringreminder.databasemodel.Reminder;
import com.mako.recurringreminder.databasemodel.ReminderRepository;

import java.util.List;

class ReminderViewModel extends ViewModel {
    private ReminderRepository mRepository;
    private LiveData<List<Reminder>> mAllReminders;

    void setLiveData(Application application) {
        if(mRepository!=null) return;
        mRepository = new ReminderRepository(application);
        mAllReminders = mRepository.getAllReminders();
    }

    LiveData<List<Reminder>> getAllReminders() {
        return mAllReminders;
    }

    void insert(Reminder reminder) {
        mRepository.insert(reminder);
    }
}
