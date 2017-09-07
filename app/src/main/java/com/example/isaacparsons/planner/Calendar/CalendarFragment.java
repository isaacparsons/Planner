package com.example.isaacparsons.planner.Calendar;


import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.example.isaacparsons.planner.MainActivity;
import com.example.isaacparsons.planner.R;
import com.example.isaacparsons.planner.ToDo.Event;
import com.example.isaacparsons.planner.ToDo.EventDetailsDialogue;
import com.example.isaacparsons.planner.ToDo.TodoAdapter;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    TextView currentdateTextview;
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    RecyclerView calendarRecycler;
    CalendarAdapter calendarAdapter;

    public CalendarAdapter getCalendarAdapter() {
        return calendarAdapter;
    }

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
        final int month = calendarCurrent.get(java.util.Calendar.MONTH);
        int year = calendarCurrent.get(java.util.Calendar.YEAR);
        int day = calendarCurrent.get(java.util.Calendar.DAY_OF_WEEK);
        int dayofmonth = calendarCurrent.get(java.util.Calendar.DAY_OF_MONTH);
        currentdateTextview.setText(days[day - 1] + ", " + months[month] + " " + dayofmonth + " " + year);


        //get current days items from DB
        ArrayList<Event> arrayList;
        String currentdayofmonth;
        String currentmonth;

        if(String.valueOf(dayofmonth).length()==1){
            currentdayofmonth = "0"+ String.valueOf(dayofmonth);
        }else{
            currentdayofmonth = String.valueOf(dayofmonth);}

        if(String.valueOf(month+1).length()==1){
            currentmonth = "0"+ String.valueOf(month+1);
        }else{
            currentmonth = String.valueOf(month+1);}

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


        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


        MainActivity.getMainActivity().getSupportFragmentManager().beginTransaction().replace(R.id.calendar_fragment, caldroidFragment).commit();

        CaldroidListener caldroidListener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                String[] dates = date.toString().split(" ");
                int index = 1;
                String ind;
                for(String m:months){
                    if(m.equals(dates[1])){
                        break;
                    }
                    index++;
                }
                if(index<=10) {
                    ind = "0" + String.valueOf(index);
                } else {
                    ind = String.valueOf(index);
                }
                String parsedDate = dates[2] + "/" + ind + "/" + dates[5];

                CalendarSelectDateDialogue calendarSelectDateDialogue = new CalendarSelectDateDialogue();
                Bundle bundle = new Bundle();
                bundle.putString("calendarDate", parsedDate);

                calendarSelectDateDialogue.setArguments(bundle);
                calendarSelectDateDialogue.show(MainActivity.getMainActivity().getFragmentManager(), "");


            }
        };
        caldroidFragment.setCaldroidListener(caldroidListener);
        ArrayList<Event> backgrounddates = eventsDB.getAllEvents("Due");
        ArrayList<Event> b2 = eventsDB.getAllEvents("Daily");
        backgrounddates.addAll(b2);
        for(int i =0; i<backgrounddates.size();i++){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date d = sdf.parse(backgrounddates.get(i).getDate());
                Drawable blue = getResources().getDrawable( R.color.calendar_background_due );
                caldroidFragment.setBackgroundDrawableForDate(blue, d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



        return v;
    }

}
