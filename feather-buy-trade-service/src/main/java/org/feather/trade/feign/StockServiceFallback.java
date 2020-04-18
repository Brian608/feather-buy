package org.feather.trade.feign;

import org.feather.common.constants.Constants;
import org.feather.common.resp.ApiResult;
import org.feather.trade.entity.StockReduce;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-16 08:54
 **/
public class StockServiceFallback  implements StockServiceClient{
    @Override
    public ApiResult<Map<Long, Integer>> reduceStock(List<StockReduce> stockReduceList) {
        ApiResult<Map<Long, Integer>> result = new ApiResult(Constants.RESP_STATUS_INTERNAL_ERROR,"请求失败,请稍后再试");
        result.setData(Collections.EMPTY_MAP);
        return result;
    }
}
