package com.example.isaacparsons.planner;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.isaacparsons.planner.Calendar.CalendarAdapter;
import com.example.isaacparsons.planner.Calendar.CalendarFragment;
import com.example.isaacparsons.planner.DBforEvents.EventsDB;
import com.example.isaacparsons.planner.Scheduler.MyNotificationPublisher;
import com.example.isaacparsons.planner.Scheduler.ScheduleNotification;
import com.example.isaacparsons.planner.Settings.Settings;
import com.example.isaacparsons.planner.ToDo.Event;
import com.example.isaacparsons.planner.ToDo.EventDetailsDialogue;
import com.example.isaacparsons.planner.ToDo.todofragment;
import com.example.isaacparsons.planner.Weather.Weather;
import com.roomorama.caldroid.CaldroidFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MainFragment mainFragment;

    public MainFragment getMainFragment() {
        return mainFragment;
    }

    todofragment todoFragment;
    CalendarFragment calendarFragment = new CalendarFragment();
    FragmentManager manager = getSupportFragmentManager();
    public static MainActivity mainActivity;
    public String apikey = "fe11d748f2ed654122b375b04adeb617";
    public Weather weather;


    public static MainActivity getMainActivity(){
        return mainActivity;
    }
    public static void setMainActivity(MainActivity mainActivity){
        MainActivity.mainActivity = mainActivity;
    }

    public Weather getWeather(){
        return weather;
    }

    public void ScheduleNotification(Context context, Calendar date, int notificationId ,String title, String dueDate) {
        Calendar now = Calendar.getInstance();

        long futureInMillis = date.getTimeInMillis() - System.currentTimeMillis();
        Log.d("time to notification:", " "+ futureInMillis);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Task: "+title)
                .setContentText("Due: "+dueDate)
                .setSmallIcon(R.drawable.ic_menu_send)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis + SystemClock.elapsedRealtime(), pendingIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.setMainActivity(this);
        getWeatherJson();
        todoFragment = new todofragment();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Settings settings = new Settings();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, settings).addToBackStack(null).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    public todofragment getTodoFragment() {
        return todoFragment;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();

        if (id == R.id.Home) {
            fm.beginTransaction().replace(R.id.content_main, mainFragment).commit();

        } else if (id == R.id.to_do) {
            fm.beginTransaction().replace(R.id.content_main, todoFragment).addToBackStack(null).commit();

        } else if (id == R.id.calendar) {
            fm.beginTransaction().replace(R.id.content_main, calendarFragment).addToBackStack(null).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addtorecycler(String whichlist, Event event){

        event.setCategory(whichlist);
        todoFragment.getAdapter(whichlist).AddData(event);
    }

    public void removefromrecycler(String name, String category){
        Event event = new Event(name);
        event.setCategory(category);
        todoFragment.getAdapter(category).DeleteData(event);
        if(calendarFragment.getCalendarAdapter()!= null){
            calendarFragment.getCalendarAdapter().DeleteData(event);
        }
    }
    public void getWeatherJson(){

        //get url

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String location = sharedPreferences.getString("current", "bogota");
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+location+"&APPID="+apikey;

        // prepare the Request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {
                            JSONArray jsonArray = response.getJSONArray("weather");
                            JSONObject jsonweatherTypeobject = jsonArray.getJSONObject(0);
                            JSONObject jsonObject = response.getJSONObject("main");
                            weather = new Weather(String.valueOf(jsonObject.getInt("temp")-273), jsonweatherTypeobject.getString("main"));

                            if(mainFragment == null){
                                mainFragment = new MainFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, mainFragment).commit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("temp: ", error.toString());
                        Log.d("weather: ", error.toString());
                    }
                }
        );
        // add it to the RequestQueue
        requestQueue.add(getRequest);
    }
    public void checkDaily(){
        
    }


}

