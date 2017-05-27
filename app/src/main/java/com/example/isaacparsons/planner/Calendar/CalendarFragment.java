package com.example.isaacparsons.planner.Calendar;


import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.isaacparsons.planner.DBforEvents.EventsDB;
import com.example.isaacparsons.planner.R;
import com.example.isaacparsons.planner.ToDo.Event;
import com.example.isaacparsons.planner.ToDo.TodoAdapter;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    TextView currentdateTextview;
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    RecyclerView calendarRecycler;
    CalendarAdapter calendarAdapter;


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        currentdateTextview = (TextView)v.findViewById(R.id.calendar_fragment_current_date);

        long calendar = Calendar.getInstance().getTimeInMillis();

        //get current date for textview
        java.util.Calendar calendarCurrent = java.util.Calendar.getInstance();
        int month = calendarCurrent.get(java.util.Calendar.MONTH);
        int year = calendarCurrent.get(java.util.Calendar.YEAR);
        int day = calendarCurrent.get(java.util.Calendar.DAY_OF_WEEK);
        int dayofmonth = calendarCurrent.get(java.util.Calendar.DAY_OF_MONTH);
        currentdateTextview.setText(days[day - 1] + ", " + months[month] + " " + dayofmonth + " " + year);


        CalendarView calendarView = (CalendarView)v.findViewById(R.id.calendarView);
        calendarView.setDate(calendar, false, true);
        calendarView.setMaxDate(calendar);
        calendarView.setMinDate(calendar);

        //get current days items from DB
        ArrayList<Event> arrayList;
        String currentdayofmonth;
        String currentmonth;

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
        EventsDB eventsDB = new EventsDB(getContext());
        arrayList = eventsDB.getTodaysElements(todayParsedDate);

        //recyclerview
        calendarRecycler = (RecyclerView)v.findViewById(R.id.calendar_recyler);
        calendarAdapter = new CalendarAdapter(arrayList, getContext());
        calendarRecycler.setAdapter(calendarAdapter);

        LinearLayoutManager linearLayoutManagercalendar = new LinearLayoutManager(getContext());
        linearLayoutManagercalendar.setOrientation(LinearLayoutManager.VERTICAL);
        calendarRecycler.setLayoutManager(linearLayoutManagercalendar);
        //Drawable drawable = getContext().getResources().getDrawable(R.drawable.calendar_cardview_decordation);

        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(calendarRecycler.getContext(),
        //        linearLayoutManagercalendar.getOrientation());
        //dividerItemDecoration.setDrawable(drawable);
        //calendarRecycler.addItemDecoration(dividerItemDecoration);


        return v;
    }

    public CalendarAdapter getCalendarAdapter() {
        return calendarAdapter;
    }
}
