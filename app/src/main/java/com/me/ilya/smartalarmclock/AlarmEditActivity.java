package com.me.ilya.smartalarmclock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.me.ilya.smartalarmclock.music.SongListActivity;

/**
 * Created by Ilya on 6/17/2016.
 */
public class AlarmEditActivity  extends AppCompatActivity {
    TimePicker tp;
    private final static String EXTRA_ALARM_ID= "alarm_id";
    public static Intent intent(Context context) {
        return new Intent(context, AlarmEditActivity.class);
    }
   public static Intent intent(Context context,int alarmId) {
       Intent intent = new Intent(context, AlarmEditActivity.class);
       intent.putExtra(EXTRA_ALARM_ID, alarmId);
       return intent;
   }

    private int alarmId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_edit_activity);
        ImageView imageView=(ImageView)findViewById(R.id.song_list);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SongListActivity.intent(getBaseContext()));
            }
        });
       tp=(TimePicker)findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
    }

}
