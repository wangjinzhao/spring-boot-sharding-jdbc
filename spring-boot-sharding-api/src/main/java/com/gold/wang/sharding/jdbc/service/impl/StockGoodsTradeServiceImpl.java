package com.gold.wang.sharding.jdbc.service.impl;

import com.gold.wang.sharding.jdbc.entity.StockGoodsTrade;
import com.gold.wang.sharding.jdbc.mapper.StockGoodsTradeMapper;
import com.gold.wang.sharding.jdbc.service.IStockGoodsTradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 货品表门面
 */

@Service
public class StockGoodsTradeServiceImpl implements IStockGoodsTradeService {


    @Autowired
    private StockGoodsTradeMapper stockGoodsTradeMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertStockGoodsTrade(StockGoodsTrade param) {
        return stockGoodsTradeMapper.insert(param);
    }

}
