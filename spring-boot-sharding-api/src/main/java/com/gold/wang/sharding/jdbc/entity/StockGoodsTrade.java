package com.gold.wang.sharding.jdbc.entity;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class StockGoodsTrade implements Serializable {
    private Long id;

    private Long orderId;

    private Long businessId;

    private Long storeId;

    private String skuMerchantCode;

    private String batchCode;

    private String batchNo;

    private String chargeBackId;

    private BigDecimal quantity;

    private BigDecimal pieceQuantity;

    private Byte type;

    private Byte status;

    private String createdBy;

    private Date gmtCreate;

    private String lastModifiedBy;

    private Date gmtUpdate;
}
