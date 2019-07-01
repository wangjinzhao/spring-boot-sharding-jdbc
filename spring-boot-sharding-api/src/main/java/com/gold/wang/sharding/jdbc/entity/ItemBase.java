package com.gold.wang.sharding.jdbc.entity;

import lombok.Data;

@Data
public class ItemBase {
    private Long itemId;
    private Long businessId;
    private Long storeId;
    private String itemName;
    /**
     * 商品编码
     */
    private String goodsNo;
    /**
     * 库存数量
     */
    private Long stock;
    /**
     * 销量
     */
    private Long sold;
    /**
     * 是否售罄
     */
    private Short isSoldout = 0;
}
