package com.wealth.btc.market.collect.collector;

import com.wealth.btc.market.collect.collector.huobi.HuobiMakertCollector;

/**
 * Created by Administrator on 2016/7/7.
 */
public class MarketCollectExecutor {

    public static void main(String[] args) {
        HttpPullMarketCollector httpPullMarketCollector = new HuobiMakertCollector();
        httpPullMarketCollector.start();
        byte[] bytes = new byte[1024];
        while (true) {
            try {
                Thread.sleep(1000);
                System.in.read(bytes);
            } catch (Exception e) {
                //LOGGER.info("read the command error! please retry again!");
            }
            String instr = new String(bytes).trim();
            if ("quit".equalsIgnoreCase(instr)) {
                break;
            }
        }
    }
}
