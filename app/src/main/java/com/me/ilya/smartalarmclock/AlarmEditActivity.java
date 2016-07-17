package com.me.ilya.smartalarmclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.me.ilya.smartalarmclock.data.AlarmItem;
import com.me.ilya.smartalarmclock.main.AlarmClockApplication;
import com.me.ilya.smartalarmclock.manager.AlarmManagerHelper;
import com.me.ilya.smartalarmclock.music.Song;
import com.me.ilya.smartalarmclock.music.SongListActivity;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ilya on 6/17/2016.
 */
public class AlarmEditActivity extends AppCompatActivity {
    AlarmItem alarmItem;
    String song;
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
    @BindView(R.id.signal_radio_button)
    RadioButton signalRadioButton;
    @BindView(R.id.edit_signal_name)
    TextView signalName;
    @BindView(R.id.song_radio_button)
    RadioButton songRadioButton;
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
            switch (requestCode) {
                case 1:
                    song = (String) data.getSerializableExtra("song");
                    editSongName.setText(new File(song).getName());
                    signalName.setText("");
                    setSongRadioButton();
                    alarmItem = new AlarmItem(alarmId, alarmItem.getName(), alarmItem.getTimeHour(), alarmItem.getTimeMinute(), song, alarmItem.getDays(), alarmItem.isEnabled(), false);
                    break;
                case 2:
                    try {
                        song = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI).toString();
                    } catch (NullPointerException e) {
                        song=Song.DEFAULT_URI;
                    }
                    editSongName.setText("");
                    alarmItem = new AlarmItem(alarmId, alarmItem.getName(), alarmItem.getTimeHour(), alarmItem.getTimeMinute(), song, alarmItem.getDays(), alarmItem.isEnabled(), true);
                    signalName.setText(RingtoneManager.getRingtone(this, (Uri) data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)).getTitle(this));
                    setSignalRadioButton();
                    break;
            }
        }

        //if(AlarmClockApplication.getDataSource().getAlarmById(alarmId).getSong()!=null)
        //editSongName.setText(AlarmClockApplication.getDataSource().getAlarmById(alarmId).getSong().getName());
    }

    public void setSignalRadioButton() {
        signalRadioButton.setChecked(true);
        songRadioButton.setChecked(false);
    }

    public void setSongRadioButton() {
        songRadioButton.setChecked(true);
        signalRadioButton.setChecked(false);
    }

    @OnClick(R.id.cancel_button)
    public void cancelClick() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_edit_activity);

        ButterKnife.bind(this);
        chekMonday.setText(getString(R.string.monday));
        chekTuesday.setText(getString(R.string.tuesday));
        chekWednesday.setText(getString(R.string.wednesday));
        chekThursday.setText(getString(R.string.thursday));
        chekFriday.setText(getString(R.string.friday));
        chekSaturday.setText(getString(R.string.saturday));
        chekSunday.setText(getString(R.string.sunday));
        signalRadioButton.setClickable(false);
        songRadioButton.setClickable(false);
        alarmId = getIntent().getIntExtra(EXTRA_ALARM_ID, -1);//notSet
        tp = (TimePicker) findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
        if (alarmId != -1) {
            alarmItem = AlarmClockApplication.getDataSource().getAlarmById(alarmId);
            if (alarmItem != null) {
                alarmNameEditText.setText(alarmItem.getName());
                tp.setCurrentMinute(alarmItem.getTimeMinute());
                tp.setCurrentHour(alarmItem.getTimeHour());

                if (alarmItem.isTone()) {
                    signalName.setText(RingtoneManager.getRingtone(this, Uri.parse(alarmItem.getSong())).getTitle(this));
                    setSignalRadioButton();
                    song = alarmItem.getSong();
                    editSongName.setText("");
                } else {
                    song = alarmItem.getSong();
                    signalName.setText("");
                    editSongName.setText(new File(alarmItem.getSong()).getName());
                    setSongRadioButton();
                }
            }
        } else {
            alarmItem = new AlarmItem(alarmId, "", tp.getCurrentHour(), tp.getCurrentMinute(), Song.DEFAULT().getUri(), true);
            song = Song.DEFAULT().getUri();
            signalName.setText(RingtoneManager.getRingtone(this, Uri.parse(song)).getTitle(this));
            editSongName.setText("");
            setSignalRadioButton();
        }
        setDays();


    }

    @OnClick(R.id.song_button)
    public void songButton() {
        startActivityForResult(SongListActivity.intent(AlarmEditActivity.this, alarmId), 1);
    }

    @OnClick(R.id.signal_button)
    public void signalButton() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        startActivityForResult(intent, 2);
    }

    private void setDays() {
        chekMonday.setChecked(alarmItem.getDay(AlarmItem.MONDAY));
        chekTuesday.setChecked(alarmItem.getDay(AlarmItem.TUESDAY));
        chekWednesday.setChecked(alarmItem.getDay(AlarmItem.WEDNESDAY));
        chekThursday.setChecked(alarmItem.getDay(AlarmItem.THURSDAY));
        chekFriday.setChecked(alarmItem.getDay(AlarmItem.FRDIAY));
        chekSaturday.setChecked(alarmItem.getDay(AlarmItem.SATURDAY));
        chekSunday.setChecked(alarmItem.getDay(AlarmItem.SUNDAY));
    }


    @OnClick(R.id.accept_button)
    public void accept() {

        AlarmItem newAlarmItem = new AlarmItem(alarmId, alarmNameEditText.getText().toString(), tp.getCurrentHour(), tp.getCurrentMinute(), song, alarmItem.isTone());
        newAlarmItem.setDay(AlarmItem.MONDAY, chekMonday.isChecked());
        newAlarmItem.setDay(AlarmItem.TUESDAY, chekTuesday.isChecked());
        newAlarmItem.setDay(AlarmItem.WEDNESDAY, chekWednesday.isChecked());
        newAlarmItem.setDay(AlarmItem.THURSDAY, chekThursday.isChecked());
        newAlarmItem.setDay(AlarmItem.FRDIAY, chekFriday.isChecked());
        newAlarmItem.setDay(AlarmItem.SATURDAY, chekSaturday.isChecked());
        newAlarmItem.setDay(AlarmItem.SUNDAY, chekSunday.isChecked());
        long newId = 0;
        if (alarmId == -1) newId = AlarmClockApplication.getDataSource().createAlarm(newAlarmItem);
        else {
            AlarmClockApplication.getDataSource().alarmItemChange(newAlarmItem);
            newId = alarmId;
        }

       String[] str;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.next_alarm_toast));//TODO спросить -1ь
        str = AlarmManagerHelper.setAlarms(this, newId).split(" ");

        if (Integer.parseInt(str[3]) != 0) sb.append(str[3]).append(getString(R.string.days));
        if (Integer.parseInt(str[2]) != 0) sb.append(str[2]).append(getString(R.string.hours));
        if (Integer.parseInt(str[1]) != 0) sb.append(str[1]).append(getString(R.string.minutes));
        if (Integer.parseInt(str[0]) != 0) sb.append(str[0]).append(getString(R.string.seconds));
        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }
}
