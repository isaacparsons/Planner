package com.example.isaacparsons.planner.ToDo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.isaacparsons.planner.DBforEvents.EventsDB;
import com.example.isaacparsons.planner.MainActivity;
import com.example.isaacparsons.planner.MainFragment;
import com.example.isaacparsons.planner.R;

import java.util.ArrayList;

/**
 * Created by isaacparsons on 2017-05-09.
 */

public class TodoAddPopup extends DialogFragment {
    EditText editText;
    EditText Dateedittext;
    TextView dateTextview;
    EditText descriptionEdittext;
    CheckBox filecheckBox;
    CheckBox foldercheckBox;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.todo_add_popup, null);

        editText = (EditText)v.findViewById(R.id.todopopuptitle);
        Dateedittext = (EditText)v.findViewById(R.id.todopopupDate);
        dateTextview = (TextView)v.findViewById(R.id.date_textview);
        descriptionEdittext = (EditText)v.findViewById(R.id.todopopupdescription);
        filecheckBox = (CheckBox)v.findViewById(R.id.filecheck);
        foldercheckBox = (CheckBox)v.findViewById(R.id.foldercheck);


        final Spinner spinner = (Spinner)v.findViewById(R.id.todospinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.todopopuparray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //only allow one box to be check at once
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        //change visibility of edittext and textview if the selected spinner is Focused.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    Dateedittext.setVisibility(view.VISIBLE);
                    dateTextview.setVisibility(view.VISIBLE);
                }
                else{
                    Dateedittext.setVisibility(view.GONE);
                    dateTextview.setVisibility(view.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        foldercheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filecheckBox.isChecked()){
                    filecheckBox.toggle();
                }
            }
        });
        filecheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(foldercheckBox.isChecked()){
                    foldercheckBox.toggle();
                }
            }
        });


        builder.setView(v)
                // Add action buttons
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String resultTitle = editText.getText().toString();
                        String text = spinner.getSelectedItem().toString();
                        String description = descriptionEdittext.getText().toString();
                        String date;

                        String currentdayofmonth;
                        String currentmonth;
                        java.util.Calendar calendarCurrent = java.util.Calendar.getInstance();
                        int month = calendarCurrent.get(java.util.Calendar.MONTH);
                        int year = calendarCurrent.get(java.util.Calendar.YEAR);
                        int day = calendarCurrent.get(java.util.Calendar.DAY_OF_WEEK);
                        int dayofmonth = calendarCurrent.get(java.util.Calendar.DAY_OF_MONTH);

                        if(String.valueOf(dayofmonth).length()==1){
                            currentdayofmonth = "0"+ String.valueOf(dayofmonth);
                        }else{
                            currentdayofmonth = String.valueOf(dayofmonth);
                        }

                        if(String.valueOf(month+1).length()==1){
                            currentmonth = "0"+ String.valueOf(month+1);
                        }else{
                            currentmonth = String.valueOf(month+1);
                        }
                        String todayParsedDate = String.valueOf(currentdayofmonth) + "/" + String.valueOf(currentmonth) + "/" + String.valueOf(year);

                        if(text.equals("Daily")){
                            date = todayParsedDate;
                        }else{
                            date  = Dateedittext.getText().toString();
                        }
                        if(filecheckBox.isChecked()){
                            MainActivity.getMainActivity().addtorecycler(resultTitle,date, text, description, "file");
                        } else {
                            MainActivity.getMainActivity().addtorecycler(resultTitle,date, text, description, "folder");
                        }

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TodoAddPopup.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
