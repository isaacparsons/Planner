package com.example.isaacparsons.planner.ToDo;

/**
 * Created by isaacparsons on 2017-05-06.
 */

public class Daily {
    private String name;
    private boolean checked;

    public Daily(String name) {
        this.name = name;
        checked = false;
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
}
