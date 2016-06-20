package com.me.ilya.smartalarmclock.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.ilya.smartalarmclock.AlarmClockApplication;
import com.me.ilya.smartalarmclock.AlarmItem;
import com.me.ilya.smartalarmclock.CursorAdapter;
import com.me.ilya.smartalarmclock.R;
import com.me.ilya.smartalarmclock.Titleable;

/**
 * Created by Ilya on 6/15/2016.
 */
public class AlarmListFragment  extends Fragment implements Titleable {

    @Override
    public String getTitle(Context context) {
        return null;
    }
    public static AlarmListFragment create() {
        return new AlarmListFragment();
    }
    RecyclerView alarmListRecycleView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alarm_list_fragment, container, false);
       alarmListRecycleView=(RecyclerView)v.findViewById(R.id.alarm_list);
        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Adapter adapter = new Adapter(getContext(), AlarmClockApplication.getDataSource().getAlarmItems());
        alarmListRecycleView.setAdapter(adapter);
      alarmListRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView daysTextView;
        TextView timeTextView;
        ImageView contextMenuImageView;
        ImageView turnOnButton;
        public ViewHolder(ViewGroup root) {
            super(LayoutInflater.from(getContext()).inflate(R.layout.alarm_custom_item, root, false));
            nameTextView=(TextView) itemView.findViewById(R.id.tv_name);
            daysTextView=(TextView) itemView.findViewById(R.id.tv_days);
            timeTextView=(TextView) itemView.findViewById(R.id.tv_time);
            contextMenuImageView=(ImageView) itemView.findViewById(R.id.show_context_menu);
            turnOnButton=(ImageView) itemView.findViewById(R.id.turnOnImage);

        }

        public void bind(final AlarmItem alarmItem) {
            nameTextView.setText(alarmItem.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //    startActivity(DeviceTypeEditActivity.intent(getContext(), deviceType));
                }
            });
            contextMenuImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(getContext(), v);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.menu_alarm_edit, popup.getMenu());
                    popup.show();
                }
            });
        }

    }
    class Adapter extends CursorAdapter<ViewHolder> {

        public Adapter(Context context, Cursor cursor) {
            super(context, cursor);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
           AlarmItem alarmItem= AlarmItem.fromCursor(cursor);
            viewHolder.bind(alarmItem);
        }

    }

}
