package com.example.isaacparsons.planner.Settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.isaacparsons.planner.MainActivity;
import com.example.isaacparsons.planner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {
    Button saveButton;
    EditText currentLocationEdittext;
    TextView currentLocationTextView;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        saveButton = (Button)v.findViewById(R.id.settings_location_save);
        currentLocationEdittext = (EditText)v.findViewById(R.id.settings_location_edittext);
        currentLocationTextView = (TextView)v.findViewById(R.id.current_location);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String location = sharedPreferences.getString("current", "bogota");

        currentLocationTextView.setText(location);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("current", (currentLocationEdittext.getText().toString()).toLowerCase());
                editor.commit();
                currentLocationTextView.setText(currentLocationEdittext.getText().toString());
                MainActivity.getMainActivity().getWeatherJson();

            }
        });

        return v;
    }

}
