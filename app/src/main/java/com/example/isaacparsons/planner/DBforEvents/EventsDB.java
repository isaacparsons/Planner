package com.example.isaacparsons.planner.DBforEvents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.isaacparsons.planner.ToDo.Event;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by isaacparsons on 2017-05-21.
 */

public class EventsDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todoList";
    private static final String TABLE_NAME = "todoTable";

    //column
    private static final String KEY_ID = "id";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_CATEGORY = "event_category";
    private static final String EVENT_DATE = "event_date";
    private static final String EVENT_DESCRIPTION = "event_description";
    private static final String EVENT_IMAGE = "event_image";
    private static final String EVENT_TIME = "event_time";
    private static final String EVENT_NOTIFICATION_DATE = "event_notification_date";



    public EventsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + EVENT_NAME + " TEXT," + EVENT_DATE + " TEXT," + EVENT_CATEGORY + " TEXT," + EVENT_DESCRIPTION + " TEXT,"+ EVENT_IMAGE + " TEXT," + EVENT_TIME + " adTEXT," +EVENT_NOTIFICATION_DATE+ " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addEvent(Event event){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //new row values
        values.put(EVENT_NAME, event.getName());
        values.put(EVENT_DATE, event.getDate());
        values.put(EVENT_CATEGORY, event.getCategory());
        values.put(EVENT_DESCRIPTION, event.getDescription());
        values.put(EVENT_IMAGE, event.getImagetype());
        values.put(EVENT_TIME, event.getTime());
        values.put(EVENT_NOTIFICATION_DATE, event.getNotificationTime());

        sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    //returns an arraylist of all the events in the database.
    public ArrayList<Event> getAllEvents(String category){
        ArrayList<Event> eventlist = new ArrayList<Event>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE event_category = '"+category+"'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Event event = new Event();
                event.setName(cursor.getString(1));
                event.setDate(cursor.getString(2));
                event.setCategory(cursor.getString(3));
                event.setDescription(cursor.getString(4));
                event.setImagetype(cursor.getString(5));
                event.setTime(cursor.getString(6));
                event.setNotificationTime(cursor.getString(7));
                eventlist.add(event);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return eventlist;
    }

    public ArrayList<Event> getTodaysElements(String today){
        ArrayList<Event> eventlist = new ArrayList<Event>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE event_date = '"+today+"'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Event event = new Event();
                event.setName(cursor.getString(1));
                event.setDate(cursor.getString(2));
                event.setCategory(cursor.getString(3));
                event.setDescription(cursor.getString(4));
                event.setImagetype(cursor.getString(5));
                event.setTime(cursor.getString(6));
                event.setNotificationTime(cursor.getString(7));
                eventlist.add(event);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return eventlist;
    }

    public ArrayList<Event> getWeeksElements(String[] weekDates){
        ArrayList<Event> eventlist = new ArrayList<Event>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE event_date = '"+weekDates[0]+"' OR "
            + "event_date = '"+weekDates[1]+"' OR "
                + "event_date = '"+weekDates[2]+"' OR "
                + "event_date = '"+weekDates[3]+"' OR "
                + "event_date = '"+weekDates[4]+"' OR "
                + "event_date = '"+weekDates[5]+"' OR "
                + "event_date = '"+weekDates[6]+"'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Event event = new Event();
                event.setName(cursor.getString(1));
                event.setDate(cursor.getString(2));
                event.setCategory(cursor.getString(3));
                event.setDescription(cursor.getString(4));
                event.setImagetype(cursor.getString(5));
                event.setTime(cursor.getString(6));
                event.setNotificationTime(cursor.getString(7));
                eventlist.add(event);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return eventlist;
    }

    public void deleteContact(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, EVENT_NAME + " = ?",
                new String[] { event.getName() });
        db.close();
    }
    public Event getEvent(String title){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE event_name = '"+title+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Event event = new Event();
        if(cursor.moveToFirst()) {
            event.setName(title);
            event.setDate(cursor.getString(2));
            event.setCategory(cursor.getString(3));
            event.setDescription(cursor.getString(4));
            event.setImagetype(cursor.getString(5));
            event.setTime(cursor.getString(6));
            event.setNotificationTime(cursor.getString(7));
        }
        db.close();
        return event;

    }

    public ArrayList<Event> getDateEvent(String date) {
        ArrayList<Event> eventlist = new ArrayList<Event>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE event_date = '" + date + "'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setName(cursor.getString(1));
                event.setDate(cursor.getString(2));
                event.setCategory(cursor.getString(3));
                event.setDescription(cursor.getString(4));
                event.setImagetype(cursor.getString(5));
                event.setTime(cursor.getString(6));
                event.setNotificationTime(cursor.getString(7));
                eventlist.add(event);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return eventlist;
    }
}
