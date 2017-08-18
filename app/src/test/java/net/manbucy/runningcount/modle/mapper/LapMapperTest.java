package net.manbucy.runningcount.modle.mapper;

import android.util.Log;

import net.manbucy.runningcount.modle.entity.Lap;
import net.manbucy.runningcount.modle.vo.LapInfo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yang on 2017/5/28.
 */
public class LapMapperTest {
    @Test
    public void transform() throws Exception {
        Lap lap1 = new Lap();
        lap1.setId(1);
        lap1.setRunningId(1);
        lap1.setLapNo(1);
        lap1.setStartTime("2017-05-28 13:19:37");
        lap1.setEndTime("2017-05-28 13:29:47");

        Lap lap2 = new Lap();
        lap2.setId(2);
        lap2.setRunningId(1);
        lap2.setLapNo(2);
        lap2.setStartTime("2017-05-28 13:29:47");
        lap2.setEndTime("2017-05-28 13:39:47");

        Lap lap3 = new Lap();
        lap3.setId(3);
        lap3.setRunningId(1);
        lap3.setLapNo(3);
        lap3.setStartTime("2017-05-28 13:39:47");
        lap3.setEndTime("2017-05-28 13:49:47");

        Lap lap4 = new Lap();
        lap4.setId(4);
        lap4.setRunningId(1);
        lap4.setLapNo(4);
        lap4.setStartTime("2017-05-28 13:49:47");
        lap4.setEndTime("2017-05-28 13:59:47");
        List<Lap> laps = new ArrayList<>();
        laps.add(lap1);
        laps.add(lap2);
        laps.add(lap3);
        laps.add(lap4);
        List<LapInfo> lapInfos = new LapMapper().transform(laps);
        System.out.println(lapInfos);

    }

}