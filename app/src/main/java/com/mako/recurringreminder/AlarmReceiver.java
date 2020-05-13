package com.mako.recurringreminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Dao;

import com.mako.recurringreminder.databasemodel.Reminder;
import com.mako.recurringreminder.databasemodel.ReminderDatabase;
import com.mako.recurringreminder.databasemodel.ReminderRepository;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "REMINDER_NOTIFICATION_CHANNEL_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            errorToast(context, "Error creating notification");
            return;
        }
        createNotificationChannel(context);
        if (intent.hasExtra("message") &&
                intent.hasExtra("id") &&
                intent.hasExtra("minutes") &&
                intent.hasExtra("hours") &&
                intent.hasExtra("days")) {
            int id = intent.getIntExtra("id", -1);
            if (id == -1) {
                errorToast(context, "Error getting id");
                return;
            }

            String contentText = String.format(Locale.getDefault(), "Interval: %d days, %d hours, %d minutes",
                    intent.getIntExtra("days", 0),
                    intent.getIntExtra("hours", 0),
                    intent.getIntExtra("minutes", 0));

            String message = intent.getStringExtra("message");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(message)
                    .setContentText(contentText)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            notificationManager.notify(id, builder.build());
        }
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
            //errorToast(context, "Magic in progress...");
        }
    }

    void errorToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
