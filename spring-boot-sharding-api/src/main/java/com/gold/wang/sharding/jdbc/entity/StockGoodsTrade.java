package com.gold.wang.sharding.jdbc.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getSkuMerchantCode() {
        return skuMerchantCode;
    }

    public void setSkuMerchantCode(String skuMerchantCode) {
        this.skuMerchantCode = skuMerchantCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getChargeBackId() {
        return chargeBackId;
    }

    public void setChargeBackId(String chargeBackId) {
        this.chargeBackId = chargeBackId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPieceQuantity() {
        return pieceQuantity;
    }

    public void setPieceQuantity(BigDecimal pieceQuantity) {
        this.pieceQuantity = pieceQuantity;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    @Override
    public String toString() {
        return "StockGoodsTrade{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", businessId=" + businessId +
                ", storeId=" + storeId +
                ", skuMerchantCode='" + skuMerchantCode + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", chargeBackId='" + chargeBackId + '\'' +
                ", quantity=" + quantity +
                ", pieceQuantity=" + pieceQuantity +
                ", type=" + type +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", gmtUpdate=" + gmtUpdate +
                '}';
    }
}
