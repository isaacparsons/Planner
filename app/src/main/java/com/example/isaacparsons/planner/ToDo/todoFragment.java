package com.example.isaacparsons.planner.ToDo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isaacparsons.planner.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class todofragment extends Fragment {


    private ArrayList<Daily> dailylist = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewfocused;
    private RecyclerView recyclerViewgeneral;

    private TodoAdapter Adapter;
    private TodoAdapter Adapter2;
    private TodoAdapter Adapter3;



    public todofragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Daily testdaily = new Daily("Kms");
        Daily testdaily2 = new Daily("mow the lawn");
        Daily testdaily3 = new Daily("existential crisis");
        dailylist.add(0, testdaily);
        dailylist.add(1, testdaily2);
        dailylist.add(2, testdaily3);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_todofragment, container, false);

        //Daily recyclerview
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerviewdaily);
        Adapter = new TodoAdapter(dailylist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(Adapter);

        //focused recyclerview
        recyclerViewfocused = (RecyclerView)v.findViewById(R.id.recyclerviewfocused);
        Adapter2 = new TodoAdapter(dailylist);
        RecyclerView.LayoutManager LayoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewfocused.setLayoutManager(LayoutManager2);
        recyclerViewfocused.setAdapter(Adapter2);



        //general recyclerview
        recyclerViewgeneral = (RecyclerView)v.findViewById(R.id.recyclerviewgeneral);
        Adapter3 = new TodoAdapter(dailylist);
        RecyclerView.LayoutManager LayoutManager3 = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewgeneral.setLayoutManager(LayoutManager3);
        recyclerViewgeneral.setAdapter(Adapter3);
        return v;
    }

}
