package com.gold.wang.sharding.jdbc.entity;


import java.math.BigDecimal;
import java.util.Date;


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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPieceUnit() {
        return pieceUnit;
    }

    public void setPieceUnit(BigDecimal pieceUnit) {
        this.pieceUnit = pieceUnit;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getBuyStock() {
        return buyStock;
    }

    public void setBuyStock(BigDecimal buyStock) {
        this.buyStock = buyStock;
    }

    public BigDecimal getPieceBuyStock() {
        return pieceBuyStock;
    }

    public void setPieceBuyStock(BigDecimal pieceBuyStock) {
        this.pieceBuyStock = pieceBuyStock;
    }

    public BigDecimal getWaittingAreaStock() {
        return waittingAreaStock;
    }

    public void setWaittingAreaStock(BigDecimal waittingAreaStock) {
        this.waittingAreaStock = waittingAreaStock;
    }

    public BigDecimal getPieceWaittingAreaStock() {
        return pieceWaittingAreaStock;
    }

    public void setPieceWaittingAreaStock(BigDecimal pieceWaittingAreaStock) {
        this.pieceWaittingAreaStock = pieceWaittingAreaStock;
    }

    public BigDecimal getUnqualifiedAreaStock() {
        return unqualifiedAreaStock;
    }

    public void setUnqualifiedAreaStock(BigDecimal unqualifiedAreaStock) {
        this.unqualifiedAreaStock = unqualifiedAreaStock;
    }

    public BigDecimal getPieceUnqualifiedAreaStock() {
        return pieceUnqualifiedAreaStock;
    }

    public void setPieceUnqualifiedAreaStock(BigDecimal pieceUnqualifiedAreaStock) {
        this.pieceUnqualifiedAreaStock = pieceUnqualifiedAreaStock;
    }

    public BigDecimal getWaitStock() {
        return waitStock;
    }

    public void setWaitStock(BigDecimal waitStock) {
        this.waitStock = waitStock;
    }

    public BigDecimal getTransitStock() {
        return transitStock;
    }

    public void setTransitStock(BigDecimal transitStock) {
        this.transitStock = transitStock;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public String getProduceCompany() {
        return produceCompany;
    }

    public void setProduceCompany(String produceCompany) {
        this.produceCompany = produceCompany;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public Short getLocked() {
        return locked;
    }

    public void setLocked(Short locked) {
        this.locked = locked;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public Byte getApplyType() {
        return applyType;
    }

    public void setApplyType(Byte applyType) {
        this.applyType = applyType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
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
        return "StockGoodsBatchCode{" +
                "id=" + id +
                ", businessId=" + businessId +
                ", storeId=" + storeId +
                ", skuMerchantCode='" + skuMerchantCode + '\'' +
                ", batchCode='" + batchCode + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", pieceUnit=" + pieceUnit +
                ", stock=" + stock +
                ", buyStock=" + buyStock +
                ", pieceBuyStock=" + pieceBuyStock +
                ", waittingAreaStock=" + waittingAreaStock +
                ", pieceWaittingAreaStock=" + pieceWaittingAreaStock +
                ", unqualifiedAreaStock=" + unqualifiedAreaStock +
                ", pieceUnqualifiedAreaStock=" + pieceUnqualifiedAreaStock +
                ", waitStock=" + waitStock +
                ", transitStock=" + transitStock +
                ", unit='" + unit + '\'' +
                ", expireDate=" + expireDate +
                ", produceDate=" + produceDate +
                ", produceCompany='" + produceCompany + '\'' +
                ", supply='" + supply + '\'' +
                ", locked=" + locked +
                ", syncDate=" + syncDate +
                ", applyType=" + applyType +
                ", version=" + version +
                ", extend='" + extend + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", gmtUpdate=" + gmtUpdate +
                '}';
    }
}
