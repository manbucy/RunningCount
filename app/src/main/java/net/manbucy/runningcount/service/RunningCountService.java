package net.manbucy.runningcount.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RunningCountService extends Service {
    private RunningBinder runningBinder = new RunningBinder();

    public RunningCountService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return runningBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
