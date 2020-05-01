package com.mako.recurringreminder;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class NumberPickerFragment extends DialogFragment {
    private int currentValue;
    private int requestId;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final NumberPicker numberPicker = new NumberPicker(getActivity());
        assert getArguments() != null;
        this.requestId = getArguments().getInt("requestId");

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                changeVal(picker, oldVal, newVal);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Choose Value");
        builder.setMessage("Choose a number :");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                returnValue();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setView(numberPicker);
        return builder.create();
    }

    static NumberPickerFragment newInstance(int requestId) {
        NumberPickerFragment f = new NumberPickerFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("requestId", requestId);
        f.setArguments(args);
        return f;
    }

    private void returnValue() {
        Bundle bundle = new Bundle();
        bundle.putInt("number", this.currentValue);
        bundle.putInt("requestId", this.requestId);
        Intent intent = new Intent().putExtras(bundle);
        Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }

    private void changeVal(NumberPicker picker, int oldVal, int newVal) {
        this.currentValue = newVal;
    }
}
