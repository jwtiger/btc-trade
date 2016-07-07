#!/bin/sh
export LANG="en_US.UTF-8"
source /etc/profile
export clspath="classes"
cd `dirname $0`/../

for k in dependency/*.jar
do
        clspath=$clspath:$k
done
echo "execute MarketCollectExecutor start..................."
  java -Xms64m -Xmx512m -XX:PermSize=64m -XX:MaxPermSize=128m -classpath "${clspath}" com.wealth.btc.market.collect.collector.MarketCollectExecutor
echo "execute MarketCollectExecutor finish..................."
