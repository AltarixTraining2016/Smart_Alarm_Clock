package com.me.ilya.smartalarmclock;

import android.database.Cursor;

import com.me.ilya.smartalarmclock.music.Song;

import java.util.ArrayList;

/**
 * Created by Ilya on 6/17/2016.
 */
public interface DataSource {
    public ArrayList<AlarmItem> getAlarms();
    void alarmItemChange(AlarmItem alarmItem);
    Cursor alarmItemsGet();
    void addAlarmItem(AlarmItem alarmItem);
    void deleteAlarmItem(int id);
    AlarmItem getAlarmById(int id);
    Cursor songGet();
}
