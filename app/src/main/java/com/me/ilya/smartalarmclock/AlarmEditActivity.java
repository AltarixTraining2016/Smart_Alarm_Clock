package com.me.ilya.smartalarmclock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ilya on 6/17/2016.
 */
public class AlarmEditActivity  extends AppCompatActivity {

    public static Intent intent(Context context) {
        return new Intent(context, AlarmEditActivity.class);
    }
  // public static Intent intent(Context context, AlarmItem deviceType) {
  //     return new Intent(context,AlarmEditActivity.class).putExtra(EXTRA_DEVICE_TYPE, deviceType);
  // }
}
