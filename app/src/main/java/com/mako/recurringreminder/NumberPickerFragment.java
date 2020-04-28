package com.mako.recurringreminder;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class NumberPickerFragment extends DialogFragment
        implements NumberPicker.OnValueChangeListener {
    private int currentValue;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Choose Value");
        builder.setMessage("Choose a number :");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                returnValue(dialog, which);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast toast = Toast.makeText(getContext(), "CANCEL " + currentValue, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        builder.setView(numberPicker);
        return builder.create();
    }

    private void returnValue(DialogInterface dialog, int which){
        Toast toast = Toast.makeText(getContext(), "MYYYY " + this.currentValue, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        this.currentValue = newVal;
    }
}
