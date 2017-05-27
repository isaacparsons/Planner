package com.example.isaacparsons.planner.ToDo;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isaacparsons.planner.DBforEvents.EventsDB;
import com.example.isaacparsons.planner.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class todofragment extends Fragment {


    private ArrayList<Event> dailylist = new ArrayList<>();
    private ArrayList<Event> dailylist2 = new ArrayList<>();
    private ArrayList<Event> dailylist3 = new ArrayList<>();

    public RecyclerView recyclerView;
    public RecyclerView recyclerViewfocused;
    public RecyclerView recyclerViewgeneral;

    public TodoAdapter Adapter;
    public TodoAdapter Adapter2;
    public TodoAdapter Adapter3;



    public todofragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_todofragment, container, false);

        EventsDB eventsDB = new EventsDB(getContext());
        dailylist = eventsDB.getAllEvents("Daily");
        dailylist2 = eventsDB.getAllEvents("Due");
        dailylist3 = eventsDB.getAllEvents("Not Due");

        //visibility for Textview if arraylist empty

        //Event recyclerview
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerviewdaily);
        Adapter = new TodoAdapter(dailylist, getContext());
        recyclerView.setAdapter(Adapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager2);

        //focused recyclerview
        recyclerViewfocused = (RecyclerView)v.findViewById(R.id.recyclerviewfocused);
        Adapter2 = new TodoAdapter(dailylist2, getContext());
        recyclerViewfocused.setAdapter(Adapter2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewfocused.setLayoutManager(linearLayoutManager);



        //general recyclerview
        recyclerViewgeneral = (RecyclerView)v.findViewById(R.id.recyclerviewgeneral);
        Adapter3 = new TodoAdapter(dailylist3, getContext());
        recyclerViewgeneral.setAdapter(Adapter3);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewgeneral.setLayoutManager(linearLayoutManager3);


        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoAddPopup popup = new TodoAddPopup();

                popup.show(getActivity().getFragmentManager(), "fabfrag");
            }
        });


        return v;
    }

    public TodoAdapter getAdapter(String pickadapter){
        switch(pickadapter){
            case "Not Due": return Adapter3;
            case "Daily": return Adapter;
            case "Due": return Adapter2;
        }
        return null;
    }


}
