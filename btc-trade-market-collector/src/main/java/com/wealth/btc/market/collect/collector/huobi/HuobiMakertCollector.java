package com.wealth.btc.market.collect.collector.huobi;

import com.wealth.btc.market.collect.collector.HttpPullMarketCollector;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/7/7.
 */
public class HuobiMakertCollector extends HttpPullMarketCollector {

    private ObjectMapper objectMapper = new ObjectMapper();

    public HuobiMakertCollector() {
        super("huobi-cfg.properties");
    }


    @Override
    protected String transformMsg(String originalContent){
        try {
            HashMap<String,Object> marketDataObject = objectMapper.readValue(originalContent, HashMap.class);
            StringBuilder stringBuilder = new StringBuilder();
            HashMap<String,Object> ticker = (HashMap<String, Object>) marketDataObject.get("ticker");
            stringBuilder.append(marketDataObject.get("time")).append(",");
            stringBuilder.append(ticker.get("open")).append(",");
            stringBuilder.append(ticker.get("last")).append(",");
            stringBuilder.append(ticker.get("high")).append(",");
            stringBuilder.append(ticker.get("low")).append(",");
            stringBuilder.append(ticker.get("buy")).append(",");
            stringBuilder.append(ticker.get("sell")).append(",");
            stringBuilder.append(ticker.get("vol"));
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
