package com.mako.recurringreminder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mako.recurringreminder.databasemodel.Reminder;

import java.util.Calendar;

class ReminderNotificationManager {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Reminder reminder;

    ReminderNotificationManager(Context context, Reminder reminder) {
        if (reminder == null || context == null) throw new NullPointerException();
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("message", reminder.getMessage());
        intent.putExtra("id", reminder.id);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.reminder = reminder;
        if(alarmMgr == null || alarmIntent==null) throw new NullPointerException("Error getting alarm manager or pending intent");
    }

    void setRepeatingNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, reminder.getStartHour());
        calendar.set(Calendar.MINUTE, reminder.getStartMinute());
        alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), reminder.getMilisec(), alarmIntent);
    }

    void deleteRepeatingNotification(){
        alarmMgr.cancel(alarmIntent);
    }

}