package com.mako.recurringreminder;

import android.app.Application;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mako.recurringreminder.databasemodel.Reminder;
import com.mako.recurringreminder.databasemodel.ReminderDatabase;
import com.mako.recurringreminder.databasemodel.ReminderRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            ReminderDatabase db = ReminderDatabase.getDatabase(context);
            try {
                Future<List<Reminder>> promise = ReminderDatabase.databaseWriteExecutor.submit(() -> db.reminderDao().getAllNoLiveData());
                List<Reminder> reminders = promise.get();
                for(Reminder re : reminders){
                    ReminderNotificationManager notificationManager = new ReminderNotificationManager(context, re);
                    notificationManager.setRepeatingNotification(true);
                }
            } catch (Exception e) {
                Log.d("RecurringReminder", "Db access on boot failed");
                db.close();
                throw new RuntimeException("Delete fail");
            }
            db.close();
        }
    }
}
