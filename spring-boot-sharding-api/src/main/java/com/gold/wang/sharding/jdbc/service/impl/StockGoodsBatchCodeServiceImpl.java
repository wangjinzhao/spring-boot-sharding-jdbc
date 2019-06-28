package com.gold.wang.sharding.jdbc.service.impl;

import com.gold.wang.sharding.jdbc.entity.StockGoodsBatchCode;
import com.gold.wang.sharding.jdbc.entity.StockGoodsTrade;
import com.gold.wang.sharding.jdbc.mapper.StockGoodsBatchCodeMapper;
import com.gold.wang.sharding.jdbc.service.IStockGoodsBatchCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StockGoodsBatchCodeServiceImpl implements IStockGoodsBatchCodeService {
    private final Logger log = LoggerFactory.getLogger(StockGoodsBatchCodeServiceImpl.class);

    @Autowired
    private StockGoodsBatchCodeMapper stockGoodsBatchCodeMapper;

    @Autowired
    private StockGoodsTradeServiceImpl stockGoodsTradeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertStockGoodsBatchCode(StockGoodsBatchCode param) {
        return stockGoodsBatchCodeMapper.insert(param);
    }


    /**
     * sharding-jdbc 本地事物(我们日常开发用的都是本地事物,因此有业务相关性的分库分表的数据,路由规则要保持一致,避免第3种情况导致的不一致性)
     * 1.完全支持非跨库事物
     * 2.完成支持因逻辑异常导致的跨库事物,例如：同一事务中，跨两个库更新。更新完毕后，抛出空指针，则两个库的内容都能回滚。
     * 3.不支持因网络、硬件异常导致的跨库事务。例如：同一事务中，跨两个库更新，更新完毕后、未提交之前，第一个库宕机，则只有第二个库数据提交。
     * sharding-jdbc 支持两阶段事物-XA,两阶段提交保证操作原子性和事物一致性(宕机重启后提交/回滚后的时候可自动恢复,需要使用单独的XA事物管理器),性能太差不推荐
     * sharding-jdbc  也支持柔性事物,需要特殊的事物管理器以及外部相关的依赖，也暂不推荐
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int splitTableTransaction(StockGoodsTrade stockGoodsTrade, StockGoodsBatchCode stockGoodsBatchCode) {
        log.info("开始插入业务数据stockGoodsTrade={},stockGoodsBatchCode={}", stockGoodsTrade, stockGoodsBatchCode);
        int resultInsertTrade = stockGoodsTradeService.insertStockGoodsTrade(stockGoodsTrade);
        int resultInsertBatchCode = stockGoodsBatchCodeMapper.insert(stockGoodsBatchCode);
        log.info("resultInsertTrade={},resultInsertBatchCode={}", resultInsertTrade, resultInsertBatchCode);
//        try {
//            new Long(stockGoodsTrade.getChargeBackId());
//        } catch (Exception e) {
//            throw e;
//        }
        return resultInsertBatchCode;
    }

    /**
     * 模拟多品下单扣减库存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doPlaceStockOrder(List<StockGoodsBatchCode> list) {
        //排序
        //list.sort(Comparator.comparing(StockGoodsBatchCode::getId));
        list.forEach(stockGoodsBatchCode -> {
            log.info("begin reduce id={}", stockGoodsBatchCode.getId());
            reduceGoodsBatchCodeStock(stockGoodsBatchCode);
            log.info("end reduce id={}", stockGoodsBatchCode.getId());
        });
        log.info("扣减库存执行结束");
    }

    /**
     * 模拟多品下单扣减库存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reduceGoodsBatchCodeStock(StockGoodsBatchCode param) {
        return stockGoodsBatchCodeMapper.updateStockForOrder(param);
    }

}
