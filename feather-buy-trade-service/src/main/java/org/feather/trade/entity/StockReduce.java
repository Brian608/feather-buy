package org.feather.trade.entity;

import lombok.Data;


@Data
public class StockReduce {

    private Long orderNo;

    private Long skuId;

    private Integer reduceCount; //正数为增加/释放库存  负数为扣减

}
