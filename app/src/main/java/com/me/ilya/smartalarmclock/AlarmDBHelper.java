package com.me.ilya.smartalarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ilya on 7/1/2016.
 */
public class AlarmDBHelper extends SQLiteOpenHelper implements DataSource {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarmclock.db";
    public static final String TABLE_NAME = "alarm";

    private static final String SQL_CREATE_ALARM = "CREATE TABLE " + TABLE_NAME + " (" +
            AlarmItem.COLUMN_NAMES[0] + " INTEGER PRIMARY KEY AUTOINCREMENT," +//id
            AlarmItem.COLUMN_NAMES[1] + " TEXT," +//name
            AlarmItem.COLUMN_NAMES[2] + " INTEGER," +//hour
            AlarmItem.COLUMN_NAMES[3] + " INTEGER," +//minute
            AlarmItem.COLUMN_NAMES[4] + " TEXT," +//song
            AlarmItem.COLUMN_NAMES[5] + " TEXT," +//days
            AlarmItem.COLUMN_NAMES[6] + " BOOLEAN," +//enable
            AlarmItem.COLUMN_NAMES[7] + " TEXT" +//tone
            " )";

    private static final String SQL_DELETE_ALARM =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALARM);
        onCreate(db);
    }

    private AlarmItem populateAlarm(Cursor c) {
        return AlarmItem.fromCursor(c);
    }

    private ContentValues populateContent(AlarmItem alarmItem) {
        ContentValues values = new ContentValues();
        //values.put(AlarmItem.COLUMN_NAMES[0], alarmItem.getId());
        values.put(AlarmItem.COLUMN_NAMES[1], alarmItem.getName());
        values.put(AlarmItem.COLUMN_NAMES[2], alarmItem.getTimeHour());
        values.put(AlarmItem.COLUMN_NAMES[3], alarmItem.getTimeMinute());
        values.put(AlarmItem.COLUMN_NAMES[4], alarmItem.getSong());
        String repeatingDays = "";
        for (int i = 0; i < 7; ++i) {
            repeatingDays += alarmItem.getDay(i) + ",";
        }
        values.put(AlarmItem.COLUMN_NAMES[5], repeatingDays);
        values.put(AlarmItem.COLUMN_NAMES[6], alarmItem.isEnabled());
        values.put(AlarmItem.COLUMN_NAMES[7],Boolean.FALSE.toString(alarmItem.isTone()));
        return values;
    }

    public long createAlarm(AlarmItem alarmItem) {
        ContentValues values = populateContent(alarmItem);
        return getWritableDatabase().insert(TABLE_NAME, null, values);
    }


    public AlarmItem getAlarm(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * FROM " + TABLE_NAME + " WHERE " + AlarmItem.COLUMN_NAMES[0] + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return populateAlarm(c);
        }

        return null;
    }

    @Override
    public void clearAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + TABLE_NAME;
        Cursor c = db.rawQuery(select, null);
        while (c.moveToNext())
            deleteAlarmItem(c.getInt(c.getColumnIndex(AlarmItem.COLUMN_NAMES[0])));
        c.close();
    }

    @Override
    public ArrayList<AlarmItem> getAlarms() {
        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * FROM " + TABLE_NAME;

        Cursor c = db.rawQuery(select, null);

        ArrayList<AlarmItem> alarmList = new ArrayList<>();

        while (c.moveToNext()) {
            alarmList.add(populateAlarm(c));
        }

        if (!alarmList.isEmpty()) {
            return alarmList;
        }

        return null;
    }

    @Override
    public long alarmItemChange(AlarmItem alarmItem) {
        ContentValues values = populateContent(alarmItem);
        return getWritableDatabase().update(TABLE_NAME, values, AlarmItem.COLUMN_NAMES[0] + " = ?", new String[]{String.valueOf(alarmItem.getId())});
    }

    @Override
    public Cursor alarmItemsGet() {
        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * FROM " + TABLE_NAME;
       return  db.rawQuery(select, null);

    }


    @Override
    public int deleteAlarmItem(int id) {
        return getWritableDatabase().delete(TABLE_NAME, AlarmItem.COLUMN_NAMES[0] + " = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public AlarmItem getAlarmById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * FROM " + TABLE_NAME + " WHERE " + AlarmItem.COLUMN_NAMES[0] + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return populateAlarm(c);
        }

        return null;
    }


}
