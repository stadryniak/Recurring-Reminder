package com.mako.recurringreminder;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mako.recurringreminder.databasemodel.ReminderDao;
import com.mako.recurringreminder.databasemodel.ReminderDatabase;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        fragmentTransaction.add(R.id.fragment, recyclerViewFragment);
        fragmentTransaction.commit();

        setContentView(R.layout.activity_main);
        // button
        FloatingActionButton fab = findViewById(R.id.add_floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToOtherFragment(v);
            }
        });
        ReminderDatabase db = Room.databaseBuilder(getApplicationContext(), ReminderDatabase.class, "RemindersDb").build();
        ReminderDao reminderDao = db.reminderDao();
        db.close();
        // mAdapter = new RemindersAdapter(remindersDataset);
        // recyclerView.setAdapter(mAdapter);
    }

    void switchToOtherFragment(View view) {
        AddReminder newFragment = new AddReminder();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }
}
