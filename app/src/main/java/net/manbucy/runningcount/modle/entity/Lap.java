package net.manbucy.runningcount.modle.entity;

import org.litepal.crud.DataSupport;

/**
 * 每圈的信息类
 * Created by yang on 2017/5/27.
 */

public class Lap extends DataSupport{
    /**
     * id
     */
    private int id;
    /**
     * 某次running的id
     */
    private int runningId;
    /**
     * 第几圈
     */
    private int lapNo;
    /**
     * 本圈的开始时间
     */
    private String startTime;
    /**
     * 本圈的结束时间
     */
    private String endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRunningId() {
        return runningId;
    }

    public void setRunningId(int runningId) {
        this.runningId = runningId;
    }

    public int getLapNo() {
        return lapNo;
    }

    public void setLapNo(int lapNo) {
        this.lapNo = lapNo;
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
}
