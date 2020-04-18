package org.feather.trade.service;

import com.netflix.discovery.converters.Auto;
import org.feather.common.enums.TradeStatus;
import org.feather.common.resp.ApiResult;
import org.feather.product.dao.ProductSkuMapper;
import org.feather.product.entity.ProductSku;
import org.feather.trade.dao.TradeItemMapper;
import org.feather.trade.dao.TradeMapper;
import org.feather.trade.entity.StockReduce;
import org.feather.trade.entity.Trade;
import org.feather.trade.entity.TradeItem;
import org.feather.trade.feign.KeyGeneratorServiceClient;
import org.feather.trade.feign.StockServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-15 21:53
 **/
@Service
public class tradeServiceImpl implements TradeService {
    @Autowired
    private KeyGeneratorServiceClient keyGeneratorServiceClient;
    @Autowired
    private StockServiceClient stockServiceClient;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private TradeItemMapper tradeItemMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional
    @Override
    public List<TradeItem> createOrder(List<TradeItem> tradeItemList) {
        //唯一ID生成  snowflake 算法
        String orderNo=keyGeneratorServiceClient.KeyGenerator();
        Long tradeNo=Long.parseLong(orderNo);
        Long userUUId=tradeItemList.get(0).getUserUuid();
        List<StockReduce> stockReduceList=new ArrayList<>();
        tradeItemList.stream().forEach(param->{
            StockReduce stockReduce=new StockReduce();
            stockReduce.setOrderNo(tradeNo);
            stockReduce.setSkuId(param.getSkuId());
            stockReduce.setReduceCount(-param.getQuantity());
            stockReduceList.add(stockReduce);
        });
        // 扣减库存
     ApiResult<Map<Long,Integer>> stockResult=stockServiceClient.reduceStock(stockReduceList);
     Map<Long,Integer> stockResultMap=stockResult.getData();
     //查询相关sku的属性
        List<ProductSku> skuResult=productSkuMapper.selectBySkuIdList(stockResultMap.keySet());

        //生成订单  新建一个订单trade
        Trade trade=new Trade();
        trade.setTradeNo(tradeNo);
        trade.setStatus(TradeStatus.WAITING_PAY.getValue());
        trade.setUserUuid(userUUId);
        tradeMapper.insertSelective(trade);
        //拿到成功扣减库存的商品 插入订单商品表
        stockResultMap.keySet().stream().forEach(
                param->{
                    //扣库存失败的移除
                    if(stockResultMap.get(param)==-1){
                        TradeItem tradeItem =  tradeItemList.stream().filter(
                                item->item.getSkuId()==param
                        ).findFirst()
                                .get();
                        tradeItemList.remove(tradeItem);
                    }
                }
        );
        //计算商品价格等信息
        tradeItemList.stream().forEach(
                param->{
                    ProductSku sku = skuResult.stream().filter(
                            skuParam->param.getSkuId()==skuParam.getId()
                    ).findFirst()
                            .get();
                    param.setTradeNo(tradeNo);
                    param.setSkuImageUrl(sku.getImgUrl());
                    param.setSkuName(sku.getSkuName());
                    param.setCurrentPrice(sku.getSkuPrice());
                    param.setTotalPrice(sku.getSkuPrice().multiply(new BigDecimal(param.getQuantity())));
                    tradeItemMapper.insertSelective(param);
                }
        );

        //设置redis 订单过期
        redisTemplate.opsForValue().set(tradeNo.toString(),tradeNo.toString(),20, TimeUnit.MINUTES);
        return tradeItemList;
    }

    @Override
    @Transactional
    public void payOrder(String out_trade_no) {

        Trade trade  = tradeMapper.selectByTradeNo(Long.valueOf(out_trade_no));
        if(trade.getStatus()==TradeStatus.WAITING_PAY.getValue()){
            trade.setStatus(TradeStatus.TRADE_PAIED.getValue());
//            trade.setPayment();
            trade.setPaymentTime(new Date());
            trade.setPaymenyType((byte)1);
            tradeMapper.updateByPrimaryKeySelective(trade);

        }
    }
}
