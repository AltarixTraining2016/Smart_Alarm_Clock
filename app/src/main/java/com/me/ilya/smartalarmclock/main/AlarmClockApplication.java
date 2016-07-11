package com.me.ilya.smartalarmclock.main;

import android.app.Application;

import com.me.ilya.smartalarmclock.AlarmDBHelper;
import com.me.ilya.smartalarmclock.AlarmItem;
import com.me.ilya.smartalarmclock.DataSource;
import com.me.ilya.smartalarmclock.MemoryDataSource;
import com.me.ilya.smartalarmclock.music.Song;

import java.util.ArrayList;

/**
 * Created by Ilya on 6/17/2016.
 */
public class AlarmClockApplication extends Application {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        return dataSource;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        dataSource =new AlarmDBHelper(getApplicationContext());
    }
}
