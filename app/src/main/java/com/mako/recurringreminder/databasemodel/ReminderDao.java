package com.mako.recurringreminder.databasemodel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReminderDao {
    @Query("SELECT * FROM reminder")
    List<Reminder> getAll();

    @Query("SELECT * FROM reminder WHERE id IN (:reminderIds)")
    List<Reminder> loadAllByIds(int[] reminderIds);

    @Query("SELECT * FROM reminder WHERE message LIKE :message LIMIT 1")
    Reminder findByMessage(String message);

    @Insert
    void insertAll(Reminder... users);

    @Delete
    void delete(Reminder reminder);
}
