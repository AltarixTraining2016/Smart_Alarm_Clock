package com.me.ilya.smartalarmclock;

import android.database.Cursor;
import android.database.MatrixCursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ilya on 6/17/2016.
 */
public class MemoryDataSource implements DataSource {

    private static final MemoryDataSource instance = new MemoryDataSource();

    public static MemoryDataSource getInstance() {
        return instance;
    }

    private List<AlarmItem> alarmItemList = new ArrayList<>();

    private MemoryDataSource() {
       alarmItemList.add(new AlarmItem(1,"a",new Date(100)));
       alarmItemList.add(new AlarmItem(2,"a",new Date(100)));
       alarmItemList.add(new AlarmItem(3,"a",new Date(100)));
       alarmItemList.add(new AlarmItem(4,"a",new Date(100)));
    }


    @Override
    public Cursor getAlarmItems() {
        MatrixCursor c = new MatrixCursor(AlarmItem.COLUMN_NAMES);
        c.addRow(new Object[]{"1","aaa","10"});
        c.addRow(new Object[]{"2","aaea","10"});
        c.addRow(new Object[]{"2","aaea","10"});
        c.addRow(new Object[]{"2","aaea","10"});
        return c;
    }

    @Override
    public void addAlarmItem(AlarmItem alarmItem) {

    }

    @Override
    public void deleteAlarmItem(int id) {

    }
}
