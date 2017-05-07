package com.example.isaacparsons.planner.ToDo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.isaacparsons.planner.R;

import java.util.ArrayList;

/**
 * Created by isaacparsons on 2017-05-07.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    ArrayList<Daily> dailylist;

    public TodoAdapter(ArrayList<Daily> dailylist) {
        this.dailylist = dailylist;
        Log.d("testing", dailylist.get(0).getName());
        Log.d("testing", dailylist.get(1).getName());
        Log.d("testing", dailylist.get(2).getName());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            checkBox= (CheckBox)view.findViewById(R.id.completedcheckbox);

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

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Daily daily = dailylist.get(position);
        holder.title.setText(daily.getName());
    }

    @Override
    public int getItemCount() {
        return dailylist.size();
    }
}
