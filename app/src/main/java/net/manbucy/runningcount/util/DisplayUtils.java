package net.manbucy.runningcount.util;

import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import net.manbucy.runningcount.app.App;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by yang on 2017/5/28.
 */

public class DisplayUtils {
    private static final String TAG = "DisplayUtils";
    /**
     * 获取设备屏幕的一些信息
     */
    private void getDisplayInfo(){
        WindowManager manager = (WindowManager) App.getContext().getSystemService(WINDOW_SERVICE);
        Point point = new Point();
        manager.getDefaultDisplay().getRealSize(point);
        Log.d(TAG, "screenX: " + point.x + "screenY: " + point.y);

        DisplayMetrics displayMetrics = App.getContext().getResources().getDisplayMetrics();
        Log.d(TAG, "Density is: " + displayMetrics.density);
        Log.d(TAG, "DensityDpi is : " + displayMetrics.densityDpi);
        Log.d(TAG, "width: " + displayMetrics.widthPixels + " height: " + displayMetrics.heightPixels);

        Log.d(TAG, "Xdpi: "+displayMetrics.xdpi);
        Log.d(TAG, "Ydpi: "+displayMetrics.ydpi);

        Log.d(TAG, "X: "+point.x / displayMetrics.xdpi);
        Log.d(TAG, "Y: "+point.y / displayMetrics.ydpi);
        Log.d(TAG, "SiZE: "+Math.sqrt(
                Math.pow(point.x / displayMetrics.xdpi,2)+Math.pow(point.y / displayMetrics.ydpi,2)
        ));
    }
}
