package net.manbucy.runningcount.modle.vo;

import java.io.Serializable;

/**
 * RunningItem将要显示的数据类
 * Created by yang on 2017/5/28.
 */

public class RunningInfo implements Serializable{
    private int id;
    /**
     * 本次跑步的总圈数
     */
    private String totalCircleNumber;
    /**
     * 本次跑步的开始时间
     */
    private String startTime;
    /**
     * 本次跑步的结束时间
     */
    private String endTime;
    /**
     * 本次跑步花费的总时间
     */
    private String totalTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTotalCircleNumber() {
        return totalCircleNumber;
    }

    public void setTotalCircleNumber(String totalCircleNumber) {
        this.totalCircleNumber = totalCircleNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
