package com.me.ilya.smartalarmclock;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.me.ilya.smartalarmclock.music.Song;

import java.util.ArrayList;
import java.util.Iterator;
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
    private List<Song> songList=new ArrayList<>();
    private MemoryDataSource() {
       alarmItemList.add(new AlarmItem(0,"Работа",  13,45,Song.DEFAULT()));
       alarmItemList.add(new AlarmItem(1,"выходной",13,45,Song.DEFAULT()));
       alarmItemList.add(new AlarmItem(2,"",        13,45,Song.DEFAULT()));
       alarmItemList.add(new AlarmItem(3,"",        13,45,Song.DEFAULT()));
    }


    @Override
    public void alarmItemChange(AlarmItem alarmItem) {
            AlarmItem newAlarmItem = null;
            if (alarmItem.getId() != -1) {
                for (Iterator<AlarmItem> iterator =alarmItemList.iterator(); iterator.hasNext(); ) {
                    AlarmItem d = iterator.next();
                    if (d.getId() == alarmItem.getId()) {
                        iterator.remove();
                        if(alarmItem.getSong()!=null){
                            newAlarmItem = new AlarmItem(d.getId(), alarmItem.getName(),alarmItem.getTimeHour(),alarmItem.getTimeMinute(),alarmItem.getSong(),alarmItem.getDays(),alarmItem.isEnabled());
                        }

                      else
                            newAlarmItem = new AlarmItem(d.getId(), alarmItem.getName(),alarmItem.getTimeHour(),alarmItem.getTimeMinute(),alarmItem.isEnabled());
                        break;
                    }
                }
                if (newAlarmItem == null) {
                    newAlarmItem = new AlarmItem(alarmItem.getId(), alarmItem.getName(),alarmItem.getTimeHour(),alarmItem.getTimeMinute(),alarmItem.getSong(),alarmItem.getDays(),alarmItem.isEnabled());

                }
            }
            if (newAlarmItem == null) {
                newAlarmItem = new AlarmItem(alarmItemList.size(), alarmItem.getName(),alarmItem.getTimeHour(),alarmItem.getTimeMinute(),alarmItem.getSong(),alarmItem.getDays(),alarmItem.isEnabled());
            }
            alarmItemList.add(newAlarmItem);
    }

    @Override
    public Cursor alarmItemsGet() {
         final MatrixCursor c = new MatrixCursor(AlarmItem.COLUMN_NAMES);
       for(AlarmItem alarmItem  :alarmItemList){
           alarmItem.addToCursor(c);
       }
        return c;
    }

    @Override
    public void addAlarmItem(AlarmItem alarmItem) {

    }

    @Override
    public void deleteAlarmItem(int id) {
        for (Iterator<AlarmItem> iterator = alarmItemList.iterator(); iterator.hasNext(); ) {
            AlarmItem alarmItem= iterator.next();
            if (alarmItem.getId() == id) {
                iterator.remove();
            }
        }
    }

    @Override
    public AlarmItem getAlarmById(int id) {
        AlarmItem result = null;
        for (AlarmItem alarmItem : alarmItemList) {
            if (alarmItem.getId() == id) {
                result = alarmItem;
            }
        }
        return result;
    }

    @Override
    public Cursor songGet() {
            MatrixCursor c = new MatrixCursor(Song.COLUMN_NAMES);
            for (Song metering : songList) {
                metering.addToCursor(c);
            }
            return c;

    }
}
