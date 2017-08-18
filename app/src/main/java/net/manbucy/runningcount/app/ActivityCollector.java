package net.manbucy.runningcount.app;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yang on 2017/5/29.
 */

public class ActivityCollector {
    private static Map<String,Activity> destroyActivityMap = new HashMap<>();

    /**
     * 将要管理的activity添加到集合
     *
     * @param activity
     * @param name
     */
    public static void addActivity(Activity activity,String name){
        destroyActivityMap.put(name,activity);
    }

    /**
     * 根据activity 的名称移除该activity
     *
     * @param name
     */
    public static void removeActivity(String name){
        destroyActivityMap.remove(name);
    }

    /**
     * 更具name值来finish 该activity
     *
     * @param name
     */
    public static void finishActivity(String name){
        Activity activity = destroyActivityMap.get(name);
        if (activity!=null){
            activity.finish();
        }
    }
}
