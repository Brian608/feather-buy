package org.feather.stock.entity;

import java.util.Date;

public class StockFlow {
    private Long id;

    private Long skuId;

    private Integer stockBefore;

    private Integer stockAfter;

    private Integer stockChange;

    private Integer lockStockBefore;

    private Integer lockStockAfter;

    private Integer lockStockChange;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getStockBefore() {
        return stockBefore;
    }

    public void setStockBefore(Integer stockBefore) {
        this.stockBefore = stockBefore;
    }

    public Integer getStockAfter() {
        return stockAfter;
    }

    public void setStockAfter(Integer stockAfter) {
        this.stockAfter = stockAfter;
    }

    public Integer getStockChange() {
        return stockChange;
    }

    public void setStockChange(Integer stockChange) {
        this.stockChange = stockChange;
    }

    public Integer getLockStockBefore() {
        return lockStockBefore;
    }

    public void setLockStockBefore(Integer lockStockBefore) {
        this.lockStockBefore = lockStockBefore;
    }

    public Integer getLockStockAfter() {
        return lockStockAfter;
    }

    public void setLockStockAfter(Integer lockStockAfter) {
        this.lockStockAfter = lockStockAfter;
    }

    public Integer getLockStockChange() {
        return lockStockChange;
    }

    public void setLockStockChange(Integer lockStockChange) {
        this.lockStockChange = lockStockChange;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}