package com.me.ilya.smartalarmclock.data;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.me.ilya.smartalarmclock.music.Song;

import java.util.Arrays;

/**
 * Created by Ilya on 6/6/2016.
 */
public class AlarmItem {
    static private String ID = "id";
    static private String NAME = "name";
    static private String TIME_HOUR = "time_hour";
    static private String TIME_MINUTE = "time_minute";
    static private String SONG = "song";
    static private String DAYS = "days";
    static private String ENABLED = "enabled";
    static private String TONE= "tone";

    public static String[] COLUMN_NAMES = {
            ID,
            NAME,
            TIME_HOUR,
            TIME_MINUTE,
            SONG,
            DAYS,
            ENABLED,
            TONE
    };
    private final int id;
    private final String name;
    private final String song;
    private final int timeHour;
    private final int timeMinute;
    private final boolean days[];
    private  final boolean enabled;
    private  final boolean tone;

    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY =2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRDIAY = 5;
    public static final int SATURDAY = 6;

    public void addToCursor(MatrixCursor cursor) {
        cursor.addRow(new Object[]{id, name, timeHour, timeMinute, song, Boolean.toString(days[0]) + " " +
                Boolean.toString(days[1]) + " " +
                Boolean.toString(days[2]) + " " +
                Boolean.toString(days[3]) + " " +
                Boolean.toString(days[4]) + " " +
                Boolean.toString(days[5]) + " " +
                Boolean.toString(days[6]),enabled, tone
        });
    }

    public AlarmItem(AlarmItem a){
        this.id=a.getId();
        this.song=a.getSong();
        this.enabled=a.isEnabled();
        this.name = a.getName();
        this.timeHour = a.timeHour;
        this.timeMinute = a.timeMinute;
        days=a.getDays();
        tone =a.isTone();
    }
    public AlarmItem(int ID, String name, int timeHour, int timeMinute, String  song1,boolean tone) {
        this.id = ID;
        song = song1;
        enabled = true;
        this.name = name;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        days = new boolean[7];
        Arrays.fill(days, true);
        this.tone =tone;
    }

    public AlarmItem(int ID, String name, int timeHour, int timeMinute,String song, boolean[] b,boolean enabled,boolean isTone) {
        this.id = ID;
        this.enabled = enabled;
        this.name = name;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.song = song;
        days = b;
        this.tone =isTone;
    }

    public AlarmItem(int ID, String name, int timeHour, int timeMinute,boolean enabled) {
        this.id = ID;
        this.enabled =enabled;
        this.name = name;
        days = new boolean[7];
        Arrays.fill(days, true);
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        song = Song.DEFAULT().getUri();///TODO
        tone=true;
    }

    public void setDay(int dayOfWeek, boolean value) {
        days[dayOfWeek] = value;
    }

    public void setDays(boolean[] b) {
        for (int i = 0; i < 7; i++) {
            days[i] = b[i];
        }
    }

    public boolean getDay(int dayOfWeek) {
        return days[dayOfWeek];
    }

    public static AlarmItem fromCursor(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex(ID));
        String name = cursor.getString(cursor.getColumnIndex(NAME));
        String song = cursor.getString(cursor.getColumnIndex(SONG));
        boolean tone = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(TONE)));
        int timeHour = cursor.getInt(cursor.getColumnIndex(TIME_HOUR));
        int timeMinute = cursor.getInt(cursor.getColumnIndex(TIME_MINUTE));
        String[] parts = cursor.getString(cursor.getColumnIndex(AlarmItem.DAYS)).split(",");
        boolean[] days=new boolean[7];
        boolean enabled=false;
        if(cursor.getString(cursor.getColumnIndex(ENABLED)).equals("1"))enabled=true;

        for (int i = 0; i < 7; i++)
           days[i]= Boolean.parseBoolean(parts[i]);
        return new AlarmItem(id, name, timeHour, timeMinute,song,days,enabled,tone);

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public String getSong() {
        return song;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public boolean[] getDays() {
        return days;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public boolean isTone() {
        return tone;
    }
}
