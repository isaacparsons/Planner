package com.example.isaacparsons.planner.ToDo;


import android.app.Activity;
import android.content.Intent;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.isaacparsons.planner.MainActivity;
import com.example.isaacparsons.planner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTodoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTodoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_TIME = 2;
    private static final int REQUEST_CODE_DATE = 3;
    public static String NOTIFICATION_TIME;
    public static String NOTIFICATION_DATE;
    public static String DATE;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText editText;
    EditText Dateedittext;
    TextView dateTextview;
    EditText descriptionEdittext;
    CheckBox filecheckBox;
    CheckBox foldercheckBox;
    TextView timeTextview;
    TextView getTimeTextviewseperator;
    EditText timehourEdittext;
    EditText timeminutesEdittext;
    TextView notificationTime;

    Button addBtn;
    Button cancelBtn;
    ImageButton notificationBtn;
    CheckBox notifcationCheckbox;
    TextView textviewDate;
    TextView staticTextviewDate;


    public NewTodoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewTodoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewTodoFragment newInstance(String param1, String param2) {
        NewTodoFragment fragment = new NewTodoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void notificationCheck(){
        if (notificationBtn.getVisibility() == View.GONE){
            notificationBtn.setVisibility(View.VISIBLE);
        } else if (notificationBtn.getVisibility() == View.VISIBLE){
            notificationBtn.setVisibility(View.GONE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {

                    Bundle bundle = data.getExtras();
                    String resultDate = bundle.getString("selectedDate", "error");
                    NOTIFICATION_DATE = resultDate;
                    TodoTimePicker todoTimePicker = new TodoTimePicker();
                    todoTimePicker.setTargetFragment(NewTodoFragment.this, REQUEST_CODE_TIME);
                    todoTimePicker.show(MainActivity.mainActivity.getSupportFragmentManager(), "timePicker");

                }
                break;

            case REQUEST_CODE_TIME:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String resultTime = bundle.getString("selectedTime", "error");
                    NOTIFICATION_TIME = resultTime;
                    notificationTime.setText(NOTIFICATION_DATE + " " + NOTIFICATION_TIME);
                    Calendar future = Calendar.getInstance();
                    future.set(Calendar.MONTH, Integer.parseInt(NOTIFICATION_DATE.substring(5,7))-1);
                    future.set(Calendar.DAY_OF_MONTH, Integer.parseInt(NOTIFICATION_DATE.substring(8,10)));
                    future.set(Calendar.HOUR, Integer.parseInt(NOTIFICATION_TIME.substring(0,2)));
                    future.set(Calendar.MINUTE, Integer.parseInt(NOTIFICATION_TIME.substring(2,4)));


                    Log.d("future time", " "+ textviewDate.getText().toString());
                    MainActivity.mainActivity.ScheduleNotification(getContext(), future, 1, editText.getText().toString(), DATE);

                }
                break;
            case REQUEST_CODE_DATE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String resultDate = bundle.getString("selectedDate", "error");
                    DATE = resultDate;
                    textviewDate.setText(resultDate);
                }
                 break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_todo, container, false);
        addBtn = (Button)v.findViewById(R.id.add_todo_btn);
        cancelBtn = (Button)v.findViewById(R.id.cancel_todo_btn);
        notificationBtn = (ImageButton)v.findViewById(R.id.todo_add_notification_add_btn);
        notifcationCheckbox = (CheckBox)v.findViewById(R.id.todo_add_notification_check);
        notificationTime = (TextView)v.findViewById(R.id.new_todo_fragment_notification_time);

        editText = (EditText)v.findViewById(R.id.todopopuptitle);
        //Dateedittext = (EditText)v.findViewById(R.id.todopopupDate);
        //dateTextview = (TextView)v.findViewById(R.id.date_textview);
        descriptionEdittext = (EditText)v.findViewById(R.id.todopopupdescription);
        filecheckBox = (CheckBox)v.findViewById(R.id.filecheck);
        foldercheckBox = (CheckBox)v.findViewById(R.id.foldercheck);
        textviewDate = (TextView)v.findViewById(R.id.new_todo_fragment_date);
        staticTextviewDate = (TextView)v.findViewById(R.id.new_todo_fragment_date_static);

        timeTextview = (TextView)v.findViewById(R.id.timeTextview);
        getTimeTextviewseperator = (TextView)v.findViewById(R.id.seperatortextview);
        timehourEdittext = (EditText)v.findViewById(R.id.time_hour);
        timeminutesEdittext = (EditText)v.findViewById(R.id.time_minutes);




        final Spinner spinner = (Spinner)v.findViewById(R.id.todospinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.todopopuparray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Due"))
                {
                    TodoDatePicker todoDatePicker = new TodoDatePicker();
                    todoDatePicker.setTargetFragment(NewTodoFragment.this, REQUEST_CODE_DATE);
                    todoDatePicker.show(MainActivity.mainActivity.getSupportFragmentManager(), "datePicker");
                    staticTextviewDate.setVisibility(View.VISIBLE);
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });



        foldercheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (filecheckBox.isChecked()) {
                            filecheckBox.toggle();
                        }
                    }
                });
        filecheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(foldercheckBox.isChecked()){
                    foldercheckBox.toggle();
                }
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = spinner.getSelectedItem().toString();
                String description = descriptionEdittext.getText().toString();
                String date;
                String timehour;

                String currentdayofmonth;
                String currentmonth;
                java.util.Calendar calendarCurrent = java.util.Calendar.getInstance();
                int month = calendarCurrent.get(java.util.Calendar.MONTH);
                int year = calendarCurrent.get(java.util.Calendar.YEAR);
                int day = calendarCurrent.get(java.util.Calendar.DAY_OF_WEEK);
                int dayofmonth = calendarCurrent.get(java.util.Calendar.DAY_OF_MONTH);

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

                Event event = new Event(editText.getText().toString());
                event.setNotificationTime(NOTIFICATION_TIME);
                event.setTime("0");
                event.setDescription(descriptionEdittext.getText().toString());

                String changedDate="";
                if (DATE!=null) {
                    changedDate = DATE.substring(8, 10) + "/" + DATE.substring(5, 7) + "/" + DATE.substring(0, 4);
                }
                Log.d("changed Date", changedDate);
                if(spinner.getSelectedItem().toString().equals("Due")){
                    event.setDate(changedDate);
                    Log.d("spinner", " "+spinner.getSelectedItem());
                    event.setCategory("Due");
                }
                if (spinner.getSelectedItem().toString().equals("Daily")){
                    event.setDate(todayParsedDate);
                    event.setCategory("Daily");
                }
                if(spinner.getAdapter().toString().equals("Not Due")){
                    event.setDate("none");
                    event.setCategory("Not Due");
                }


                if(filecheckBox.isChecked()){

                    event.setImagetype("file");
                    MainActivity.mainActivity.addtorecycler(text, event);
                    MainActivity.mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new todofragment()).commit();
                } else {

                    event.setImagetype("folder");
                    MainActivity.mainActivity.addtorecycler(text, event);
                    MainActivity.mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new todofragment()).commit();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, MainActivity.mainActivity.getTodoFragment()).commit();
            }
        });
        notifcationCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationCheck();
            }
        });
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoDatePicker todoDatePicker = new TodoDatePicker();
                todoDatePicker.setTargetFragment(NewTodoFragment.this, REQUEST_CODE);
                todoDatePicker.show(MainActivity.mainActivity.getSupportFragmentManager(), "datePicker");
            }
        });

        return v;
    }

}
