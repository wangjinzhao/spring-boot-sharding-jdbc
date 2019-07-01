package com.gold.wang.sharding.jdbc.api.test;


import com.gold.wang.sharding.jdbc.ShardingJdbcApplication;
import com.gold.wang.sharding.jdbc.entity.ItemBase;
import com.gold.wang.sharding.jdbc.service.impl.ItemBaseServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingJdbcApplication.class)
public class ItemBaseServiceImplTest {

    @Autowired
    private ItemBaseServiceImpl itemBaseService;

    ExecutorService executorService = Executors.newFixedThreadPool(200);


    /**
     * demo3  非主键更新并发的情况下导致的死锁,尽可能的update的时候走主键
     * <p>
     * 相关文章链接
     * 1.https://www.cnblogs.com/JAYIT/p/6554643.html
     * 2.https://blog.csdn.net/aesop_wubo/article/details/8286215
     * 3.https://blog.csdn.net/zcl_love_wx/article/details/81983267
     */
    @Test
    public void testUpdateSoldAndStockByGoodsNo() {
        System.out.println("start testUpdateSoldAndStockByGoodsNo");
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                ItemBase itemBase = new ItemBase();
                itemBase.setBusinessId(1L);
                itemBase.setStoreId(1L);
                itemBase.setGoodsNo("2");
                itemBase.setSold(1L);
                itemBase.setStock(1L);
                itemBaseService.updateSoldAndStockByGoodsNo(itemBase);
            });
        }
        System.out.println("end testUpdateSoldAndStockByGoodsNo");
    }

    /**
     * demo3
     */
    @Test
    public void testUpdateSoldoutByPK() {
        System.out.println("start testUpdateSoldoutByPK");
        for (int i = 0; i < 10000; i++) {
            executorService.submit(() -> {
                ItemBase itemBase = new ItemBase();
                itemBase.setItemId(2L);
                itemBase.setIsSoldout((short) 0);
                itemBaseService.updateSoldoutByPK(itemBase);
            });
        }
        System.out.println("end testUpdateSoldoutByPK");
    }


    @Test
    public void testInsert() {
        int result = 0;
        for (int i = 0; i < 10000; i++) {
            ItemBase itemBase = new ItemBase();
            itemBase.setBusinessId(1L);
            itemBase.setStoreId(1L);
            itemBase.setGoodsNo("3"+i);
            itemBase.setItemName("藿香正气口服液");
            itemBase.setSold(100L);
            itemBase.setStock(100L);
            itemBase.setIsSoldout((short) 0);
            result += itemBaseService.save(itemBase);
        }
        Assert.assertTrue(result > 10000);

    }

}
