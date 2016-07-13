package com.wealth.btc.market.collect.collector.huobi;

import com.wealth.btc.market.collect.collector.HttpPullMarketCollector;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by Administrator on 2016/7/13.
 */
public class HuobiTradeRecordCollector extends HttpPullMarketCollector {

    private ObjectMapper objectMapper = new ObjectMapper();

    public HuobiTradeRecordCollector() {
        super("huobi-trade-cfg.properties");
    }

    private long lastRecordTime = 0;

    private String lastRecordTimeStr = "";


    @Override
    protected String transformMsg(String originalContent) {
        try {
            HashMap<String, Object> tradeDataMap = objectMapper.readValue(originalContent, HashMap.class);
            StringBuilder stringBuilder = new StringBuilder();
            List<HashMap<String, Object>> trades = (List<HashMap<String, Object>>) tradeDataMap.get("trades");
            if (trades != null && trades.size() > 0) {
                long timeLast = DateTime.parse(DateTime.now().toString("yyyy-MM-dd") + "T" + trades.get(0).get("time")).getMillis()/1000;
                if(timeLast-1 > lastRecordTime){
                    lastRecordTime = timeLast-1;
                    lastRecordTimeStr = trades.get(0).get("time").toString();
                    String formatStr = format(trades);
//                    System.out.println(formatStr);
                    return formatStr;
                }

            }
            return stringBuilder.toString();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }


    private String format(List<HashMap<String, Object>> trades){
        for(Iterator<HashMap<String,Object>> iterator = trades.iterator();iterator.hasNext();){
            Map<String,Object> trade = iterator.next();
            if(trade.get("time").equals(lastRecordTimeStr)){
                trade.remove("time");
                trade.remove("en_type");
                trade.put("type", trade.get("type").equals("ask")?"s":"b");
            }else{
                iterator.remove();
            }
        }
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("time",lastRecordTime);
        map.put("trades",trades);
        StringWriter stringWriter = new StringWriter();
        try {
            objectMapper.writeValue(stringWriter, map);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return stringWriter.toString().trim();
    }
}
