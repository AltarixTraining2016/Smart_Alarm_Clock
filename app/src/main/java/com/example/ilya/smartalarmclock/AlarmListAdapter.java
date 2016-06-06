package com.example.ilya.smartalarmclock;

import android.content.Context;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ilya on 6/6/2016.
 */
public class AlarmListAdapter extends BaseAdapter {
   private Context nContext;
    private List<AlarmItem> nAlarmList;

    public AlarmListAdapter(Context nContext, List<AlarmItem> nAlarmList) {
        this.nContext = nContext;
        this.nAlarmList = nAlarmList;
    }

    @Override
    public int getCount() {
        return nAlarmList.size();
    }

    @Override
    public Object getItem(int position) {
        return nAlarmList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(nContext,R.layout.alarm_custom_item,null);
        TextView tvName=(TextView) v.findViewById(R.id.tv_name);
        TextView tvTime=(TextView) v.findViewById(R.id.tv_time);
        tvName.setText(nAlarmList.get(position).getName());
        tvName.setText(nAlarmList.get(position).getTime());
        v.setTag(nAlarmList.get(position).getID());
        return v;
    }
}
