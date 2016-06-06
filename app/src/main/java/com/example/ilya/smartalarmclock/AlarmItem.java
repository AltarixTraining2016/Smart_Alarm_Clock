package com.example.ilya.smartalarmclock;

/**
 * Created by Ilya on 6/6/2016.
 */
public class AlarmItem {
    private int ID;
    private String name;
    private String time;


    public AlarmItem(int ID, String name, String time) {
        this.ID = ID;
        this.name = name;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
