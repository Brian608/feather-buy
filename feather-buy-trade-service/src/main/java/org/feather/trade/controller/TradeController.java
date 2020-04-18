package org.feather.trade.controller;

import org.feather.common.constants.Constants;
import org.feather.common.resp.ApiResult;
import org.feather.trade.entity.TradeItem;
import org.feather.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-10 22:50
 **/
@RestController
@RequestMapping
public class TradeController {
    @Autowired
    @Qualifier("/tradeServiceImpl")
    private TradeService tradeService;

    @RequestMapping("/order")
    public ApiResult<List<TradeItem>> createOrder(@RequestBody List<TradeItem> tradeItemList){

        ApiResult<List<TradeItem>> result = new ApiResult(Constants.RESP_STATUS_OK,"订单提交成功");

        List<TradeItem> tradeItemSuccResult =tradeService.createOrder(tradeItemList);
        result.setData(tradeItemSuccResult);

        return  result;
    }

}
