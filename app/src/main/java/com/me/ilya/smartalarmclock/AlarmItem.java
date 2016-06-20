package com.me.ilya.smartalarmclock;

import android.database.Cursor;
import android.database.MatrixCursor;

import java.util.Date;

/**
 * Created by Ilya on 6/6/2016.
 */
public class AlarmItem {
   static private String ID;
   static private String NAME;
    static private String TIME;

    public static String[] COLUMN_NAMES = {
            ID,
            NAME,
            TIME
    };
    private final int id;
    private final String name;
    private final Date time;

    public void addToCursor(MatrixCursor cursor) {
        cursor.addRow(new Object[]{id, name,time});
    }
    public AlarmItem(int ID, String name, Date time) {
        this.id = ID;
        this.name = name;
        this.time = time;
    }
    public static AlarmItem fromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(ID));
        String name = cursor.getString(cursor.getColumnIndex(NAME));

        return new AlarmItem(id, name,new Date());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getTime() {
        return time;
    }

}
