package net.manbucy.runningcount.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.manbucy.runningcount.R;
import net.manbucy.runningcount.activity.base.BaseActivity;
import net.manbucy.runningcount.adapter.LapAdapter;
import net.manbucy.runningcount.app.ActivityCollector;
import net.manbucy.runningcount.modle.mapper.LapMapper;
import net.manbucy.runningcount.modle.vo.LapInfo;
import net.manbucy.runningcount.service.RunningBinder;
import net.manbucy.runningcount.service.RunningCountService;
import net.manbucy.runningcount.service.ScreenObserver;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends BaseActivity {
    private static final String TAG = "AddActivity";
    private LinearLayout rootLayout;
    private RelativeLayout secondLayout;
    private ListView lapListView;
    private TextView lapNo;
    private LapAdapter adapter;
    private List<LapInfo> lapInfoList = new ArrayList<>();
    private Button startCount;
    private Button pause;
    private Button reset;
    private Chronometer chronometer;
    private boolean isLock;
    private ScreenObserver observer;
    private boolean isRunning = false;
    private boolean isPause = true;

    private AlertDialog.Builder dialog;

    private RunningBinder runningBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_add);
        Intent bindIntent = new Intent(this, RunningCountService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
        isLock = false;
        observer = new ScreenObserver(this);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            runningBinder = (RunningBinder) service;
            initView();
            initListener();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    private void initListener() {
        startCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause.setClickable(true);
                reset.setClickable(false);
                if (!runningBinder.isStart()){
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    show();
                    runningBinder.start();
                    runningBinder.setStart(true);
                    lapNo.setText(String.valueOf(runningBinder.getLapNo()));
                    isRunning = true;
                    isPause = false;
                }else{
                    runningBinder.count();
                    updateList();
                    lapNo.setText(String.valueOf(runningBinder.getLapNo()));
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runningBinder.pause();
                updateList();
                chronometer.stop();
                startCount.setClickable(false);
                reset.setClickable(true);
                isPause = true;
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runningBinder.reset();
                hide();
                chronometer.setBase(SystemClock.elapsedRealtime());
                startCount.setClickable(true);
                pause.setClickable(true);
                cleanList();
                lapNo.setText("0");
                isRunning = false;
                isPause = true;
            }
        });

        observer.startObserver(new ScreenObserver.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                Log.d(TAG, "onScreenOn: ");
            }

            @Override
            public void onScreenOff() {
                Log.d(TAG, "onScreenOff: ");
                isLock = true;
                showLockLayout();
            }

            @Override
            public void onUserPresent() {
                Log.d(TAG, "onUserPresent: ");
                isLock = false;
                showUnLockLayout();
            }
        });

        dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

    private void showUnLockLayout() {
        rootLayout.setBackgroundColor(Color.WHITE);
        secondLayout.setBackgroundResource(R.color.primary);
        lapListView.setVisibility(View.VISIBLE);
        lapNo.setVisibility(View.INVISIBLE);
    }

    private void showLockLayout() {
        rootLayout.setBackgroundResource(R.drawable.bg_lock);
        secondLayout.setBackgroundResource(R.color.background_null);
        lapListView.setVisibility(View.INVISIBLE);
        lapNo.setVisibility(View.VISIBLE);
    }

    private void initView() {
        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        secondLayout = (RelativeLayout) findViewById(R.id.secondLayout);
        lapNo = (TextView) findViewById(R.id.lap_no);
        lapListView = (ListView) findViewById(R.id.lap_list);
        adapter = new LapAdapter(this, R.layout.lap_item, lapInfoList);
        lapListView.setAdapter(adapter);
        startCount = (Button) findViewById(R.id.start_count);
        pause = (Button) findViewById(R.id.pause);
        pause.setClickable(false);
        reset = (Button) findViewById(R.id.reset);
        chronometer = (Chronometer) findViewById(R.id.timer);
        if (!runningBinder.isStart()) {
            hide();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        observer.shutdownObserver();
    }

    @Override
    public void onBackPressed() {
        if (isLock) {
            return;
        }
        if (!isRunning){
            finish();
        }else if (isPause){
            dialog.setMessage("是否结束本次跑步?");
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishWithSave(false);
                }
            });
            dialog.show();
        }else{
            dialog.setMessage("正在跑步是否要退出?");
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishWithSave(true);
                }
            });
            dialog.show();
        }
    }
    private void finishWithSave(boolean isSave){
        if (isSave){
            runningBinder.pause();
        }
        Intent intent = new Intent();
        intent.putExtra("running",runningBinder.getRunning());
        setResult(RESULT_OK,intent);
        finish();
    }

    private void updateList() {
        lapInfoList.clear();
        lapInfoList.addAll(new LapMapper().transform(runningBinder.getLaps()));
        adapter.notifyDataSetChanged();
        lapListView.smoothScrollToPosition(lapListView.getCount() - 1);
    }

    private void cleanList() {
        lapInfoList.clear();
        adapter.notifyDataSetChanged();
    }

    private void show() {
        startCount.setText("COUNT");
        pause.setVisibility(View.VISIBLE);
        reset.setVisibility(View.VISIBLE);
    }

    private void hide() {
        startCount.setText("START");
        pause.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.INVISIBLE);
    }

}
