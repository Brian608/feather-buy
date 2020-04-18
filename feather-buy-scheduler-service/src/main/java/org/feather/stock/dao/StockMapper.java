package org.feather.stock.dao;


import org.feather.stock.entity.Stock;

public interface StockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    void syncStockByStockFlow(Long skuId);
}