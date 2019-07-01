package com.gold.wang.sharding.jdbc.api.test;

import com.gold.wang.sharding.jdbc.ShardingJdbcApplication;
import com.gold.wang.sharding.jdbc.entity.StockGoodsBatchCode;
import com.gold.wang.sharding.jdbc.entity.StockGoodsTrade;
import com.gold.wang.sharding.jdbc.service.IStockGoodsBatchCodeService;
import com.gold.wang.sharding.jdbc.service.IStockGoodsTradeService;
import com.gold.wang.sharding.jdbc.service.impl.StockGoodsBatchCodeBatchServiceImpl;
import com.gold.wang.sharding.jdbc.util.IdGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingJdbcApplication.class)
public class StockGoodsTradeServiceImplTest {

    @Autowired
    private IStockGoodsTradeService iStockGoodsTradeService;

    @Autowired
    private IStockGoodsBatchCodeService iStockGoodsBatchCodeService;

    @Autowired
    private StockGoodsBatchCodeBatchServiceImpl stockGoodsBatchCodeBatchService;


    /**
     * demo1   分库分表遵循的规则
     * 1.需要散列足够均匀,具体实时要结合具体业务场景
     * 2.有业务关联性的分库分表,需要事物保障一致性的,尽量分库分表的规则一致
     */
    @Test
    public void testSplitTableTransaction() {
        Long orderId = 123123123123L;
        Long storeId = 1211111111L;
        Long storeId1 = 1211111111L;
//        Long storeId = 1111111111L;
//        Long storeId1 = 1111111111L;
        Long businessId = 131763L;
        //StockTrade
        StockGoodsTrade trade = new StockGoodsTrade();
        trade.setId(IdGenerator.generate(storeId));
        trade.setOrderId(orderId);
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

        //StockGoodsBatchCode
        StockGoodsBatchCode code = new StockGoodsBatchCode();
        code.setId(IdGenerator.generate(storeId1));
        code.setBusinessId(businessId);
        code.setStoreId(storeId);
        code.setSkuMerchantCode(new Random().nextInt(100000) + "");
        code.setBatchCode("123123");
        code.setBatchNo("112233");
        code.setPieceUnit(new BigDecimal("0"));
        code.setStock(new BigDecimal("100"));
        code.setBuyStock(new BigDecimal("100"));
        code.setPieceBuyStock(new BigDecimal("0"));
        code.setWaittingAreaStock(new BigDecimal("0"));
        code.setPieceWaittingAreaStock(new BigDecimal("0"));
        code.setUnqualifiedAreaStock(new BigDecimal("0"));
        code.setPieceUnqualifiedAreaStock(new BigDecimal("0"));
        code.setWaitStock(new BigDecimal("0"));
        code.setTransitStock(new BigDecimal("0"));
        code.setUnit("");
        code.setExpireDate(new Date());
        code.setProduceDate(new Date());
        code.setProduceCompany("");
        code.setSupply("");
        code.setLocked((short) 0);
        code.setSyncDate(new Date());
        code.setApplyType((byte) 0);
        code.setVersion(0);
        code.setExtend("");
        code.setCreatedBy("");
        code.setGmtCreate(new Date());
        code.setLastModifiedBy("");
        code.setGmtUpdate(new Date());
        iStockGoodsBatchCodeService.splitTableTransaction(trade, code);
    }


    /**
     * demo2  死锁常见的产生的原因以及开发中如何规避。
     * 1.多数据操作的时候注意对数据进行排序,规避并发情况下产生死锁
     * mysql -h127.0.0.1 -uesuser -pususer
     * 相关链接:https://www.cnblogs.com/janehoo/p/5603983.html
     */
    @Test
    public void testDoPlaceStockOrder() {
        List<StockGoodsBatchCode> one = new ArrayList<>();
        StockGoodsBatchCode oneGood = new StockGoodsBatchCode();
        oneGood.setId(52851111156303L);
        oneGood.setBuyStock(new BigDecimal("1"));
        oneGood.setStock(new BigDecimal("1"));
        StockGoodsBatchCode twoGood = new StockGoodsBatchCode();
        twoGood.setId(94311111155412L);
        twoGood.setBuyStock(new BigDecimal("1"));
        twoGood.setStock(new BigDecimal("1"));
        one.add(oneGood);
        one.add(twoGood);
        iStockGoodsBatchCodeService.doPlaceStockOrder(one);
    }

