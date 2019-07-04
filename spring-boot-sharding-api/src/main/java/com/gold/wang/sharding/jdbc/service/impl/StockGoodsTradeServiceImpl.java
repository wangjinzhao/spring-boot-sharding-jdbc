package com.gold.wang.sharding.jdbc.service.impl;

import com.gold.wang.sharding.jdbc.entity.StockGoodsTrade;
import com.gold.wang.sharding.jdbc.mapper.StockGoodsTradeMapper;
import com.gold.wang.sharding.jdbc.service.IStockGoodsTradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StockGoodsTradeServiceImpl implements IStockGoodsTradeService {


    @Autowired
    private StockGoodsTradeMapper stockGoodsTradeMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertStockGoodsTrade(StockGoodsTrade param) {
        return stockGoodsTradeMapper.insert(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(List<StockGoodsTrade> list) {
        return stockGoodsTradeMapper.insertBatch(list);
    }

    /**
     * badcase
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatchBadCase(List<StockGoodsTrade> list) {
        int result = 0;
        for (StockGoodsTrade stockGoodsTrade : list) {
            insertStockGoodsTrade(stockGoodsTrade);
            result++;
        }
        return result;
    }

    /**
     * 批量查询demo
     */
    @Override
    public List<StockGoodsTrade> batchSelect(List<Long> ids) {
        return stockGoodsTradeMapper.batchSelect(ids);
    }
}
