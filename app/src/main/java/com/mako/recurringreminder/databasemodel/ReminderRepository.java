package com.mako.recurringreminder.databasemodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReminderRepository {
    private ReminderDao mReminderDao;
    private LiveData<List<Reminder>> mAllReminders;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    ReminderRepository(Application application) {
        ReminderDatabase db = ReminderDatabase.getDatabase(application);
        mReminderDao = db.reminderDao();
        mAllReminders = mReminderDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Reminder>> getAllWords() {
        return mAllReminders;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Reminder word) {
        ReminderDatabase.databaseWriteExecutor.execute(() -> mReminderDao.insertAll(word));
    }
}