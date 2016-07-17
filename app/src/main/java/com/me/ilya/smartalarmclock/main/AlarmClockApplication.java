package com.me.ilya.smartalarmclock.main;

import android.app.Application;
import android.media.RingtoneManager;

import com.me.ilya.smartalarmclock.data.AlarmDBHelper;
import com.me.ilya.smartalarmclock.data.DataSource;
import com.me.ilya.smartalarmclock.music.Song;

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
        Song.setDefaultUri(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString());
        dataSource =new AlarmDBHelper(getApplicationContext());
    }
}
