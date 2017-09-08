package com.example.isaacparsons.planner.Calendar;

/**
 * Created by isaacparsons on 2017-09-08.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaacparsons.planner.MainActivity;
import com.example.isaacparsons.planner.R;
import com.example.isaacparsons.planner.ToDo.Event;
import com.example.isaacparsons.planner.ToDo.EventDetailsDialogue;

import java.util.ArrayList;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import com.example.isaacparsons.planner.ToDo.TodoAdapter;

import java.util.ArrayList;

/**
 * Created by isaacparsons on 2017-05-23.
 */

public class CalendarAdapter2 extends RecyclerView.Adapter<CalendarAdapter2.MyViewHolder> {
    ArrayList<Event> dailylist;
    Context context;


    public CalendarAdapter2(ArrayList<Event> dailylist, Context context) {
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
            checkBox = (CheckBox)view.findViewById(R.id.completedcheckbox);
            imageView = (ImageView)view.findViewById(R.id.card_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                    EventDetailsDialogue eventDetailsDialogue = new EventDetailsDialogue();
                    String eventtitle = title.getText().toString();

                    Bundle bundle = new Bundle();
                    bundle.putString("title", eventtitle);
                    eventDetailsDialogue.setArguments(bundle);

                    eventDetailsDialogue.show(manager, " ");
                }
            });
        }
    }
    @Override
    public CalendarAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

        return new CalendarAdapter2.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CalendarAdapter2.MyViewHolder holder, int position) {
        Event event = dailylist.get(position);
        holder.title.setText(event.getName());
        int resID = MainActivity.getMainActivity().getResources().getIdentifier(event.getImagetype(), "drawable", MainActivity.getMainActivity().getPackageName());
        holder.imageView.setImageResource(resID);
    }

    @Override
    public int getItemCount() {
        return dailylist.size();
    }
    public void DeleteData(Event event){
        for(int i = 0; i<dailylist.size(); i++){
            if ((dailylist.get(i).getName()).equals( event.getName())){
                dailylist.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

}
