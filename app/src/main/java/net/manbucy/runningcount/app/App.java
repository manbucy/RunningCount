package net.manbucy.runningcount.app;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by yang on 2017/5/27.
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);
    }

    /**
     * 获取一个全局的context
     *
     * @return 一个全局的context
     */
    public static Context getContext() {
        return context;
    }

}
