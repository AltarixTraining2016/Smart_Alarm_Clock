package com.me.ilya.smartalarmclock.main;

import android.app.Application;

import com.me.ilya.smartalarmclock.DataSource;
import com.me.ilya.smartalarmclock.MemoryDataSource;

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
        dataSource = MemoryDataSource.getInstance();
    }
}
