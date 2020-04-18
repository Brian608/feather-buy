package org.feather.trade.feign;


/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-16 09:16
 **/
public class KeyGeneratorServiceFallback implements KeyGeneratorServiceClient {


    @Override
    public String KeyGenerator() {
        return null;
    }
}
