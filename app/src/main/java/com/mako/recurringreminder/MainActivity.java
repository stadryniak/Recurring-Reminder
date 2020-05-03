package com.mako.recurringreminder;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ReminderViewModel mReminderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        fragmentTransaction.add(R.id.frame, fragment);
        fragmentTransaction.commit();

        setContentView(R.layout.activity_main);
        // button
        FloatingActionButton fab = findViewById(R.id.add_floating_button);
        fab.setOnClickListener(this::switchToOtherFragment);
       // mReminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);
        mReminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);
        mReminderViewModel.setLiveData(getApplication());
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
        transaction.replace(R.id.frame, newFragment, "ADD_FRAGMENT");
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
        FloatingActionButton fab = findViewById(R.id.add_floating_button);
    }
}
