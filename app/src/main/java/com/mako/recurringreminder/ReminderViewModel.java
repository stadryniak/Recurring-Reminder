package com.mako.recurringreminder;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mako.recurringreminder.databasemodel.Reminder;
import com.mako.recurringreminder.databasemodel.ReminderRepository;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {
    private ReminderRepository mRepository;
    private LiveData<List<Reminder>> mAllWords;

    public ReminderViewModel(Application application) {
        super(application);
        mRepository = new ReminderRepository(application);
        mAllWords = mRepository.getAllReminders();
    }

    LiveData<List<Reminder>> getAllWords() {
        return mAllWords;
    }

    public void insert(Reminder reminder) {
        mRepository.insert(reminder);
    }
}
