package net.manbucy.runningcount.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import net.manbucy.runningcount.R;
import net.manbucy.runningcount.activity.base.BaseActivity;
import net.manbucy.runningcount.adapter.RunningAdapter;
import net.manbucy.runningcount.app.App;
import net.manbucy.runningcount.app.Constant;
import net.manbucy.runningcount.modle.db.LapDBHelper;
import net.manbucy.runningcount.modle.db.RunningDBHelper;
import net.manbucy.runningcount.modle.entity.Running;
import net.manbucy.runningcount.modle.mapper.RunningMapper;
import net.manbucy.runningcount.modle.vo.RunningInfo;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ListView runningListview;
    private List<RunningInfo> runningInfos = new ArrayList<>();
    private RunningAdapter adapter;
    private FloatingActionButton addButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.getDatabase();
        initView();
        initListener();

    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: ");
                queryData();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(addIntent, Constant.RESULTCODE_ADD);
            }
        });

        runningListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent lapIntent = new Intent(MainActivity.this, LapInfoActivity.class);
                lapIntent.putExtra("runningInfo", runningInfos.get(position));
                startActivityForResult(lapIntent, Constant.RESULTCODE_LAP);
            }
        });

        runningListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage("是否要删除这条记录")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteRunning(position);
                            }
                        });
                dialog.show();
                return true;
            }
        });
    }

    private void deleteRunning(int position) {
        RunningInfo runningInfo = runningInfos.get(position);
        RunningDBHelper.deleteByIdAsync(String.valueOf(runningInfo.getId()));
        LapDBHelper.deleteAll(String.valueOf(runningInfo.getId()));
        runningInfos.remove(position);
        adapter.notifyDataSetChanged();
    }

    private void queryData() {
        RunningDBHelper.findAllAsync(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                runningInfos.clear();
                List<Running> runnings = (List<Running>) t;
                runningInfos.addAll(new RunningMapper().transform(runnings));
                Collections.reverse(runningInfos);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("计圈器");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        runningListview = (ListView) findViewById(R.id.running_list);
        addButton = (FloatingActionButton) findViewById(R.id.add);
        adapter = new RunningAdapter(this, R.layout.running_item, runningInfos);
        runningListview.setAdapter(adapter);
        queryData();
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                View view =  LayoutInflater.from(App.getContext()).inflate(R.layout.setting_popup,null);
                PopupWindow popupWindow = new PopupWindow(view);
                Log.d(TAG, "onOptionsItemSelected: settings");
                popupWindow.showAsDropDown(toolbar);
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.RESULTCODE_ADD:
                if (resultCode == RESULT_OK) {
                    Running running = (Running) data.getSerializableExtra("running");
                    RunningInfo runningInfo = new RunningMapper().transform(running);
                    runningInfos.add(runningInfo);
                    adapter.notifyDataSetChanged();
                }
                break;
            case Constant.RESULTCODE_LAP:
                if (resultCode == RESULT_OK) {
                    queryData();
                    swipeRefreshLayout.setRefreshing(true);
                }
                break;
            default:
        }
    }
}
