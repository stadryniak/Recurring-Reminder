package com.mako.recurringreminder;

import android.app.Application;
import android.content.Context;
import android.util.Log;

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

@SuppressWarnings("WeakerAccess") // less accessible than public breaks UI
public class ReminderViewModel extends AndroidViewModel {
    private ReminderRepository mRepository;
    private LiveData<List<Reminder>> mAllReminders;

    public ReminderViewModel(@NonNull Application application) {
        super(application);
    }

    void setLiveData(Application application) {
        if (mRepository != null) return;
        mRepository = new ReminderRepository(application);
        mAllReminders = mRepository.getAllReminders();
    }

    LiveData<List<Reminder>> getAllReminders() {
        return mAllReminders;
    }

    int insert(Reminder reminder) {
        return mRepository.insert(reminder);
    }

    int delete(Reminder reminder) {
        ReminderNotificationManager notificationManager = new ReminderNotificationManager(this.getApplication(), reminder);
        notificationManager.deleteRepeatingNotification();
        return mRepository.delete(reminder);
    }
}

