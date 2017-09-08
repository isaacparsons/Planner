package com.example.isaacparsons.planner.ToDo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

/**
 * Created by isaacparsons on 2017-09-06.
 */

public class TodoDatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        String yearString = String.valueOf(year);
        String monthString = String.valueOf(month+1);
        Log.d("please", monthString);
        String dayString = String.valueOf(day);
        if(month+1<10){
            monthString ="0" + String.valueOf(month+1);}
        if(day<10){
            dayString = "0" + String.valueOf(day);}

        Intent i = new Intent();
        i.putExtra("selectedDate",yearString + "/" + monthString + "/" + dayString);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
    }
}