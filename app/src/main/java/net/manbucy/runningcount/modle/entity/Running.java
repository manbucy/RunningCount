package net.manbucy.runningcount.modle.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 每次跑步信息
 * Created by yang on 2017/5/27.
 */

public class Running extends DataSupport implements Serializable{
    /**
     * id
     */
    private int id;
    /**
     * 本次跑步的总圈数
     */
    private int totalCircleNumber;
    /**
     * 本次跑步的开始时间
     */
    private String startTime;
    /**
     * 本次跑步的结束时间
     */
    private String endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalCircleNumber() {
        return totalCircleNumber;
    }

    public void setTotalCircleNumber(int totalCircleNumber) {
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
}
