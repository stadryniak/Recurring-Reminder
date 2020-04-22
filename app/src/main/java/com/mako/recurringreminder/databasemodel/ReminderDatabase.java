package com.mako.recurringreminder.databasemodel;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Reminder.class}, version = 1)
public abstract class ReminderDatabase extends RoomDatabase {
    public abstract ReminderDao reminderDao();
}
