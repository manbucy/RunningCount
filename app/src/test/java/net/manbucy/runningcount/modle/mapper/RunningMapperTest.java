package net.manbucy.runningcount.modle.mapper;

import android.util.Log;

import net.manbucy.runningcount.modle.entity.Running;
import net.manbucy.runningcount.modle.vo.RunningInfo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yang on 2017/5/28.
 */
public class RunningMapperTest {
    @Test
    public void transform() throws Exception {
        Running running1 = new Running();
        running1.setId(1);
        running1.setTotalCircleNumber(4);
        running1.setStartTime("2017-05-28 12:32:28");
        running1.setEndTime("2017-05-28 12:41:40");
        Running running2 = new Running();
        running2.setId(2);
        running2.setTotalCircleNumber(3);
        running2.setStartTime("2017-05-28 13:32:28");
        running2.setEndTime("2017-05-28 13:41:40");
        List<Running> runnings = new ArrayList<>();
        runnings.add(running1);
        runnings.add(running2);
        List<RunningInfo> runningInfos = new RunningMapper().transform(runnings);
        Log.d("", "transform: "+runningInfos);
    }

    @Test
    public void transform1() throws Exception {

    }

}