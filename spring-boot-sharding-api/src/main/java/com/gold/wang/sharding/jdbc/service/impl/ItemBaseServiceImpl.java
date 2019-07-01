package com.gold.wang.sharding.jdbc.service.impl;

import com.gold.wang.sharding.jdbc.entity.ItemBase;
import com.gold.wang.sharding.jdbc.mapper.ItemBaseMapper;
import com.gold.wang.sharding.jdbc.service.IItemBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ItemBaseServiceImpl implements IItemBaseService {

    @Autowired
    private ItemBaseMapper itemBaseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(ItemBase itembase) {
        return itemBaseMapper.insert(itembase);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSoldAndStockByGoodsNo(ItemBase param) {
        log.info("start update by uniqK itemBase={}", param);
        int result=itemBaseMapper.update(param);
        log.info("start update by uniqK result={}", result);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSoldoutByPK(ItemBase itemBase) {
        log.info("start update by PK itemBase={}", itemBase);
        int result=itemBaseMapper.update(itemBase);
        log.info("start update by PK result={}", result);
        return result;
    }


}
