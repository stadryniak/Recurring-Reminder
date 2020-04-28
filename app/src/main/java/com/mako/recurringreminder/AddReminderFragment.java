package com.mako.recurringreminder;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.SyncStateContract;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddReminderFragment extends Fragment {

    private int startHour;
    private int startMinute;
    private int DIALOG_REQUEST_CODE = 71;

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
        TextView currentStartTime = view.findViewById(R.id.currentStartTime);
        currentStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });
        return view;
    }

    private void showTimePickerDialog(View v) {
        DialogFragment pickerFragment = new TimePickerFragment();
        assert getFragmentManager() != null;
        pickerFragment.setTargetFragment(this, this.DIALOG_REQUEST_CODE);
        pickerFragment.show(getFragmentManager(), "timePicker");
    }

    private void setStartTimeText() {
        View view = getView();
        if (view == null) return;
        TextView text = view.findViewById(R.id.currentStartTime);
        text.setText(String.format(Locale.getDefault(), "%d:%d", this.startHour, this.startMinute));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.DIALOG_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                if (Objects.requireNonNull(data.getExtras()).containsKey("hours") ||
                        Objects.requireNonNull(data.getExtras()).containsKey("minutes")) {
                    this.startHour = data.getExtras().getInt("hours");
                    this.startMinute = data.getExtras().getInt("minutes");
                    setStartTimeText();
                }
            }
        }
    }

}
