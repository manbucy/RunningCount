package net.manbucy.runningcount.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by yang on 2017/5/27.
 */

public class DateUtils {
    private static final String TAG = "DateUtils";
    /**
     * 日期时间 格式
     */
    public static String PATTEN_HMS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 带毫秒日期格式
     */
    public static String PATTEN_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * HH:mm:ss
     */
    public static String PATTEN_ONLYHMS = "HH:mm:ss";

    /**
     * 提供日期格式化工具
     *
     * @param pattern 日期格式
     * @return 格式化工具类
     */
    private static SimpleDateFormat getDateParser(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 将当前时间转换成指定的格式显示
     *
     * @param pattern 日期格式
     * @return 转换后的日期字符串
     */
    public static String dateToString(String pattern) {
        return getDateParser(pattern).format(new Date());
    }

    /**
     * 获取两个时间的间隔并转换为指定格式
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @param pattern   日期格式
     * @return 转换后的日期间隔字符串
     */
    public static String getInterval(Date startTime, Date endTime, String pattern) {
        long interval = endTime.getTime() - startTime.getTime();
        SimpleDateFormat sd = getDateParser(pattern);
        sd.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sd.format(interval);
    }

    /**
     * 获取两个时间的间隔并转换为指定格式
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @param pattern   日期格式
     * @return 转换后的日期间隔字符串
     */
    public static String getInterval(String startTime, String endTime, String pattern) {
        Date start = stringToDate(startTime, PATTEN_HMS);
        Date end = stringToDate(endTime, PATTEN_HMS);
        return getInterval(start,end,pattern);
    }


    /**
     * 将自定格式的时间转换为Date类型
     *
     * @param date    指定日期
     * @param pattern 指定格式
     * @return 转换后的日期
     */
    public static Date stringToDate(String date, String pattern) {
        Date result = null;
        try {
            result = getDateParser(pattern).parse(date);
        } catch (ParseException e) {
            Log.d(TAG, "stringToDate: " + e.toString());
        }
        return result;
    }

    public static String longToDate(long date,String pattern){
        SimpleDateFormat sd = getDateParser(pattern);
        sd.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sd.format(date);
    }

}
