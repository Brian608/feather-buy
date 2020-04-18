package org.feather.trade.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-12 07:15
 **/
@FeignClient(name = "key-generator",fallback = KeyGeneratorServiceFallback.class)
public interface KeyGeneratorServiceClient {

    @RequestMapping("/keygen")
    String KeyGenerator();

}
