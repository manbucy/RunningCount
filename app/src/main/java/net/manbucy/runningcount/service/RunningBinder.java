package net.manbucy.runningcount.service;

import android.os.Binder;
import android.util.Log;

import net.manbucy.runningcount.modle.db.LapDBHelper;
import net.manbucy.runningcount.modle.db.RunningDBHelper;
import net.manbucy.runningcount.modle.entity.Lap;
import net.manbucy.runningcount.modle.entity.Running;
import net.manbucy.runningcount.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2017/5/28.
 */

public class RunningBinder extends Binder {
    private List<Lap> laps;
    private Running running;
    private Lap lap;
    private String startTime;
    private String endTime;
    private int lapNo = 1;

    public int getLapNo() {
        return lapNo;
    }

    private boolean isStart = false;

    public RunningBinder() {
        laps = new ArrayList<>();
    }

    public void start() {
        startTime = DateUtils.dateToString(DateUtils.PATTEN_HMS);
        running = RunningDBHelper.createRunning(startTime);
        lap = LapDBHelper.createLap(startTime, running.getId());
    }


    public void count() {
        saveData();
        lapNo++;
        startTime = endTime;
        lap = LapDBHelper.createLap(startTime, running.getId());
    }

    private void saveData() {
        endTime = DateUtils.dateToString(DateUtils.PATTEN_HMS);
        lap.setLapNo(lapNo);
        lap.setEndTime(endTime);
        LapDBHelper.saveAsync(lap);
        running.setTotalCircleNumber(lapNo);
        running.setEndTime(endTime);
        RunningDBHelper.saveAsync(running);
        laps.add(lap);
    }

    public void pause() {
        saveData();
    }

    public void reset() {
        laps.clear();
        LapDBHelper.deleteAll(String.valueOf(running.getId()));
        RunningDBHelper.deleteByIdAsync(String.valueOf(running.getId()));
        isStart = false;
        lapNo = 1;
    }

    public List<Lap> getLaps() {
        return laps;
    }

    public Running getRunning() {
        return running;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }


}
