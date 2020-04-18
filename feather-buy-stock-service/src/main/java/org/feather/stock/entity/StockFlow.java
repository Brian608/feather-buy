package org.feather.stock.entity;

import lombok.Data;

import java.util.Date;

@Data
public class StockFlow {
    private Long id;

    private Long orderNo;

    private Long skuId;

    private Integer stockBefore;

    private Integer stockAfter;

    private Integer stockChange;

    private Integer lockStockBefore;

    private Integer lockStockAfter;

    private Integer lockStockChange;

    private Date createTime;

    private Date updateTime;

}