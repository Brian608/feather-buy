package org.feather.stock.service;

import org.feather.stock.entity.StockReduce;

import java.util.List;
import java.util.Map;

public interface StockService {
    int queryStock(Long skuId);

    Map<Long, Integer> reduceStock(List<StockReduce> stockReduceList);
}
