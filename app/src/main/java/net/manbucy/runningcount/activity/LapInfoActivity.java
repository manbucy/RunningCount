package net.manbucy.runningcount.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.manbucy.runningcount.R;
import net.manbucy.runningcount.activity.base.BaseActivity;
import net.manbucy.runningcount.adapter.LapAdapter;
import net.manbucy.runningcount.modle.db.LapDBHelper;
import net.manbucy.runningcount.modle.db.RunningDBHelper;
import net.manbucy.runningcount.modle.entity.Lap;
import net.manbucy.runningcount.modle.entity.Running;
import net.manbucy.runningcount.modle.mapper.LapMapper;
import net.manbucy.runningcount.modle.mapper.RunningMapper;
import net.manbucy.runningcount.modle.vo.LapInfo;
import net.manbucy.runningcount.modle.vo.RunningInfo;
import net.manbucy.runningcount.widget.MyListView;

import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.ArrayList;
import java.util.List;

public class LapInfoActivity extends BaseActivity {

    private static final String TAG = "LapActivity";
    private RunningInfo runningInfo;
    private Toolbar toolbar;
    private TextView startTime;
    private TextView endTime;
    private TextView totalTime;
    private FloatingActionButton share;
    private MyListView lapListView;
    private LapAdapter adapter;
    private List<LapInfo> lapInfoList = new ArrayList<>();
    private List<Lap> laps = new ArrayList<>();
    private AlertDialog.Builder dialog;
    private Lap lap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_info);
        runningInfo = (RunningInfo) getIntent().getSerializableExtra("runningInfo");
        initView();
        queryData();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar.setTitle(runningInfo.getTotalCircleNumber());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListener();
    }

    private void initListener() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否要删除这一条记录")
                .setNegativeButton("取消", null);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LapInfoActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
            }
        });
        lapListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (position == (lapInfoList.size() - 1)) {
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteLastLap(position);
                        }
                    });
                    dialog.show();
                }
                return true;
            }
        });
    }

    private void deleteLastLap(int position) {
        lap = laps.get(position);
        ContentValues values = new ContentValues(2);
        values.put("totalcirclenumber", lapInfoList.size() - 1);
        values.put("endtime", lap.getStartTime());
        RunningDBHelper.updateById(String.valueOf(lap.getRunningId()), values, new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Running running = RunningDBHelper.findById(String.valueOf(lap.getRunningId()));
                        runningInfo = new RunningMapper().transform(running);
                        toolbar.setTitle(runningInfo.getTotalCircleNumber());
                        showRunningInfo();
                    }
                });
            }
        });
        LapDBHelper.deleteById(String.valueOf(lap.getId()));
        lapInfoList.remove(position);
        adapter.notifyDataSetChanged();
        lapListView.setAdapter(adapter);
        setResult(RESULT_OK);
    }

    private void queryData() {
        LapDBHelper.findByRunningIdAsync(String.valueOf(runningInfo.getId()), new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                lapInfoList.clear();
                laps = (List<Lap>) t;
                lapInfoList.addAll(new LapMapper().transform(laps));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        lapListView.setAdapter(adapter);
                    }
                });
            }
        });
    }

    private void showRunningInfo() {
        startTime.setText(runningInfo.getStartTime());
        endTime.setText(runningInfo.getEndTime());
        totalTime.setText(runningInfo.getTotalTime());
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        totalTime = (TextView) findViewById(R.id.total_time);
        share = (FloatingActionButton) findViewById(R.id.share);
        showRunningInfo();
        lapListView = (MyListView) findViewById(R.id.lap_list);
        adapter = new LapAdapter(this, R.layout.lap_item, lapInfoList);
        lapListView.setAdapter(adapter);
    }

}
