package com.wealth.btc.market.collect.collector;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2016/7/6.
 */
public abstract class PullMarketCollector implements MarketCollector {

    private ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(10);

    /**
     * 抓取间隔时间
     */
    protected int interval;

    public void start() {
        init();
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                String msg = capture();
                if (msg != null && !"".equals(msg)) {
                    solMarket(msg);
                }
            }
        }, 0, interval, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        scheduledThreadPoolExecutor.shutdownNow();
    }

    protected abstract void init();

    protected abstract String capture();

    protected abstract void solMarket(String marketMsg);

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
