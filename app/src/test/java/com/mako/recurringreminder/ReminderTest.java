package com.mako.recurringreminder;

import com.mako.recurringreminder.databasemodel.Reminder;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ReminderTest {
    @Test
    public void getMilisec_isCorrect() {
        int[] hours = new int[]{1, 0, 0, 2};
        int[] minutes = new int[]{0, 1, 0, 3};
        int[] days = new int[]{0, 0, 1, 4};
        int[] expected = new int[]{3600000, 60000, 86400000, 352980000};
        for (int i = 0; i < hours.length; i++) {
            Reminder reminder = new Reminder(0, 0, days[i], hours[i], minutes[i], "test");
            assertEquals(expected[i], reminder.getMilisec());
        }
    }
}