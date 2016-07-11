package com.me.ilya.smartalarmclock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.me.ilya.smartalarmclock.main.AlarmClockApplication;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ilya on 7/4/2016.
 */
public class AlarmManagerHelper extends BroadcastReceiver {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TIME_HOUR = "timeHour";
    public static final String TIME_MINUTE = "timeMinute";
    public static final String TONE = "alarmTone";
    private static Calendar timeToNext;

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    public static String setAlarms(Context context) {
        cancelAlarms(context);

        List<AlarmItem> alarms = AlarmClockApplication.getDataSource().getAlarms();
        if (alarms != null) {
            for (AlarmItem alarm : alarms) {
                if (alarm.isEnabled()) {

                    PendingIntent pIntent = createPendingIntent(context, alarm);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, alarm.getTimeHour());
                    calendar.set(Calendar.MINUTE, alarm.getTimeMinute());
                    calendar.set(Calendar.SECOND, 00);
                    boolean alarmSet = false;
                    if (!calendar.after(Calendar.getInstance())) calendar.add(Calendar.DATE, 1);
                    while (!alarmSet) {
                        for (int i = 0; i <= 6; i++) {
                            if (alarm.getDay(calendar.get(Calendar.DAY_OF_WEEK) - 1)) {
                                setAlarm(context, calendar, pIntent);
                                alarmSet = true;
                            } else calendar.add(Calendar.DATE, 1);
                        }
                    }

                    Calendar today = Calendar.getInstance();
                    long diffInMillisec = calendar.getTimeInMillis() - today.getTimeInMillis();//result in millis
                    if (timeToNext == null || calendar.getTimeInMillis() < timeToNext.getTimeInMillis()) {
                        timeToNext = calendar;
                    }
                }
            }
        }
        return null;
    }

    public static String setAlarms(Context context, long id) {
        cancelAlarms(context);

        String str = "";
        List<AlarmItem> alarms = AlarmClockApplication.getDataSource().getAlarms();

        for (AlarmItem alarm : alarms) {
            if (alarm.isEnabled()) {
                PendingIntent pIntent = createPendingIntent(context, alarm);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarm.getTimeHour());
                calendar.set(Calendar.MINUTE, alarm.getTimeMinute());
                calendar.set(Calendar.SECOND, 00);
                boolean alarmSet = false;
                if (!calendar.after(Calendar.getInstance())) calendar.add(Calendar.DATE, 1);
                while (!alarmSet) {
                    for (int i = 0; i <= 6; i++) {
                        if (alarm.getDay(calendar.get(Calendar.DAY_OF_WEEK) - 1)) {
                            setAlarm(context, calendar, pIntent);
                            alarmSet = true;
                        } else calendar.add(Calendar.DATE, 1);
                    }
                }

                Calendar today = Calendar.getInstance();
                long diffInMillisec = calendar.getTimeInMillis() - today.getTimeInMillis();//result in millis

                if (alarm.getId() == id) {
                    str = parseTime(diffInMillisec);
                }
                if (timeToNext == null || calendar.getTimeInMillis() < timeToNext.getTimeInMillis()) {
                    timeToNext = calendar;
                }
            }
        }
        return str;//"seconds minutes hours days" invert
    }

    public static String parseTime(long diffInMillisec) {
        StringBuilder sb = new StringBuilder();
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);
        long seconds = diffInSec % 60;
        diffInSec /= 60;
        long minutes = diffInSec % 60;
        diffInSec /= 60;
        long hours = diffInSec % 24;
        diffInSec /= 24;
        long days = diffInSec;
        sb.append(seconds);
        if (minutes != 0) sb.append(" ").append(minutes);
        if (hours != 0) sb.append(" ").append(hours);
        if (days != 0) sb.append(" ").append(days);
        return sb.toString();
    }

    public static Calendar getTimeToNext() {
        return timeToNext;
    }

    @SuppressLint("NewApi")
    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }

    public static void cancelAlarms(Context context) {
        timeToNext = null;
        List<AlarmItem> alarms = AlarmClockApplication.getDataSource().getAlarms();

        if (alarms != null) {
            for (AlarmItem alarm : alarms) {
                if (alarm.isEnabled()) {
                    PendingIntent pIntent = createPendingIntent(context, alarm);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
                }
            }
        }
    }

    private static PendingIntent createPendingIntent(Context context, AlarmItem alarmItem) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(ID, alarmItem.getId());
        intent.putExtra(NAME, alarmItem.getName());
        intent.putExtra(TIME_HOUR, alarmItem.getTimeHour());
        intent.putExtra(TIME_MINUTE, alarmItem.getTimeMinute());
        intent.putExtra(TONE, alarmItem.getSong());

        return PendingIntent.getService(context, (int) alarmItem.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}