package com.me.ilya.smartalarmclock;

import android.database.Cursor;

import com.me.ilya.smartalarmclock.music.Song;

/**
 * Created by Ilya on 6/17/2016.
 */
public interface DataSource {
    void alarmItemChange(AlarmItem alarmItem);
    Cursor alarmItemsGet();
    void addAlarmItem(AlarmItem alarmItem);
    void deleteAlarmItem(int id);
    AlarmItem getAlarmById(int id);
    Cursor songGet();
}
