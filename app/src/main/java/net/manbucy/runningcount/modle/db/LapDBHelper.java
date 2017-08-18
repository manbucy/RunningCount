package net.manbucy.runningcount.modle.db;

import android.util.Log;

import net.manbucy.runningcount.modle.entity.Lap;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.List;

/**
 * Created by yang on 2017/5/28.
 */

public class LapDBHelper {

    private static final String TAG = "LapDBHelper";

    public static Lap createLap(String startTime, int runningId) {
        Lap lap = new Lap();
        lap.setStartTime(startTime);
        lap.setEndTime(startTime);
        lap.setRunningId(runningId);
        lap.saveAsync().listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                if (!success) {
                    Log.d(TAG, "lap saveAsync failure");
                }
            }
        });
        return lap;
    }

    /**
     * 以异的方式存入lap
     *
     * @param lap
     */
    public static void saveAsync(Lap lap) {
        lap.saveAsync().listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                if (!success) {
                    Log.d(TAG, "lap saveAsync failure");
                }
            }
        });
    }

    /**
     * 以异步的方式根据runningid查询对应的所有lap
     *
     * @param runningId
     * @param callback
     */
    public static void findByRunningIdAsync(String runningId, FindMultiCallback callback) {
        DataSupport.where("runningid=" + runningId).findAsync(Lap.class).listen(callback);
    }

    /**
     * 以异步的方式根据laoId删除该跳lap记录
     *
     * @param lapId
     */
    public static void deleteById(String lapId){
        DataSupport.deleteAsync(Lap.class,Long.parseLong(lapId)).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                Log.d(TAG, "onFinish: "+rowsAffected);
            }
        });
    }

    /**
     * 以异步的方式根据runningId删除数据
     *
     * @param runningId
     */
    public static void deleteAll(String runningId) {
        DataSupport.deleteAllAsync(Lap.class, "runningid=" + runningId).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                Log.d(TAG, "onFinish: " + rowsAffected);
            }
        });
    }


}
