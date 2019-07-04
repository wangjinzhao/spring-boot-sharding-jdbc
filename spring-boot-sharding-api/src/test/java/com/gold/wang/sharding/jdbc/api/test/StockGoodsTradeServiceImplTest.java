package com.gold.wang.sharding.jdbc.api.test;

import com.gold.wang.sharding.jdbc.ShardingJdbcApplication;
import com.gold.wang.sharding.jdbc.entity.StockGoodsBatchCode;
import com.gold.wang.sharding.jdbc.entity.StockGoodsTrade;
import com.gold.wang.sharding.jdbc.service.IStockGoodsBatchCodeService;
import com.gold.wang.sharding.jdbc.service.IStockGoodsTradeService;
import com.gold.wang.sharding.jdbc.service.impl.StockGoodsBatchCodeBatchServiceImpl;
import com.gold.wang.sharding.jdbc.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
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
     * <p>
     * sharding-jdbc 本地事物(我们日常开发用的都是本地事物,因此有业务相关性的分库分表的数据,路由规则要保持一致,避免第3种情况导致的不一致性)
     * * 1.完全支持非跨库事物
     * * 2.完成支持因逻辑异常导致的跨库事物,例如：同一事务中，跨两个库更新。更新完毕后，抛出空指针，则两个库的内容都能回滚。
     * * 3.不支持因网络、硬件异常导致的跨库事务。例如：同一事务中，跨两个库更新，更新完毕后、未提交之前，第一个库宕机，则只有第二个库数据提交。
     * * 4.支持分布式事物 不做介绍,感兴趣自行查看官网
     * * https://shardingsphere.apache.org/document/current/cn/manual/sharding-jdbc/usage/transaction/
     * * sharding-jdbc 支持两阶段事物-XA,两阶段提交保证操作原子性和事物一致性(宕机重启后提交/回滚后的时候可自动恢复,需要使用单独的XA事物管理器),性能太差不推荐
     * * sharding-jdbc  也支持柔性事物,需要特殊的事物管理器以及外部相关的依赖，也暂不推荐
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
     * mysql -h127.0.0.1 -uroot -proot
     * SHOW ENGINE INNODB STATUS\G
     * <p>
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


    /**
     * demo5
     * 对于数据库操作能批量的一定批量操作,很多时候我们业务操作都已经带了分库分表的字段,并且可能需要处理的list分库分表字段值都是一样
     * 千万不要一上来就for循环里面操作数据库了
     * sharding-jdbc 3.0支持批量 insert/update/select
     * 举例
     * 1.多品下单扣减库存,对于POS单来说同一个单肯定同一个门店,库存表也是按照storeId分库分表的,批处理肯定可以一次数据库io操作
     * 2.insert/update 对于一个list操作里面加入分库分表的字段值不一样,那么先按照分库分表字段分组,然后分组批处理,可能要保证事物之类的for循环分组即可
     * 3.对于select 如果一组待处理的数据分库分表的字段值不一致分组处理,然后多线程异步处理分组批量查询
     */
    @Test
    public void testBatchInsertStockGoodTrade() {
        //为了刨除初始化时候数据库建立连接的时间先查询一次,下面比对相对准确
        ArrayList<Long> ids = new ArrayList(Arrays.asList(1011211170445L, 1371211182272L, 3521211130672L,
                6521211118613L, 8121211145764L, 8211211141231L, 8531211114015L, 8561211145406L, 9271211192057L));
        iStockGoodsTradeService.batchSelect(ids);

        //BadCase
        List<StockGoodsTrade> list1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            StockGoodsTrade trade = new StockGoodsTrade();
            trade.setId(IdGenerator.generate(1211111111L));
            trade.setOrderId(new Random().nextLong());
            trade.setBusinessId(131763L);
            trade.setStoreId(1211111111L);
            trade.setSkuMerchantCode(new Random().nextInt(100000) + "");
            trade.setBatchCode(new Random().nextInt(100000) + "");
            trade.setBatchNo(new Random().nextInt(100000) + "");
            trade.setChargeBackId("-1");
            trade.setQuantity(new BigDecimal("0"));
            trade.setPieceQuantity(new BigDecimal("0"));
            trade.setType((byte) 0);
            trade.setStatus((byte) 0);
            trade.setCreatedBy("");
            trade.setGmtCreate(new Date());
            trade.setLastModifiedBy("");
            trade.setGmtUpdate(new Date());
            list1.add(trade);
        }
        long start1 = System.currentTimeMillis();
        iStockGoodsTradeService.insertBatchBadCase(list1);
        log.info("=========================for循环插入耗时time={}", System.currentTimeMillis() - start1);


        List<StockGoodsTrade> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            StockGoodsTrade trade = new StockGoodsTrade();
            trade.setId(IdGenerator.generate(1211111111L));
            trade.setOrderId(new Random().nextLong());
            trade.setBusinessId(131763L);
            trade.setStoreId(1211111111L);
            trade.setSkuMerchantCode(new Random().nextInt(100000) + "");
            trade.setBatchCode(new Random().nextInt(100000) + "");
            trade.setBatchNo(new Random().nextInt(100000) + "");
            trade.setChargeBackId("-1");
            trade.setQuantity(new BigDecimal("0"));
            trade.setPieceQuantity(new BigDecimal("0"));
            trade.setType((byte) 0);
            trade.setStatus((byte) 0);
            trade.setCreatedBy("");
            trade.setGmtCreate(new Date());
            trade.setLastModifiedBy("");
            trade.setGmtUpdate(new Date());
            list.add(trade);
        }
        long start = System.currentTimeMillis();
        int result = iStockGoodsTradeService.insertBatch(list);
        log.info("==============================批量插入耗时time={}", System.currentTimeMillis() - start);
        Assert.assertTrue(result == list.size());
    }



    /**
     *
     */


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
