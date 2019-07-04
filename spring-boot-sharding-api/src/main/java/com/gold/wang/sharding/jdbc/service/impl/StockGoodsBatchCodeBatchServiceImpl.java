package com.gold.wang.sharding.jdbc.service.impl;

import com.gold.wang.sharding.jdbc.entity.StockGoodsBatchCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StockGoodsBatchCodeBatchServiceImpl {


    @Autowired
    private StockGoodsBatchCodeServiceImpl stockGoodsBatchCodeService;

    public int batchHandle(List<StockGoodsBatchCode> list) {
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            try {
                int j = stockGoodsBatchCodeService.handleOne(list.get(i), i);
                result += j;
            } catch (Exception e) {
                log.error("batchHandle 当前数据处理异常! param={}", list.get(i), e);
            }
        }
        return result;
    }

}
