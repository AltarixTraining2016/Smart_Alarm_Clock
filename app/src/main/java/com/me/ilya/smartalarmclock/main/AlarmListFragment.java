package com.me.ilya.smartalarmclock.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.ilya.smartalarmclock.AlarmEditActivity;
import com.me.ilya.smartalarmclock.data.AlarmItem;
import com.me.ilya.smartalarmclock.manager.AlarmManagerHelper;
import com.me.ilya.smartalarmclock.R;
import com.me.ilya.smartalarmclock.Titleable;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ilya on 6/15/2016.
 */
public class AlarmListFragment extends Fragment implements Titleable {
    AlarmItem deletedAlarm;
    Adapter adapter;
    @BindView(R.id.next_alarm)
    TextView nextAlarm;
    @BindView(R.id.remain)
    TextView remain;
    @BindView(R.id.next)
    TextView next;
    @BindView(R.id.remain_time)
    TextView remainTime;

    @Override
    public String getTitle(Context context) {
        return "Smart Alarm Clock";
    }

    public static AlarmListFragment create() {
        return new AlarmListFragment();
    }

    RecyclerView alarmListRecycleView;

    public void setRemainings() {
        Calendar calendar = AlarmManagerHelper.getTimeToNext();
        if (calendar == null) {
            nextAlarm.setText(getString(R.string.no_available_alarm));
            remain.setText("");
            remainTime.setText("");
            AlarmManagerHelper.cancelNotification();
        } else {
            next.setText(getString(R.string.next_alarm));
            remain.setText(getString(R.string.remain_time));
            nextAlarm.setText(String.format(getString(R.string.next), calendarDay(calendar.get(Calendar.DAY_OF_WEEK)), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));//TODO
            String[] str;
            StringBuilder sb = new StringBuilder();
            str = AlarmManagerHelper.parseTime(calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()).split(" ");
            if (Integer.parseInt(str[3]) != 0) sb.append(str[3]).append(getString(R.string.days));
            if (Integer.parseInt(str[2]) != 0) sb.append(str[2]).append(getString(R.string.hours));
            if (Integer.parseInt(str[1]) != 0)
                sb.append(str[1]).append(getString(R.string.minutes));
            if (sb.length() < 1) sb.append(getString(R.string.less_minute));//TODO отделить тексты
            remainTime.setText(sb.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setRemainings();
    }

    private String calendarDay(int day) {
        switch (day) {
            case 1:
                return getString(R.string.sunday);
            case 2:
                return getString(R.string.monday);//TODO
            case 3:
                return getString(R.string.tuesday);
            case 4:
                return getString(R.string.wednesday);
            case 5:
                return getString(R.string.thursday);
            case 6:
                return getString(R.string.friday);
            case 7:
                return getString(R.string.saturday);
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ImageView addButton;
        View v = inflater.inflate(R.layout.alarm_list_fragment, container, false);
        ButterKnife.bind(this, v);
        alarmListRecycleView = (RecyclerView) v.findViewById(R.id.alarm_list);
        addButton = (ImageView) v.findViewById(R.id.btn_addAlarm);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(AlarmEditActivity.intent(getContext()), 0);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {


            adapter.swapCursor(AlarmClockApplication.getDataSource().alarmItemsGet());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new Adapter(getContext(), AlarmClockApplication.getDataSource().alarmItemsGet());
        alarmListRecycleView.setAdapter(adapter);
        alarmListRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AlarmItem alarmItem;
        @BindView(R.id.unabled_tv_days)
        TextView unabledDays;
        @BindView(R.id.unabled_tv_name)
        TextView unabledName;


        @BindView(R.id.tv_time_unabled)
        TextView unabledTime;

        @BindView(R.id.turnOffImage)
        OnButton turnOffButton;
        @BindView(R.id.tv_name)
        TextView nameTextView;
        @BindView(R.id.tv_days)
        TextView daysTextView;
        @BindView(R.id.tv_time)
        TextView timeTextView;
        @BindView(R.id.show_context_menu)
        ImageView contextMenuImageView;
        @BindView(R.id.turnOnImage)
        OnButton onButton;

        public ViewHolder(ViewGroup root) {
            super(LayoutInflater.from(getContext()).inflate(R.layout.alarm_custom_item, root, false));
            ButterKnife.bind(this, itemView);

        }


        public void bind(final AlarmItem alarmItem) {
            this.alarmItem = alarmItem;

            if (alarmItem.isEnabled()) {//TODO оптимизировать


                unabledDays.setVisibility(View.INVISIBLE);
                unabledTime.setVisibility(View.INVISIBLE);
                unabledName.setVisibility(View.INVISIBLE);
                turnOffButton.setVisibility(View.INVISIBLE);

                nameTextView.setVisibility(View.VISIBLE);
                daysTextView.setVisibility(View.VISIBLE);
                timeTextView.setVisibility(View.VISIBLE);
                onButton.setVisibility(View.VISIBLE);

                FrameLayout frameLayout = (FrameLayout) itemView.findViewById(R.id.item_back);
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

                    frameLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_item_background));
                } else {
                    frameLayout.setBackground(getResources().getDrawable(R.drawable.list_item_background));
                }

                nameTextView.setText(this.alarmItem.getName());
                timeTextView.setText(String.format(getString(R.string.alarm_time), alarmItem.getTimeHour(), alarmItem.getTimeMinute()));
            } else {


                nameTextView.setVisibility(View.INVISIBLE);
                daysTextView.setVisibility(View.INVISIBLE);
                timeTextView.setVisibility(View.INVISIBLE);
                onButton.setVisibility(View.INVISIBLE);

                unabledDays.setVisibility(View.VISIBLE);
                unabledTime.setVisibility(View.VISIBLE);
                unabledName.setVisibility(View.VISIBLE);
                turnOffButton.setVisibility(View.VISIBLE);
                FrameLayout frameLayout = (FrameLayout) itemView.findViewById(R.id.item_back);
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    frameLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_item_background_unabled));
                } else {
                    frameLayout.setBackground(getResources().getDrawable(R.drawable.list_item_background_unabled));
                }
                unabledName.setText(this.alarmItem.getName());
                unabledTime.setText(String.format(getString(R.string.alarm_time), alarmItem.getTimeHour(), alarmItem.getTimeMinute()));
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(AlarmEditActivity.intent(getContext(), alarmItem.getId()), 0);
                }
            });
            contextMenuImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(getContext(), v);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.menu_alarm_edit, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            deletedAlarm = AlarmClockApplication.getDataSource().getAlarmById(alarmItem.getId());
                            //deletedAlarm.setDays(alarmItem.getDays());
                            AlarmManagerHelper.cancelAlarms(getContext());
                            AlarmClockApplication.getDataSource().deleteAlarmItem(alarmItem.getId());
                            AlarmManagerHelper.setAlarms(getContext());
                            adapter.swapCursor(AlarmClockApplication.getDataSource().alarmItemsGet());
                            setRemainings();
                            Snackbar mSnackBar = Snackbar.make(itemView, "Будильник " + alarmItem.getName() + " удален", Snackbar.LENGTH_LONG);
                            mSnackBar.setAction(getString(R.string.cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlarmClockApplication.getDataSource().createAlarm(deletedAlarm);
                                    deletedAlarm = null;
                                    adapter.swapCursor(AlarmClockApplication.getDataSource().alarmItemsGet());
                                    AlarmManagerHelper.setAlarms(getContext());
                                    setRemainings();
                                }
                            });
                            mSnackBar.show();
                            return true;
                        }
                    });
                }
            });
            setDays();
        }


        private void setDays() {
            StringBuilder stringBuilder = new StringBuilder();
            int everyday = 0;
            for (int i = AlarmItem.MONDAY; i <= AlarmItem.SATURDAY; i++) {
                if (alarmItem.getDay(i)) {
                    everyday++;
                    switch (i) {
                        case 1:
                            stringBuilder.append(getString(R.string.monday)).append(" ");
                            break;
                        case 2:
                            stringBuilder.append(getString(R.string.tuesday)).append(" ");
                            break;
                        case 3:
                            stringBuilder.append(getString(R.string.wednesday)).append(" ");
                            break;
                        case 4:
                            stringBuilder.append(getString(R.string.thursday)).append(" ");
                            break;
                        case 5:
                            stringBuilder.append(getString(R.string.friday)).append(" ");
                            break;
                        case 6:
                            stringBuilder.append(getString(R.string.saturday)).append(" ");
                            break;

                    }
                }
            }
            if (alarmItem.getDay(AlarmItem.SUNDAY)) {
                stringBuilder.append(getString(R.string.sunday)).append(" ");
                everyday++;
            }
            if (everyday == 7) stringBuilder = new StringBuilder(getString(R.string.everyday));
            daysTextView.setText(stringBuilder.toString());
            unabledDays.setText(stringBuilder.toString());
        }
    }

    class Adapter extends CursorAdapter<ViewHolder> {
        //      ViewGroup parent;

        public Adapter(Context context, Cursor cursor) {
            super(context, cursor);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //       this.parent=parent;
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {
            final AlarmItem alarmItem = AlarmItem.fromCursor(cursor);//как
            //      if(alarmItem.isEnabled()){
            //      }
            viewHolder.bind(alarmItem);
            viewHolder.onButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlarmManagerHelper.cancelAlarms(getContext());

                    AlarmItem newAlarmItem = new AlarmItem(alarmItem.getId(), alarmItem.getName(), alarmItem.getTimeHour(), alarmItem.getTimeMinute(), alarmItem.getSong(), alarmItem.getDays(), !alarmItem.isEnabled(), alarmItem.isTone());
                    AlarmClockApplication.getDataSource().alarmItemChange(newAlarmItem);
                    swapCursor(AlarmClockApplication.getDataSource().alarmItemsGet());
                    AlarmManagerHelper.setAlarms(getContext());
                    setRemainings();
                }
            });
            viewHolder.turnOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmManagerHelper.cancelAlarms(getContext());
                    AlarmItem newAlarmItem = new AlarmItem(alarmItem.getId(), alarmItem.getName(), alarmItem.getTimeHour(), alarmItem.getTimeMinute(), alarmItem.getSong(), alarmItem.getDays(), !alarmItem.isEnabled(), alarmItem.isTone());
                    AlarmClockApplication.getDataSource().alarmItemChange(newAlarmItem);
                    swapCursor(AlarmClockApplication.getDataSource().alarmItemsGet());

                    String[] str;
                    StringBuilder sb = new StringBuilder();
                    sb.append(getString(R.string.next_alarm_toast));//TODO спросить -1
                    str = AlarmManagerHelper.setAlarms(getContext(), alarmItem.getId()).split(" ");
                    setRemainings();
                    if (Double.parseDouble(str[3]) != 0)
                        sb.append(str[3]).append(getString(R.string.days));
                    if (Double.parseDouble(str[2]) != 0)
                        sb.append(str[2]).append(getString(R.string.hours));
                    if (Double.parseDouble(str[1]) != 0)
                        sb.append(str[1]).append(getString(R.string.minutes));
                    if (Double.parseDouble(str[0]) != 0)
                        sb.append(str[0]).append(getString(R.string.seconds));
                    Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
