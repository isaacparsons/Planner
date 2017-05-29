package com.example.isaacparsons.planner.Calendar;

/**
 * Created by isaacparsons on 2017-05-28.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaacparsons.planner.DBforEvents.EventsDB;
import com.example.isaacparsons.planner.MainActivity;
import com.example.isaacparsons.planner.R;
import com.example.isaacparsons.planner.ToDo.Event;
import com.example.isaacparsons.planner.ToDo.EventDetailsDialogue;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;

import com.example.isaacparsons.planner.DBforEvents.EventsDB;
import com.example.isaacparsons.planner.MainActivity;
import com.example.isaacparsons.planner.R;
import com.example.isaacparsons.planner.ToDo.TodoAdapter;

import java.util.ArrayList;

/**
 * Created by isaacparsons on 2017-05-07.
 */

public class CalendarDialogAdapter extends RecyclerView.Adapter<CalendarDialogAdapter.MyViewHolder> {
    ArrayList<Event> dailylist;
    Context context;


    public CalendarDialogAdapter(ArrayList<Event> dailylist, Context context) {
        this.dailylist = dailylist;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CheckBox checkBox;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            checkBox= (CheckBox)view.findViewById(R.id.completedcheckbox);
            imageView = (ImageView)view.findViewById(R.id.card_image);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

        return new com.example.isaacparsons.planner.Calendar.CalendarDialogAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = dailylist.get(position);
        Log.d("event.getImage: )", " " + event.getImagetype());
        Log.d("event.getName: )", " " + event.getName());

        int resID = MainActivity.getMainActivity().getResources().getIdentifier(event.getImagetype() ,"drawable", MainActivity.getMainActivity().getPackageName());
        Log.d("res; ", String.valueOf(resID));
        holder.title.setText(event.getName());
        holder.imageView.setImageResource(resID);
    }

    @Override
    public int getItemCount() {
        return dailylist.size();
    }

}
