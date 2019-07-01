package com.gold.wang.sharding.jdbc.service.impl;

import com.gold.wang.sharding.jdbc.entity.StockGoodsBatchCode;
import com.gold.wang.sharding.jdbc.entity.StockGoodsTrade;
import com.gold.wang.sharding.jdbc.mapper.StockGoodsBatchCodeMapper;
import com.gold.wang.sharding.jdbc.service.IStockGoodsBatchCodeService;
import com.gold.wang.sharding.jdbc.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;


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
     * 扣减库存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reduceGoodsBatchCodeStock(StockGoodsBatchCode param) {
        return stockGoodsBatchCodeMapper.updateStockForOrder(param);
    }


    /**
     * spring 事物底层原理是基于 AOP进行的代理增强实现的。
     * 1.spring 配置的事物不起作用？什么情况下会导致spring事物不起作用？
     * 2.很多时候我们要处理一个list集合，但是呢我们又不想 某条失败就回滚全部的
     * 内容，只想回滚当前的某一条数据，其他的继续处理成功。
     * what?怎么这么写我的事物没起作用？这是神马情况导致的？
     * 相关文章：https://blog.csdn.net/seelye/article/details/40144817
     * <p>
     * 总结一下现象：
     * 1、ServiceA类为Web层的Action服务
     * 2、Action调用了ServiceA的方法A，而方法A没有声明事务（原因是方法A本身比较耗时而又不需要事务）
     * 3、ServiceA的方法A调用了自己的方法B，而方法B声明了事务，但是方法B的事务声明在这种情况失效了。
     * 4、如果在方法A上也声明事务，则在Action调用方法A时，事务生效，而方法B则自动参与了这个事务。
     * <p>
     * 产生影响：
     * 1、内部调用时，被调用方法的事务声明将不起作用
     * 2、换句话说，你在某个方法上声明它需要事务的时候，如果这个类还有其他开发者，你将不能保证这个方法真的会在事务环境中
     * 3、再换句话说，Spring的事务传播策略在内部方法调用时将不起作用。不管你希望某个方法需要单独事务，是RequiresNew，还是要嵌套事务，要Nested，等等，统统不起作用。
     * 4、不仅仅是事务通知，所有你自己利用Spring实现的AOP通知，都会受到同样限制
     * 解决办法:
     * 只要避开Spring目前的AOP实现上的限制即可.
     * 1.要么都声明要事务.(推荐,当然可能这种场景下不适用,但是我们写代码很多时候会有该问题)
     * 2.要么分开成两个类.(推荐)
     * 3要么直接在方法里使用编程式事务 (不推荐编程式事物烦琐,且跟现在springboot的发展理念不合)
     */
    @Override
    public int batchHandle(List<StockGoodsBatchCode> list) {
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            try {
                int j = this.handleOne(list.get(i), i);
                result += j;
            } catch (Exception e) {
                log.error("batchHandle 当前数据处理异常! param={}", list.get(i), e);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int handleOne(StockGoodsBatchCode param, int num) {
        // 插入 StockTrade 表
        Long storeId = param.getStoreId();
        Long businessId = 131763L;
        StockGoodsTrade trade = new StockGoodsTrade();
        trade.setId(IdGenerator.generate(storeId));
        trade.setOrderId(new Random().nextLong());
        trade.setBusinessId(businessId);
        trade.setStoreId(storeId);
        trade.setSkuMerchantCode(new Random().nextInt(100000) + "");
        trade.setBatchCode(new Random().nextInt(100000) + "");
        trade.setBatchNo(new Random().nextInt(100000) + "");
        trade.setChargeBackId("");
        trade.setQuantity(new BigDecimal("0"));
        trade.setPieceQuantity(new BigDecimal("0"));
        trade.setType((byte) 0);
        trade.setStatus((byte) 0);
        trade.setCreatedBy("");
        trade.setGmtCreate(new Date());
        trade.setLastModifiedBy("");
        trade.setGmtUpdate(new Date());
        stockGoodsTradeService.insertStockGoodsTrade(trade);
        //构造一个异常
        if (num % 2 == 1) {
            try {
                new Long("A");
            } catch (Exception e) {
                throw e;
            }
        }
        //扣减库存
        int result = stockGoodsBatchCodeMapper.updateStockForOrder(param);
        return result;
    }
}
