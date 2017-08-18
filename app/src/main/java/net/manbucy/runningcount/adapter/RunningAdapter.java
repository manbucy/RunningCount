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
import net.manbucy.runningcount.modle.vo.RunningInfo;

import java.util.List;

/**
 * RunningList的数据适配器
 * Created by yang on 2017/5/27.
 */

public class RunningAdapter extends ArrayAdapter<RunningInfo> {
    private int itemLayoutId;

    public RunningAdapter(@NonNull Context context, @LayoutRes int resource,
                          @NonNull List<RunningInfo> objects) {
        super(context, resource, objects);
        itemLayoutId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RunningInfo runningInfo = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView ==null){
            view = LayoutInflater.from(getContext()).inflate(itemLayoutId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.totalCircleNumber = (TextView) view.findViewById(R.id.total_circle_number);
            viewHolder.startTime = (TextView) view.findViewById(R.id.start_time);
            viewHolder.endTime = (TextView) view.findViewById(R.id.end_time);
            viewHolder.totalTime = (TextView) view.findViewById(R.id.total_time);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.totalCircleNumber.setText(runningInfo.getTotalCircleNumber());
        viewHolder.startTime.setText(runningInfo.getStartTime());
        viewHolder.endTime.setText(runningInfo.getEndTime());
        viewHolder.totalTime.setText(runningInfo.getTotalTime());
        return view;
    }
    class ViewHolder{
        TextView totalCircleNumber;
        TextView startTime;
        TextView endTime;
        TextView totalTime;
    }
}
