package com.example.isaacparsons.planner.ToDo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.isaacparsons.planner.DBforEvents.EventsDB;
import com.example.isaacparsons.planner.MainActivity;
import com.example.isaacparsons.planner.R;

/**
 * Created by isaacparsons on 2017-05-22.
 */

public class EventDetailsDialogue extends android.support.v4.app.DialogFragment {
    TextView nameTextview;
    TextView descriptionTextView;
    TextView dateTextView;
    TextView timeTextView;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.event_details_dialogue, null);
        nameTextview = (TextView)v.findViewById(R.id.event_details_dialogue_event_name) ;
        descriptionTextView = (TextView)v.findViewById(R.id.details_dialogue_event_description);
        dateTextView = (TextView)v.findViewById(R.id.details_dialogue_event_date);
        timeTextView = (TextView)v.findViewById(R.id.event_details_dialogue_event_time);

        String searchtitle = getArguments().getString("title");
        EventsDB eventsDB = new EventsDB(getContext());
        final Event event = eventsDB.getEvent(searchtitle);

        nameTextview.setText(searchtitle);
        descriptionTextView.setText(event.getDescription());
        timeTextView.setText(event.getTime());
        dateTextView.setText(event.getDate());


        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    EventDetailsDialogue.this.dismiss();
                    }
                })
                .setNegativeButton("Delete Task", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        MainActivity.getMainActivity().removefromrecycler(nameTextview.getText().toString(), event.getCategory());
                    }
                });
        return builder.create();
    }
}

