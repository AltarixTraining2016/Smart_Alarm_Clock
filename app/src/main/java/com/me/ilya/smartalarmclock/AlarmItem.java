package com.me.ilya.smartalarmclock;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.me.ilya.smartalarmclock.music.Song;

import java.util.Arrays;

/**
 * Created by Ilya on 6/6/2016.
 */
public class AlarmItem {
    static private String TABLE_NAME = "alarm";
    static private String ID = "id";
    static private String NAME = "name";
    static private String TIME_HOUR = "time_hour";
    static private String TIME_MINUTE = "time_minute";
    static private String SONG = "song";
    static private String DAYS = "days";
    static private String ENABLED = "enabled";

    public static String[] COLUMN_NAMES = {
            ID,
            TABLE_NAME,
            NAME,
            TIME_HOUR,
            TIME_MINUTE,
            SONG,
            DAYS,
            ENABLED
    };
    private final int id;
    private final String name;
    private final Song song;
    private final int timeHour;
    private final int timeMinute;
    private final boolean days[];
    private  final boolean enabled;

    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRDIAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;

    public void addToCursor(MatrixCursor cursor) {
        cursor.addRow(new Object[]{id, name, timeHour, timeMinute, song, Boolean.toString(days[0]) + " " +
                Boolean.toString(days[1]) + " " +
                Boolean.toString(days[2]) + " " +
                Boolean.toString(days[3]) + " " +
                Boolean.toString(days[4]) + " " +
                Boolean.toString(days[5]) + " " +
                Boolean.toString(days[6]),enabled
        });
    }

    public AlarmItem(int ID, String name, int timeHour, int timeMinute, Song song1) {
        this.id = ID;
        song = song1;
        enabled = true;
        this.name = name;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        days = new boolean[7];
        Arrays.fill(days, true);
    }

    public AlarmItem(int ID, String name, int timeHour, int timeMinute, Song song, boolean[] b,boolean enabled) {
        this.id = ID;
        this.enabled = enabled;
        this.name = name;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.song = song;
        days = b;
    }

    public AlarmItem(int ID, String name, int timeHour, int timeMinute,boolean enabled) {
        this.id = ID;
        this.enabled =enabled;
        this.name = name;
        days = new boolean[7];
        Arrays.fill(days, true);
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        song = Song.DEFAULT();
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
        int timeHour = cursor.getInt(cursor.getColumnIndex(TIME_HOUR));
        int timeMinute = cursor.getInt(cursor.getColumnIndex(TIME_MINUTE));
        String[] parts = cursor.getString(cursor.getColumnIndex(AlarmItem.DAYS)).split(" ");
        boolean enabled=    Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(ENABLED)));
        AlarmItem alarmItem = new AlarmItem(id, name, timeHour, timeMinute,enabled);
        for (int i = 0; i < 7; i++)
            alarmItem.setDay(i, Boolean.parseBoolean(parts[i]));
        return alarmItem;
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
        return new Song(song);
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

}
