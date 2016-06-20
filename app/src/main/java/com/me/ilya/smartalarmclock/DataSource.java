package com.me.ilya.smartalarmclock;

import android.database.Cursor;

/**
 * Created by Ilya on 6/17/2016.
 */
public interface DataSource {
    Cursor getAlarmItems();
    void addAlarmItem(AlarmItem alarmItem);
    void deleteAlarmItem(int id);
}
