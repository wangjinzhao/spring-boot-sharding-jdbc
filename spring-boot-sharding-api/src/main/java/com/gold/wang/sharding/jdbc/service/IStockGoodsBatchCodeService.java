package com.gold.wang.sharding.jdbc.service;

import com.gold.wang.sharding.jdbc.entity.StockGoodsBatchCode;
import com.gold.wang.sharding.jdbc.entity.StockGoodsTrade;

import java.util.List;


public interface IStockGoodsBatchCodeService {

    int insertStockGoodsBatchCode(StockGoodsBatchCode param);

    int splitTableTransaction(StockGoodsTrade stockGoodsTrade, StockGoodsBatchCode stockGoodsBatchCode);

    int reduceGoodsBatchCodeStock(StockGoodsBatchCode param);

    void doPlaceStockOrder(List<StockGoodsBatchCode> list);


}
