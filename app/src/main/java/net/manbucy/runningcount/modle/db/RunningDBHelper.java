package net.manbucy.runningcount.modle.db;

import android.content.ContentValues;
import android.util.Log;

import net.manbucy.runningcount.modle.entity.Running;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

/**
 * Created by yang on 2017/5/28.
 */

public class RunningDBHelper {
    private static final String TAG = "RunningDBHelper";

    /**
     * 创建一个新的 Running,并存入数据库
     *
     * @param startTime 这个Running的开始时间
     * @return 这个Running 对象
     */
    public static Running createRunning(String startTime) {
        Running running = new Running();
        running.setStartTime(startTime);
        running.setEndTime(startTime);
        running.save();
        return running;
    }

    /**
     * 根据id查询一条running记录
     *
     * @param id
     * @return
     */
    public static Running findById(String id){
        return DataSupport.find(Running.class,Long.parseLong(id));
    }

    /**
     * 已异步的方式存入 running
     *
     * @param running
     */
    public static void saveAsync(Running running) {
        running.saveAsync().listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                if (!success) {
                    Log.d(TAG, "running saveAsync failure");
                }
            }
        });
    }

    /**
     * 以异步的方式查询所有running数据
     *
     * @param callback
     */
    public static void findAllAsync(FindMultiCallback callback) {
        DataSupport.findAllAsync(Running.class).listen(callback);
    }

    /**
     * 以异步的方式根据id删除该running
     *
     * @param id
     */
    public static void deleteByIdAsync(String id){
        DataSupport.deleteAsync(Running.class,Long.parseLong(id)).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                Log.d(TAG, "onFinish: "+rowsAffected);
            }
        });
    }

    /**
     * 以异步的方式根据id值修改running的属性
     *
     * @param id
     * @param values
     */
    public static void updateById(String id,ContentValues values,UpdateOrDeleteCallback callback){
        DataSupport.updateAsync(Running.class,values,Long.parseLong(id)).listen(callback);
    }
}
