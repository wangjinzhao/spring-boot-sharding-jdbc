package com.gold.wang.sharding.jdbc.service;

import com.gold.wang.sharding.jdbc.entity.ItemBase;


public interface IItemBaseService {

    int updateSoldAndStockByGoodsNo(ItemBase param);

    int updateSoldoutByPK(ItemBase itemBase);

    int save(ItemBase itembase);
}
