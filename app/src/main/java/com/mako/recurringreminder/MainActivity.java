package com.mako.recurringreminder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mako.recurringreminder.databasemodel.Reminder;
import com.mako.recurringreminder.databasemodel.ReminderDao;
import com.mako.recurringreminder.databasemodel.ReminderDatabase;

import java.util.List;


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
        fab.setOnClickListener(this::switchToOtherFragment);
       // ReminderDatabase db =  ReminderDatabase.getDatabase(this);
       // db.close();
        // mAdapter = new RemindersAdapter(remindersDataset);
        // recyclerView.setAdapter(mAdapter);
    }

    void switchToOtherFragment(View view) {
        // check if add fragment is already here. if so, abort
        AddReminderFragment oldFragment = (AddReminderFragment) getSupportFragmentManager().findFragmentByTag("ADD_FRAGMENT");
        if (oldFragment != null && oldFragment.isVisible())
            return;

        AddReminderFragment newFragment = new AddReminderFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment, newFragment, "ADD_FRAGMENT");
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
        FloatingActionButton fab = findViewById(R.id.add_floating_button);
    }
}
