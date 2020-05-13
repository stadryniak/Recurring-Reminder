package com.mako.recurringreminder.databasemodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReminderRepository {
    private ReminderDao mReminderDao;
    private LiveData<List<Reminder>> mAllReminders;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ReminderRepository(Context context) {
        ReminderDatabase db = ReminderDatabase.getDatabase(context);
        mReminderDao = db.reminderDao();
        mAllReminders = mReminderDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Reminder>> getAllReminders() {
        return mAllReminders;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public int insert(Reminder reminder) {
        int id;
        try {
            Future<Long> promise = ReminderDatabase.databaseWriteExecutor.submit(() -> mReminderDao.insert(reminder));
            id = (int) promise.get().longValue();
        } catch (Exception e) {
            throw new RuntimeException("Insert fail");
        }
        return id;
    }

    public int delete(Reminder reminder) {
        int id;
        try {
            Future<Integer> promise = ReminderDatabase.databaseWriteExecutor.submit(() -> mReminderDao.delete(reminder));
            id = promise.get();
        } catch (Exception e) {
            throw new RuntimeException("Delete fail");
        }
        return id;
    }
}
