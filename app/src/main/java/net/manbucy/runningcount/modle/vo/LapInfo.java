package net.manbucy.runningcount.modle.vo;

/**
 * lapItem将要显示的数据类
 * Created by yang on 2017/5/28.
 */

public class LapInfo {
    /**
     * 第几圈
     */
    private String lapNo;
    /**
     * 本圈花费的总时间
     */
    private String lapTime;
    /**
     * 本次跑步到本圈结束花费的总时间
     */
    private String totalTime;

    public String getLapNo() {
        return lapNo;
    }

    public void setLapNo(String lapNo) {
        this.lapNo = lapNo;
    }

    public String getLapTime() {
        return lapTime;
    }

    public void setLapTime(String lapTime) {
        this.lapTime = lapTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
