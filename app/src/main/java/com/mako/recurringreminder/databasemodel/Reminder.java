package com.mako.recurringreminder.databasemodel;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Reminder {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "interval_days")
    private int intervalDays;
    @ColumnInfo(name = "interval_hours")
    private int intervalHours;
    @ColumnInfo(name = "interval_minutes")
    private int intervalMinutes;
    @ColumnInfo(name = "start_hour")
    private int startHour;
    @ColumnInfo(name = "start_minute")
    private int startMinute;
    @ColumnInfo(name = "message")
    private String message;

    Reminder(int startHour, int startMinute, int intervalDays, int intervalHours, int intervalMinutes, String message) {
        // check inputs
        if ((startHour > 23 || startHour < 0) || (startMinute > 59 || startMinute < 0))
            throw new IllegalArgumentException("Invalid start time");
        if ((intervalDays < 0 || intervalHours < 0 || intervalMinutes < 0) || (intervalDays == 0 && intervalHours == 0 && intervalMinutes == 0))
            throw new IllegalArgumentException("Invalid interval");
        if (message == null) throw new NullPointerException("Message is null");
        message = message.trim();
        if (message.isEmpty()) throw new IllegalArgumentException("Message is empty");
        // set fields
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.intervalDays = intervalDays;
        this.intervalMinutes = intervalMinutes;
        this.intervalHours = intervalHours;
        this.message = message;
    }

    int getStartHour() {
        return startHour;
    }

    int getStartMinute() {
        return startMinute;
    }

    int getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(int days) {
        if (days > 31 || days < 0)
            throw new IllegalArgumentException("Days must be in 0-31 range");
        this.intervalDays = days;
    }

    int getIntervalHours() {
        return intervalHours;
    }

    public void setIntervalHours(int hours) {
        if (hours > 23 || hours < 0)
            throw new IllegalArgumentException("Hours must be in 0-23 range");
        this.intervalHours = hours;
    }

    int getIntervalMinutes() {
        return intervalMinutes;
    }

    public void setIntervalMinutes(int minutes) {
        if (minutes > 59 || minutes < 0)
            throw new IllegalArgumentException("Minutes must be in 0-59 range");
        this.intervalMinutes = minutes;
    }

    String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
