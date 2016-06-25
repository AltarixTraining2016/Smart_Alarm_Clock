package com.me.ilya.smartalarmclock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.me.ilya.smartalarmclock.music.Song;
import com.me.ilya.smartalarmclock.music.SongListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ilya on 6/17/2016.
 */
public class AlarmEditActivity  extends AppCompatActivity {
    @BindView(R.id.alarm_name)
    EditText alarmNameEditText;
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
@BindView(R.id.edit_song_name)
    TextView editSongName;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(AlarmClockApplication.getDataSource().getAlarmById(alarmId).getSong()!=null)
        editSongName.setText(AlarmClockApplication.getDataSource().getAlarmById(alarmId).getSong().getName());
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alarm_edit_activity);

        ButterKnife.bind(this);
        alarmId=getIntent().getIntExtra(EXTRA_ALARM_ID,-1);//notSet
        tp=(TimePicker)findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
        if (alarmId!= -1) {
            AlarmItem   alarmItem = AlarmClockApplication.getDataSource().getAlarmById(alarmId);
            if (alarmItem != null) {
               alarmNameEditText.setText(alarmItem.getName());
                tp.setCurrentMinute(alarmItem.getTimeMinute());
                tp.setCurrentHour(alarmItem.getTimeHour());
            }
        }
        ImageView imageView=(ImageView)findViewById(R.id.song_list);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startAct();
            }
        });

    }
    private void startAct(){
        startActivityForResult(SongListActivity.intent(AlarmEditActivity.this,alarmId),0 );
    }

}
