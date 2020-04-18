package org.feather.stock.controller;

import org.feather.common.constants.Constants;
import org.feather.common.resp.ApiResult;
import org.feather.stock.entity.Stock;
import org.feather.stock.entity.StockReduce;
import org.feather.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-14 06:42
 **/
@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    @Qualifier("StockServiceImpl")
    private StockService stockService;


    @RequestMapping("/query/{skuId}")
    public ApiResult<Stock> queryStock(@PathVariable Long skuId){
        ApiResult<Stock> result=new ApiResult<>(Constants.RESP_STATUS_OK,"库存查询成功");
        Stock stock=new Stock();
        stock.setSkuId(skuId);
        int stockCount=stockService.queryStock(skuId);
        stock.setStock(stockCount);
        result.setData(stock);
        return result;
    }

    @RequestMapping("/reduce")
    public ApiResult<Map<Long,Integer>> reduceStock(@RequestBody List<StockReduce> stockReduceList){
        ApiResult result=new ApiResult(Constants.RESP_STATUS_OK,"库存扣减成功");
        Map<Long,Integer> resultMap=stockService.reduceStock(stockReduceList);
        result.setData(resultMap);
        return result;
    }
}
