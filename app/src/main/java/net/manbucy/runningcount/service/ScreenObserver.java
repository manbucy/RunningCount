package net.manbucy.runningcount.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * 对屏幕的亮屏、锁屏、解锁进行监听
 * Created by xiyanglu on 4/11/15.
 */
public class ScreenObserver {
    private Context mContext;
    private ScreenBroadcastReceiver mScreenReceiver;
    private ScreenStateListener mScreenStateListener;

    public ScreenObserver(Context context) {
        mContext = context;
        mScreenReceiver = new ScreenBroadcastReceiver();
    }

    public void startObserver(ScreenStateListener listener) {
        mScreenStateListener = listener;
        registerListener();
        getScreenState();
    }

    public void shutdownObserver() {
        unregisterListener();
    }

    /**
     * 获取screen状态
     */
    @SuppressLint("NewApi")
    private void getScreenState() {
        if (mContext == null) {
            return;
        }

        PowerManager manager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        if (manager.isScreenOn()) {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOn();
            }
        } else {
            if (mScreenStateListener != null) {
                mScreenStateListener.onScreenOff();
            }
        }
    }

    private void registerListener() {
        if (mContext != null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            mContext.registerReceiver(mScreenReceiver, filter);
        }
    }

    private void unregisterListener() {
        if (mContext != null)
            mContext.unregisterReceiver(mScreenReceiver);
    }


    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
                mScreenStateListener.onScreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                mScreenStateListener.onScreenOff();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
                mScreenStateListener.onUserPresent();
            }
        }
    }

    public interface ScreenStateListener {// 返回给调用者屏幕状态信息

        void onScreenOn();

        void onScreenOff();

        void onUserPresent();
    }
}