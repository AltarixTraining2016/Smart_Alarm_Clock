package com.me.ilya.smartalarmclock;

import android.app.Application;

/**
 * Created by Ilya on 6/17/2016.
 */
public class AlarmClockApplication extends Application {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        return dataSource;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        dataSource = MemoryDataSource.getInstance();
    }
}
