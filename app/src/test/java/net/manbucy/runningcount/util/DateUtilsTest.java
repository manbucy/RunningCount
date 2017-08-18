package net.manbucy.runningcount.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by yang on 2017/5/27.
 */
public class DateUtilsTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void dateToString() throws Exception {
        System.out.println(DateUtils.dateToString(DateUtils.PATTEN_HMS));
        System.out.println(DateUtils.dateToString(DateUtils.PATTEN_SSS));
    }

    @Test
    public void getInterVal() throws Exception {
        Date startTime = DateUtils.stringToDate("2017-05-25 18:22:49",DateUtils.PATTEN_HMS);
        System.out.println(startTime.getTime());
        Date endTime = DateUtils.stringToDate("2017-05-27 18:22:47",DateUtils.PATTEN_HMS);
        System.out.println(endTime.getTime());
        System.out.println(endTime.getTime() - startTime.getTime());
        System.out.println(DateUtils.getInterval(startTime,endTime,DateUtils.PATTEN_ONLYHMS));
        System.out.println(DateUtils.getInterval("2017-05-25 18:22:49","2017-05-27 18:22:47",DateUtils.PATTEN_ONLYHMS));

    }

}