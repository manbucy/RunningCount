package net.manbucy.runningcount.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.manbucy.runningcount.R;
import net.manbucy.runningcount.modle.vo.LapInfo;

import java.util.List;

/**
 * LapList的数据适配器
 * Created by yang on 2017/5/27.
 */

public class LapAdapter extends ArrayAdapter<LapInfo> {
    private int itemLayoutId;
    public LapAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<LapInfo> objects) {
        super(context, resource, objects);
        itemLayoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LapInfo lapInfo = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView ==null){
            view = LayoutInflater.from(getContext()).inflate(itemLayoutId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.lapNo = (TextView) view.findViewById(R.id.lap_no);
            viewHolder.lapTime = (TextView) view.findViewById(R.id.lap_time);
            viewHolder.totalTime = (TextView) view.findViewById(R.id.total_time);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.lapNo.setText(lapInfo.getLapNo());
        viewHolder.lapTime.setText(lapInfo.getLapTime());
        viewHolder.totalTime.setText(lapInfo.getTotalTime());
        return view;
    }

    class ViewHolder{
        TextView lapNo;
        TextView lapTime;
        TextView totalTime;
    }
}
