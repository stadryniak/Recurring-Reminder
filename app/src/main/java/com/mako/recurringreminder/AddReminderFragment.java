package com.mako.recurringreminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mako.recurringreminder.databasemodel.Reminder;
import com.mako.recurringreminder.databasemodel.ReminderDao;
import com.mako.recurringreminder.databasemodel.ReminderDatabase;

import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddReminderFragment extends Fragment {
    private ReminderViewModel mReminderViewModel;
    private int startHour;
    private int startMinute;
    private int intervalDay;
    private int intervalHour;
    private int intervalMinute;

    private boolean startToday = false;

    private int TIME_PICKER_DIALOG_REQUEST_CODE = 71;
    private int NUMBER_PICKER_DIALOG_REQUEST_CODE = 75;

    public AddReminderFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment add_reminder.
     */
    private static AddReminderFragment newInstance() {
        return new AddReminderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_reminder, container, false);
        // set buttons
        // start time set
        TextView currentStartTime = view.findViewById(R.id.currentStartTime);
        currentStartTime.setOnClickListener(this::showTimePickerDialog);
        // intervals set onclick
        TextView intervalText = view.findViewById(R.id.currentDays);
        intervalText.setOnClickListener(v -> showNumberPickerDialog(v, 0));
        intervalText = view.findViewById(R.id.currentHours);
        intervalText.setOnClickListener(v -> showNumberPickerDialog(v, 1));
        intervalText = view.findViewById(R.id.currentMinutes);
        intervalText.setOnClickListener(v -> showNumberPickerDialog(v, 2));

        CheckBox startTodayCheckbox = view.findViewById(R.id.startTodayCheckbox);
        startTodayCheckbox.setOnClickListener(this::onCheckboxClicked);

        Button addButton = view.findViewById(R.id.addToDbButton);
        addButton.setOnClickListener(this::addToDb);

        mReminderViewModel = new ViewModelProvider(this, new ReminderViewModelFactory(Objects.requireNonNull(getActivity()).getApplication())).get(ReminderViewModel.class);
        mReminderViewModel.setLiveData(Objects.requireNonNull(getActivity()).getApplication());
        return view;
    }

    private void addToDb(View v) {
        View view = getView();
        assert view != null;
        EditText editText = view.findViewById(R.id.messageInput);
        String message = editText.getText().toString();
        if (message.isEmpty()) {
            Toast toast = Toast.makeText(getContext(), "Message cannot be empty", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if (this.intervalDay == 0 && this.intervalHour == 0 && this.intervalMinute == 0) {
            Toast toast = Toast.makeText(getContext(), "Interval cannot be 0", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Reminder reminder = new Reminder(this.startHour, this.startMinute, this.intervalDay, this.intervalHour, this.intervalMinute, message);
        reminder.id = mReminderViewModel.insert(reminder);
        // set reminder notification
        ReminderNotificationManager notificationManager = new ReminderNotificationManager(getContext(), reminder);
        notificationManager.setRepeatingNotification(this.startToday);

        goBack();
    }

    private void goBack() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null)
            fragmentManager.popBackStack();
    }

    private void showNumberPickerDialog(View v, int requestId) {
        NumberPickerFragment pickerFragment = NumberPickerFragment.newInstance(requestId);
        pickerFragment.setTargetFragment(this, this.NUMBER_PICKER_DIALOG_REQUEST_CODE);
        assert getFragmentManager() != null;
        pickerFragment.show(getFragmentManager(), "numberPicker");
    }

    private void showTimePickerDialog(View v) {
        DialogFragment pickerFragment = new TimePickerFragment();
        assert getFragmentManager() != null;
        pickerFragment.setTargetFragment(this, this.TIME_PICKER_DIALOG_REQUEST_CODE);
        pickerFragment.show(getFragmentManager(), "timePicker");
    }

    private void setStartTimeText() {
        View view = getView();
        if (view == null) return;
        TextView text = view.findViewById(R.id.currentStartTime);
        text.setText(String.format(Locale.getDefault(), "%02d:%02d", this.startHour, this.startMinute));
    }

    private void setIntervalTexts() {
        View view = getView();
        if (view == null) return;
        TextView textView = view.findViewById(R.id.currentDays);
        textView.setText(String.valueOf(this.intervalDay));
        textView = view.findViewById(R.id.currentHours);
        textView.setText(String.valueOf(this.intervalHour));
        textView = view.findViewById(R.id.currentMinutes);
        textView.setText(String.valueOf(this.intervalMinute));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.TIME_PICKER_DIALOG_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                if (Objects.requireNonNull(data.getExtras()).containsKey("hours") &&
                        Objects.requireNonNull(data.getExtras()).containsKey("minutes")) {
                    this.startHour = data.getExtras().getInt("hours");
                    this.startMinute = data.getExtras().getInt("minutes");
                    setStartTimeText();
                }
            }
        } else if (requestCode == this.NUMBER_PICKER_DIALOG_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (Objects.requireNonNull(data.getExtras()).containsKey("number") &&
                        data.getExtras().containsKey("requestId")) {
                    int interval = data.getExtras().getInt("number");
                    switch (data.getExtras().getInt("requestId")) {
                        case 0:
                            this.intervalDay = interval;
                            break;
                        case 1:
                            this.intervalHour = interval;
                            break;
                        case 2:
                            this.intervalMinute = interval;
                            break;
                    }
                    setIntervalTexts();
                }
            }
        }
    }

    private void onCheckboxClicked(View v) {
        this.startToday = ((CheckBox) v).isChecked();
    }
}
