package com.me.ilya.smartalarmclock;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.me.ilya.smartalarmclock.music.Song;

import java.util.Date;
import java.util.TooManyListenersException;

/**
 * Created by Ilya on 6/6/2016.
 */
public class  AlarmItem {
   static private String ID="id";
   static private String NAME="name";
    static private String TIME_HOUR="time_hour";
    static private String TIME_MINUTE="time_minute";
    static private String SONG="song";

    public static String[] COLUMN_NAMES = {
            ID,
            NAME,
            TIME_HOUR,
            TIME_MINUTE,
            SONG
    };
    private final int id;
    private final String name;
    private  Song song;
    private final int timeHour;
    private final int timeMinute;

    public void addToCursor(MatrixCursor cursor) {
        cursor.addRow(new Object[]{id, name,timeHour,timeMinute,song});
    }
    public AlarmItem(int ID, String name, int timeHour,int timeMinute,Song Song) {
        this.id = ID;
        song=Song;
        this.name = name;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
    }
    public AlarmItem(int ID, String name, int timeHour,int timeMinute) {
        this.id = ID;
        this.name = name;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
    }
    public static AlarmItem fromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(ID));
        String name = cursor.getString(cursor.getColumnIndex(NAME));
        int timeHour=cursor.getInt(cursor.getColumnIndex(TIME_HOUR));
        int timeMinute=cursor.getInt(cursor.getColumnIndex(TIME_MINUTE));
        return new AlarmItem(id, name,timeHour,timeMinute);
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

    public Song getSong() {
        return song;
    }

    public int getTimeHour() {
        return timeHour;
    }
}
