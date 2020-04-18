package org.feather.stock.dao;


import org.apache.ibatis.annotations.Mapper;
import org.feather.stock.entity.Stock;
import org.feather.stock.entity.StockReduce;

@Mapper
public interface StockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    int reduceStock(StockReduce stockReduce);

    Stock selectBySkuId(Long skuId);
}