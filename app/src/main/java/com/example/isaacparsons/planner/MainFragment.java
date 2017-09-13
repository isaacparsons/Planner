package com.example.isaacparsons.planner;


import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.isaacparsons.planner.Home.NoteEditText;
import com.example.isaacparsons.planner.Weather.Weather;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Array;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    TextView DateEdittext;
    TextView weathertex;
    ImageView weathericon;
    TextView currentWeather;
    LinearLayout linearLayout;
    NoteEditText noteEditText;

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("notes", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("note", noteEditText.getText().toString());
        editor.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        Weather weather = MainActivity.getMainActivity().getWeather();


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String location = sharedPreferences.getString("current", "bogota");
        currentWeather = (TextView)v.findViewById(R.id.home_current_weather_textview);
        weathericon = (ImageView)v.findViewById(R.id.weather_icon);
        weathertex = (TextView)v.findViewById(R.id.weather_temp_text);

        // get what was typed in notes
        SharedPreferences sharedPreferencesfornote = getContext().getSharedPreferences("notes", Context.MODE_PRIVATE);
        String note = sharedPreferencesfornote.getString("note", " ");


        if(weather!=null) {
            weathertex.setText(weather.getCurrentTemp() + (char) 0x00B0 + "C");
            currentWeather.setText("Current Weather for " + location + ": ");
            if ((weather.getCurrentWeather()).equals("Clear") || (weather.getCurrentWeather()).equals("Clouds")) {
                int resID = MainActivity.getMainActivity().getResources().getIdentifier((weather.getCurrentWeather()).toLowerCase(), "drawable", MainActivity.getMainActivity().getPackageName());
                weathericon.setImageResource(resID);
            }
        }

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int month = calendar.get(java.util.Calendar.MONTH);
        int year = calendar.get(java.util.Calendar.YEAR);
        int day = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        int dayofmonth = calendar.get(java.util.Calendar.DAY_OF_MONTH);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)v.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(days[day - 1] + ", " + months[month] + " " + dayofmonth + " " + year);

        linearLayout = (LinearLayout)v.findViewById(R.id.noteview);

        XmlPullParser parser = getResources().getLayout(R.layout.fragment_main);
        AttributeSet attributes = Xml.asAttributeSet(parser);

        noteEditText = new NoteEditText(getContext(), attributes);
        noteEditText.setText("Write Down Notes Here");
        linearLayout.addView(noteEditText);

        return v;
    }

}
