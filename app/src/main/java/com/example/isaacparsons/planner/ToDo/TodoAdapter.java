package com.example.isaacparsons.planner.ToDo;

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

import java.util.ArrayList;

/**
 * Created by isaacparsons on 2017-05-07.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    ArrayList<Event> dailylist;
    Context context;


    public TodoAdapter(ArrayList<Event> dailylist, Context context) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

        return new MyViewHolder(v);
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

    public void AddData(Event event){
        EventsDB eventsDB = new EventsDB(context);
        eventsDB.addEvent(event);
        dailylist.add(event);
        notifyDataSetChanged();
    }
    public void DeleteData(Event event){
        EventsDB eventsDB = new EventsDB(context);
        eventsDB.deleteContact(event);
        for(int i = 0; i<dailylist.size(); i++){
            if ((dailylist.get(i).getName()).equals(event.getName())){
                dailylist.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }
}
