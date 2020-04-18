package org.feather.trade.service;

import org.feather.trade.entity.TradeItem;

import java.util.List;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-02 20:52
 **/
public interface TradeService {
    List<TradeItem> createOrder(List<TradeItem> tradeItemList);

    void payOrder(String out_trade_no);
}
