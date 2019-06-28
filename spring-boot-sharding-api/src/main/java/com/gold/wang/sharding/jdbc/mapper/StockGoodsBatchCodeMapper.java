package com.gold.wang.sharding.jdbc.mapper;

import com.gold.wang.sharding.jdbc.entity.StockGoodsBatchCode;


public interface StockGoodsBatchCodeMapper {
    int insert(StockGoodsBatchCode record);

    int updateStockForOrder(StockGoodsBatchCode record);
}
