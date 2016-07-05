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
import com.me.ilya.smartalarmclock.AlarmItem;
import com.me.ilya.smartalarmclock.CursorAdapter;
import com.me.ilya.smartalarmclock.R;
import com.me.ilya.smartalarmclock.Titleable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ilya on 6/15/2016.
 */
public class AlarmListFragment extends Fragment implements Titleable {
    AlarmItem deletedAlarm;
    Adapter adapter;

    @Override
    public String getTitle(Context context) {
        return "Alarm Clock Application";
    }

    public static AlarmListFragment create() {
        return new AlarmListFragment();
    }

    RecyclerView alarmListRecycleView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ImageView addButton;
        View v = inflater.inflate(R.layout.alarm_list_fragment, container, false);
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




            Toast.makeText(getContext(),"")
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
                            deletedAlarm.setDays(alarmItem.getDays());
                            AlarmClockApplication.getDataSource().deleteAlarmItem(alarmItem.getId());
                            adapter.swapCursor(AlarmClockApplication.getDataSource().alarmItemsGet());
                            Snackbar mSnackBar = Snackbar.make(itemView, "Будильник " + alarmItem.getName() + " удален", Snackbar.LENGTH_LONG);
                            mSnackBar.setAction("Отменить", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlarmClockApplication.getDataSource().alarmItemChange(deletedAlarm);
                                    deletedAlarm = null;
                                    adapter.swapCursor(AlarmClockApplication.getDataSource().alarmItemsGet());
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
            for (int i = AlarmItem.MONDAY; i <= AlarmItem.SUNDAY; i++) {
                if (alarmItem.getDay(i)) {
                    switch (i) {
                        case 0:
                            stringBuilder.append(getString(R.string.monday)).append(" ");
                            break;
                        case 1:
                            stringBuilder.append(getString(R.string.tuesday)).append(" ");
                            break;
                        case 2:
                            stringBuilder.append(getString(R.string.wednesday)).append(" ");
                            break;
                        case 3:
                            stringBuilder.append(getString(R.string.thursday)).append(" ");
                            break;
                        case 4:
                            stringBuilder.append(getString(R.string.friday)).append(" ");
                            break;
                        case 5:
                            stringBuilder.append(getString(R.string.saturday)).append(" ");
                            break;
                        case 6:
                            stringBuilder.append(getString(R.string.sunday)).append(" ");
                            break;
                    }
                }
            }
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
                    AlarmItem newAlarmItem = new AlarmItem(alarmItem.getId(), alarmItem.getName(), alarmItem.getTimeHour(), alarmItem.getTimeMinute(), alarmItem.getSong(), alarmItem.getDays(), !alarmItem.isEnabled());
                    AlarmClockApplication.getDataSource().alarmItemChange(newAlarmItem);
                    swapCursor(AlarmClockApplication.getDataSource().alarmItemsGet());
                }
            });
            viewHolder.turnOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmItem newAlarmItem = new AlarmItem(alarmItem.getId(), alarmItem.getName(), alarmItem.getTimeHour(), alarmItem.getTimeMinute(), alarmItem.getSong(), alarmItem.getDays(), !alarmItem.isEnabled());
                    AlarmClockApplication.getDataSource().alarmItemChange(newAlarmItem);
                    swapCursor(AlarmClockApplication.getDataSource().alarmItemsGet());
                }
            });
        }
    }

}
