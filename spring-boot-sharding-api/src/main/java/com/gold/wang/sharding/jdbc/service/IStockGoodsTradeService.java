package com.gold.wang.sharding.jdbc.service;


import com.gold.wang.sharding.jdbc.entity.StockGoodsTrade;

import java.util.List;

public interface IStockGoodsTradeService {
    /**
     * 插入货品流水记录
     *
     * @param param
     */
    int insertStockGoodsTrade(StockGoodsTrade param);

    /**
     * 批量插入
     */
    int insertBatch(List<StockGoodsTrade> list);

    /**
     * 错误实例
     */
    int insertBatchBadCase(List<StockGoodsTrade> list);


    /**
     * 批量查询
     */
    List<StockGoodsTrade> batchSelect(List<Long> ids);

}