    /**
     * demo2
     */
    @Test
    public void testDoPlaceStockOrder1() {
        List<StockGoodsBatchCode> one = new ArrayList<>();
        StockGoodsBatchCode oneGood = new StockGoodsBatchCode();
        oneGood.setId(52851111156303L);
        oneGood.setBuyStock(new BigDecimal("1"));
        oneGood.setStock(new BigDecimal("1"));
        StockGoodsBatchCode twoGood = new StockGoodsBatchCode();
        twoGood.setId(94311111155412L);
        twoGood.setBuyStock(new BigDecimal("1"));
        twoGood.setStock(new BigDecimal("1"));
        one.add(twoGood);
        one.add(oneGood);
        iStockGoodsBatchCodeService.doPlaceStockOrder(one);
    }


    /**
     * demo4  (错误用法示例)
     * spring 事物底层原理是基于 AOP进行的代理增强实现的。
     * 1.spring 配置的事物不起作用？什么情况下会导致spring事物不起作用？
     * 2.很多时候我们要处理一个list集合，但是呢我们又不想 某条失败就回滚全部的
     * 内容，只想回滚当前的某一条数据，其他的继续处理成功。
     * what?怎么这么写我的事物没起作用？这是神马情况导致的？
     */
    @Test
    public void testBatchHandle() {
        List<StockGoodsBatchCode> list = new ArrayList();
        StockGoodsBatchCode oneGood = new StockGoodsBatchCode();
        oneGood.setId(88301211197275L);
        oneGood.setStoreId(1211111111L);
        oneGood.setBuyStock(new BigDecimal("1"));
        oneGood.setStock(new BigDecimal("1"));
        StockGoodsBatchCode twoGood = new StockGoodsBatchCode();
        twoGood.setId(97021211186268L);
        twoGood.setStoreId(1211111111L);
        twoGood.setBuyStock(new BigDecimal("1"));
        twoGood.setStock(new BigDecimal("1"));
        list.add(oneGood);
        list.add(twoGood);
        iStockGoodsBatchCodeService.batchHandle(list);
    }

    /**
     * demo4  (错误示例)
     */
    @Test
    public void testBatchHandle1() {
        List<StockGoodsBatchCode> list = new ArrayList();
        StockGoodsBatchCode oneGood = new StockGoodsBatchCode();
        oneGood.setId(88301211197275L);
        oneGood.setStoreId(1211111111L);
        oneGood.setBuyStock(new BigDecimal("1"));
        oneGood.setStock(new BigDecimal("1"));
        StockGoodsBatchCode twoGood = new StockGoodsBatchCode();
        twoGood.setId(97021211186268L);
        twoGood.setStoreId(1211111111L);
        twoGood.setBuyStock(new BigDecimal("1"));
        twoGood.setStock(new BigDecimal("1"));
        list.add(oneGood);
        list.add(twoGood);
        iStockGoodsBatchCodeService.batchHandle(list);
    }

    /**
     * demo4  (正确示例1)
     */
    @Test
    public void testBatchHandle12() {
        List<StockGoodsBatchCode> list = new ArrayList();
        StockGoodsBatchCode oneGood = new StockGoodsBatchCode();
        oneGood.setId(88301211197275L);
        oneGood.setStoreId(1211111111L);
        oneGood.setBuyStock(new BigDecimal("1"));
        oneGood.setStock(new BigDecimal("1"));
        StockGoodsBatchCode twoGood = new StockGoodsBatchCode();
        twoGood.setId(97021211186268L);
        twoGood.setStoreId(1211111111L);
        twoGood.setBuyStock(new BigDecimal("1"));
        twoGood.setStock(new BigDecimal("1"));
        list.add(oneGood);
        list.add(twoGood);
        stockGoodsBatchCodeBatchService.batchHandle(list);
    }


    @Test
    public void reduceGoodsBatchCodeStock() {
        StockGoodsBatchCode oneGood = new StockGoodsBatchCode();
        oneGood.setId(811111198972L);
        oneGood.setStoreId(1211111111L);
        oneGood.setBuyStock(new BigDecimal("1"));
        oneGood.setStock(new BigDecimal("1"));
        int resut = iStockGoodsBatchCodeService.reduceGoodsBatchCodeStock(oneGood);
        Assert.assertTrue(resut > 0);
    }


}
