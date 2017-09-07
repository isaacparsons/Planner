package com.example.isaacparsons.planner.ToDo;

/**
 * Created by isaacparsons on 2017-05-06.
 */

public class Event {
    private String name;
    private boolean checked;
    private String date;
    private String category;
    private String description;
    private String imagetype;
    private String time;
    private String notificationTime;

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getImagetype() {
        return imagetype;
    }

    public void setImagetype(String imagetype) {
        this.imagetype = imagetype;
    }

    public Event(){

    }
    public Event(String name) {
        this.name = name;
        checked = false;
        this.date = "NONE";
        this.category = "Daily";
        this.description = " ";
        this.imagetype = "file";
        this.time = "1200";
    }
    public Event(String name, String date, String time, String category, String description, String imagetype){
        this.date = date;
        this.name = name;
        this.category = category;
        this.description = description;
        this.imagetype = imagetype;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
