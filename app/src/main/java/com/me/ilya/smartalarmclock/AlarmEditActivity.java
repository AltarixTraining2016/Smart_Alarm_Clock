package com.me.ilya.smartalarmclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.me.ilya.smartalarmclock.main.AlarmClockApplication;
import com.me.ilya.smartalarmclock.music.Song;
import com.me.ilya.smartalarmclock.music.SongListActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ilya on 6/17/2016.
 */
public class AlarmEditActivity extends AppCompatActivity {
    AlarmItem alarmItem;
    Song song;
    @BindView(R.id.chk_mond)
    DaySwitcher chekMonday;
    @BindView(R.id.chk_tue)
    DaySwitcher chekTuesday;
    @BindView(R.id.chk_wed)
    DaySwitcher chekWednesday;
    @BindView(R.id.chk_thur)
    DaySwitcher chekThursday;
    @BindView(R.id.chk_frid)
    DaySwitcher chekFriday;
    @BindView(R.id.chk_sat)
    DaySwitcher chekSaturday;
    @BindView(R.id.chk_sun)
    DaySwitcher chekSunday;

    @BindView(R.id.alarm_name)
    EditText alarmNameEditText;
    TimePicker tp;
    @BindView(R.id.accept_button)
    ImageView acceptButton;
    private final static String EXTRA_ALARM_ID = "alarm_id";

    public static Intent intent(Context context) {
        return new Intent(context, AlarmEditActivity.class);
    }

    public static Intent intent(Context context, int alarmId) {
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
        if (resultCode == Activity.RESULT_OK) {
           song= (Song) data.getSerializableExtra("song");
            editSongName.setText(song.getName());
        }
        //if(AlarmClockApplication.getDataSource().getAlarmById(alarmId).getSong()!=null)
        //editSongName.setText(AlarmClockApplication.getDataSource().getAlarmById(alarmId).getSong().getName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alarm_edit_activity);

        ButterKnife.bind(this);
        alarmId = getIntent().getIntExtra(EXTRA_ALARM_ID, -1);//notSet
        tp = (TimePicker) findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
        if (alarmId != -1) {
            alarmItem = AlarmClockApplication.getDataSource().getAlarmById(alarmId);
            if (alarmItem != null) {
                song=alarmItem.getSong();
                alarmNameEditText.setText(alarmItem.getName());
                tp.setCurrentMinute(alarmItem.getTimeMinute());
                tp.setCurrentHour(alarmItem.getTimeHour());
                editSongName.setText(alarmItem.getSong().getName());
            }
        } else {
            alarmItem = new AlarmItem(alarmId, "", tp.getCurrentHour(), tp.getCurrentMinute(), Song.DEFAULT());
            song = Song.DEFAULT();
            editSongName.setText("Default(" + RingtoneManager.getRingtone(this, Uri.parse(Song.DEFAULT_URI)).getTitle(this) + ")");

        }
        setDays();
        ImageView imageView = (ImageView) findViewById(R.id.song_list);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct();
            }
        });

    }
    private void setDays(){
        chekMonday.setChecked(alarmItem.getDay(AlarmItem.MONDAY));
        chekTuesday.setChecked(alarmItem.getDay(AlarmItem.TUESDAY));
        chekWednesday.setChecked(alarmItem.getDay(AlarmItem.WEDNESDAY));
        chekThursday.setChecked(alarmItem.getDay(AlarmItem.THURSDAY));
        chekFriday.setChecked(alarmItem.getDay(AlarmItem.FRDIAY));
        chekSaturday.setChecked(alarmItem.getDay(AlarmItem.SATURDAY));
        chekSunday.setChecked(alarmItem.getDay(AlarmItem.SUNDAY));
    }
    private void startAct() {
        startActivityForResult(SongListActivity.intent(AlarmEditActivity.this, alarmId), 0);
    }


    @OnClick(R.id.accept_button)
    public void accept() {

         AlarmItem newAlarmItem = new AlarmItem(alarmId, alarmNameEditText.getText().toString(), tp.getCurrentHour(), tp.getCurrentMinute(), song);
        newAlarmItem.setDay(AlarmItem.MONDAY, chekMonday.isChecked());
        newAlarmItem.setDay(AlarmItem.TUESDAY, chekTuesday.isChecked());
        newAlarmItem.setDay(AlarmItem.WEDNESDAY, chekWednesday.isChecked());
        newAlarmItem.setDay(AlarmItem.THURSDAY, chekThursday.isChecked());
        newAlarmItem.setDay(AlarmItem.FRDIAY, chekFriday.isChecked());
        newAlarmItem.setDay(AlarmItem.SATURDAY, chekSaturday.isChecked());
        newAlarmItem.setDay(AlarmItem.SUNDAY, chekSunday.isChecked());
        AlarmClockApplication.getDataSource().alarmItemChange(newAlarmItem);


        final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)   ;
        final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
        boolean alarmSet = false;

        for (int dayOfWeek = 1; dayOfWeek <= 7; ++dayOfWeek) {
            if (alarmItem.getDay(dayOfWeek - 1) && dayOfWeek >= nowDay &&
                    !(dayOfWeek == nowDay && alarmItem.getTimeHour() < nowHour) &&
                    !(dayOfWeek == nowDay && alarmItem.getTimeHour() == nowHour && alarmItem.getTimeMinute() <= nowMinute)) {
                 calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                setAlarm(context, calendar, pIntent);
                alarmSet = true;
                break;
            }
        }

        //Else check if it's earlier in the week
        if (!alarmSet) {
            for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                if (alarmItem.getDay(dayOfWeek - 1) && dayOfWeek <= nowDay){
                    calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                    setAlarm(context, calendar, pIntent);
                    alarmSet = true;
                    break;
                }
            }
        }



        Toast.makeText(this,"lala",Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }
}
