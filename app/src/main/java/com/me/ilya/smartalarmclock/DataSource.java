package com.me.ilya.smartalarmclock;

import android.database.Cursor;

import com.me.ilya.smartalarmclock.music.Song;

import java.util.ArrayList;

/**
 * Created by Ilya on 6/17/2016.
 */
public interface DataSource {
    public void clearAll();
    public ArrayList<AlarmItem> getAlarms();
    long alarmItemChange(AlarmItem alarmItem);
    Cursor alarmItemsGet();
    public long createAlarm(AlarmItem alarmItem);
    int deleteAlarmItem(int id);
    AlarmItem getAlarmById(int id);
}
