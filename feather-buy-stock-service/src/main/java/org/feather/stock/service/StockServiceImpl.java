package org.feather.stock.service;

import org.feather.common.constants.Constants;
import org.feather.common.utils.RedisUtils;
import org.feather.stock.dao.StockFlowMapper;
import org.feather.stock.dao.StockMapper;
import org.feather.stock.entity.Stock;
import org.feather.stock.entity.StockFlow;
import org.feather.stock.entity.StockReduce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-14 08:45
 **/
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StockFlowMapper stockFlowMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public int queryStock(Long skuId) {
        Stock stock;
        String stockKey= Constants.CACHE_PRODUCT_STOCK+":"+skuId;
        String stockLockKey=Constants.CACHE_PRODUCT_STOCK_LOCK+":"+skuId;

        Object stockObj = redisTemplate.opsForValue().get(stockKey);
        Integer stockInRedis=null;
        if (stockObj!=null){
            stockInRedis=Integer.valueOf(stockObj.toString());
        }
        if (stockInRedis==null){
            //库存redis为空，设置库存
            stock=stockMapper.selectBySkuId(skuId);
            redisUtils.skuStockInit(stockKey,stockLockKey,stock.getStock().toString(),stock.getLockStock().toString());


        }else {
            return  stockInRedis;
        }

        return stock.getStock();
    }

    @Override
    @Transactional
    public Map<Long, Integer> reduceStock(List<StockReduce> stockReduceList) {
        //以订单ID 加个缓存锁 防止程序短时间重试 重复扣减库存 不用解锁 自己超时
        Long orderNo = stockReduceList.get(0).getOrderNo();
        boolean lockResult = redisUtils.distributeLock(Constants.ORDER_RETRY_LOCK+orderNo.toString(),orderNo.toString(),300000);
        if(!lockResult){
            //锁定失败 重复提交
            return  Collections.EMPTY_MAP;
        }
        Map<Long,Integer> resultMap = new HashMap<>();
        //扣减每个商品的redis库存 扣减流水插入流水表
        stockReduceList.stream().forEach(param->{
            String stockKey = Constants.CACHE_PRODUCT_STOCK+":"+param.getSkuId();
            String stockLockKey = Constants.CACHE_PRODUCT_STOCK_LOCK+":"+param.getSkuId();
            Object result = redisUtils.reduceStock(stockKey,stockLockKey,param.getReduceCount().toString(),String.valueOf(Math.abs(param.getReduceCount())));
            if(result instanceof Long){
                //库存不存在或者不足 扣减失败 sku下单失败 记录下来
                resultMap.put(param.getSkuId(),-1);
            }else if (result instanceof List){
                //扣减成功 记录扣减流水
                List resultList =  ((List) result);
                int stockAftChange =  ((Long)resultList.get(0)).intValue();
                int stockLockAftChange = ((Long) resultList.get(1)).intValue();
                StockFlow stockFlow = new StockFlow();
                stockFlow.setOrderNo(param.getOrderNo());
                stockFlow.setSkuId(param.getSkuId());
                stockFlow.setLockStockAfter(stockLockAftChange);
                stockFlow.setLockStockBefore(stockLockAftChange+param.getReduceCount());
                stockFlow.setLockStockChange(Math.abs(param.getReduceCount()));
                stockFlow.setStockAfter(stockAftChange);
                stockFlow.setStockBefore(stockAftChange+Math.abs(param.getReduceCount()));
                stockFlow.setStockChange(param.getReduceCount());
                stockFlowMapper.insertSelective(stockFlow);
                resultMap.put(param.getSkuId(),1);
            }
        });

        return resultMap;
    }
}
