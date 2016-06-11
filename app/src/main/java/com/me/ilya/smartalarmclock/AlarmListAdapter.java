package com.me.ilya.smartalarmclock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ilya on 6/6/2016.
 */
public class AlarmListAdapter extends BaseAdapter {
   private Context nContext;
    private List<AlarmItem> nAlarmList;
    CustomButtonListener customButtonListener;


    public AlarmListAdapter(Context nContext, List<AlarmItem> nAlarmList) {
        this.nContext = nContext;
        this.nAlarmList = nAlarmList;
    }

    public void setCustomButtonListener(CustomButtonListener customButtonListner)
    {
        this.customButtonListener = customButtonListner;
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
      //  View row;
      //  final AlarmItem alarmItem;
      //  if(convertView==null){
      //     LayoutInflater layoutInflater =LayoutInflater.from(getCo)
      //     row = layoutInflater.inflate(R.layout.alarm_custom_item,parent,false);
      //     alarmItem=new AlarmItem();
      //     alarmItem.setName();
      //     listViewHolder.tvFruitName = (TextView) row.findViewById(R.id.tvFruitName);
      //     listViewHolder.ivFruit= (ImageView) row.findViewById(R.id.ivFruit);
      //     listViewHolder.tvPrices = (TextView) row.findViewById(R.id.tvFruitPrice);
      //     listViewHolder.btnPlus = (ImageButton) row.findViewById(R.id.ib_addnew);
      //     listViewHolder.edTextQuantity = (EditText) row.findViewById(R.id.editTextQuantity);
      //     listViewHolder.btnMinus = (ImageButton) row.findViewById(R.id.ib_remove);
      //     row.setTag(listViewHolder);

      View v=View.inflate(nContext,R.layout.alarm_custom_item,null);
    TextView tvName=(TextView) v.findViewById(R.id.tv_name);
    TextView tvTime=(TextView) v.findViewById(R.id.tv_time);
    tvName.setText(nAlarmList.get(position).getName());
    tvTime.setText(nAlarmList.get(position).getTime());
    ImageButton imageButton=(ImageButton)v.findViewById(R.id.ib_config);
    imageButton.setTag(nAlarmList.get(position).getID());
    v.setTag(nAlarmList.get(position).getID());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(nContext,"button",Toast.LENGTH_SHORT).show();
            }
        });
     return v;
    }
}
