package com.mako.recurringreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import com.mako.recurringreminder.databasemodel.Reminder;
import com.mako.recurringreminder.databasemodel.ReminderDao;
import com.mako.recurringreminder.databasemodel.ReminderDatabase;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.MainRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ReminderDatabase db = Room.databaseBuilder(getApplicationContext(), ReminderDatabase.class, "RemindersDb").build();
        ReminderDao reminderDao = db.reminderDao();
        db.close();
        // mAdapter = new RemindersAdapter(remindersDataset);
        // recyclerView.setAdapter(mAdapter);
    }
}
