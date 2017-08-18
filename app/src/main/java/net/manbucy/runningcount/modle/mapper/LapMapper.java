package net.manbucy.runningcount.modle.mapper;

import net.manbucy.runningcount.modle.entity.Lap;
import net.manbucy.runningcount.modle.vo.LapInfo;
import net.manbucy.runningcount.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2017/5/28.
 */

public class LapMapper {
    /**
     * 将LapList转换为LapInfoList
     *
     * @param laps
     * @return
     */
    public List<LapInfo> transform(List<Lap> laps){
        List<LapInfo> lapInfos = new ArrayList<>();
        String runningStartTime = laps.get(0).getStartTime();
        for (Lap lap : laps) {
            LapInfo lapInfo = new LapInfo();
            lapInfo.setLapNo(String.valueOf(lap.getLapNo()));
            lapInfo.setLapTime(DateUtils.getInterval(lap.getStartTime(),
                    lap.getEndTime(),DateUtils.PATTEN_ONLYHMS));
            lapInfo.setTotalTime(DateUtils.getInterval(runningStartTime,
                    lap.getEndTime(),DateUtils.PATTEN_ONLYHMS));
            lapInfos.add(lapInfo);
        }
        return lapInfos;
    }
}
