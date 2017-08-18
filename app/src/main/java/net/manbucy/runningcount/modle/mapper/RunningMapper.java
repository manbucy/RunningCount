package net.manbucy.runningcount.modle.mapper;

import net.manbucy.runningcount.modle.entity.Running;
import net.manbucy.runningcount.modle.vo.RunningInfo;
import net.manbucy.runningcount.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2017/5/28.
 */

public class RunningMapper {

    /**
     * 将RunningList 装换为RunningInfoList
     * @param runnings
     * @return
     */
    public List<RunningInfo> transform(List<Running> runnings) {
        List<RunningInfo> runningInfos = new ArrayList<>();
        for (Running running : runnings) {
            runningInfos.add(transform(running));
        }
        return runningInfos;
    }

    /**
     * 将Running转换为RunningInfo
     *
     * @param running
     * @return
     */
    public RunningInfo transform(Running running){
        RunningInfo runningInfo = new RunningInfo();
        runningInfo.setId(running.getId());
        runningInfo.setTotalCircleNumber(String.valueOf(running.getTotalCircleNumber()));
        runningInfo.setStartTime(running.getStartTime());
        runningInfo.setEndTime(running.getEndTime());
        runningInfo.setTotalTime(DateUtils.getInterval(running.getStartTime(),
                    running.getEndTime(),DateUtils.PATTEN_ONLYHMS));
        return runningInfo;
    }

}
