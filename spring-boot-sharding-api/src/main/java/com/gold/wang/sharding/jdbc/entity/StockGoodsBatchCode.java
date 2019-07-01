package com.gold.wang.sharding.jdbc.entity;


import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class StockGoodsBatchCode {
    private Long id;

    private Long businessId;

    private Long storeId;

    private String skuMerchantCode;

    private String batchCode;

    private String batchNo;

    private BigDecimal pieceUnit;

    private BigDecimal stock;

    private BigDecimal buyStock;

    private BigDecimal pieceBuyStock;

    private BigDecimal waittingAreaStock;

    private BigDecimal pieceWaittingAreaStock;

    private BigDecimal unqualifiedAreaStock;

    private BigDecimal pieceUnqualifiedAreaStock;

    private BigDecimal waitStock;

    private BigDecimal transitStock;

    private String unit;

    private Date expireDate;

    private Date produceDate;

    private String produceCompany;

    private String supply;

    private Short locked;

    private Date syncDate;

    private Byte applyType;

    private Integer version;

    private String extend;

    private String createdBy;

    private Date gmtCreate;

    private String lastModifiedBy;

    private Date gmtUpdate;

}
