package com.gold.wang.sharding.jdbc.mapper;


import com.gold.wang.sharding.jdbc.entity.StockGoodsTrade;

import java.util.List;


public interface StockGoodsTradeMapper {
    int insert(StockGoodsTrade record);

    /**
     * 批量插入
     */
    int insertBatch(List<StockGoodsTrade> list);

    List<StockGoodsTrade> batchSelect(List<Long> ids);

}
