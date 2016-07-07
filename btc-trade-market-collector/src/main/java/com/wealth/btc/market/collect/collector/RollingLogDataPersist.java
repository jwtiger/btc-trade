package com.wealth.btc.market.collect.collector;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

import java.io.IOException;

/**
 * Created by Administrator on 2016/7/6.
 */
public class RollingLogDataPersist implements DataPersist {

    private DailyRollingFileAppender dailyRollingFileAppender;

    public RollingLogDataPersist(String filePath) {
        try {
            dailyRollingFileAppender = new DailyRollingFileAppender(new PatternLayout(), filePath, "'.'yyyyMMdd");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //(String fqnOfCategoryClass, Category logger, Priority level, Object message, Throwable throwable)
    public void persist(String data) {
        dailyRollingFileAppender.append(new LoggingEvent("", Logger.getLogger(RollingLogDataPersist.class), Priority.INFO,data,null));
    }

    public static void main(String[] args) {
        new RollingLogDataPersist("/test/123.txt").persist("test 1234");
    }
}
