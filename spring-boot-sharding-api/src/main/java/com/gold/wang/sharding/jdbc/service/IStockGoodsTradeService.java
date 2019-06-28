package com.gold.wang.sharding.jdbc.service;


import com.gold.wang.sharding.jdbc.entity.StockGoodsTrade;

public interface IStockGoodsTradeService {
    /**
     * 插入货品流水记录
     *
     * @param param
     */
    int insertStockGoodsTrade(StockGoodsTrade param);


}
