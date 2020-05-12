package com.mako.recurringreminder.databasemodel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReminderDao {
    @Query("SELECT * FROM reminder_table ORDER BY id ASC")
    LiveData<List<Reminder>> getAll();

    @Query("SELECT * FROM reminder_table WHERE id IN (:reminderIds)")
    List<Reminder> loadAllByIds(int[] reminderIds);

    @Query("SELECT * FROM reminder_table WHERE message LIKE :message LIMIT 1")
    Reminder findByMessage(String message);

    @Update
    void updateAll(Reminder[] reminders);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Reminder reminders);

    @Delete
    int delete(Reminder reminder);
}
