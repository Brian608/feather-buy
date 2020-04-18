package org.feather.trade.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.feather.trade.entity.TradeItem;

import java.util.List;

@Mapper
public interface TradeItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeItem record);

    int insertSelective(TradeItem record);

    TradeItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeItem record);

    int updateByPrimaryKey(TradeItem record);

    void batchInsert(@Param("tradeItemList") List<TradeItem> tradeItemList);
}