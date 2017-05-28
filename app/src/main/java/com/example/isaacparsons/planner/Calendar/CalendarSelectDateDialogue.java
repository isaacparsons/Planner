package com.example.isaacparsons.planner.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.isaacparsons.planner.DBforEvents.EventsDB;
import com.example.isaacparsons.planner.MainActivity;
import com.example.isaacparsons.planner.R;
import com.example.isaacparsons.planner.ToDo.Event;
import com.example.isaacparsons.planner.ToDo.EventDetailsDialogue;
import com.example.isaacparsons.planner.ToDo.TodoAdapter;

import java.util.ArrayList;

/**
 * Created by isaacparsons on 2017-05-27.
 */

public class CalendarSelectDateDialogue extends DialogFragment {

    TextView nameTextview;
    TextView dateTextview;
    TextView descriptionTextview;
    TextView staticnameTextview;
    TextView staticdateTextview;
    TextView staticdescriptionTextview;
    TextView noTaskTextview;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.calendar_dialogue, null);
        String date = getArguments().getString("calendarDate");
        EventsDB eventsDB = new EventsDB(getActivity().getApplicationContext());
        ArrayList EventsByDate = eventsDB.getDateEvent(date);

        noTaskTextview = (TextView)v.findViewById(R.id.notaskstextview);

        TodoAdapter dialogueAdapter = new TodoAdapter(EventsByDate, MainActivity.getMainActivity().getApplicationContext());
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.calendar_dialog_recycler);
        recyclerView.setAdapter(dialogueAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.getMainActivity().getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

            if(EventsByDate.isEmpty()) {
                noTaskTextview.setVisibility(v.VISIBLE);

            }


        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        CalendarSelectDateDialogue.this.dismiss();
                    }
                });
        return builder.create();
    }
}
