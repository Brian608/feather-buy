package org.feather.stock.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.stock.entity.StockFlow;

@Mapper
public interface StockFlowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockFlow record);

    int insertSelective(StockFlow record);

    StockFlow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockFlow record);

    int updateByPrimaryKey(StockFlow record);
}