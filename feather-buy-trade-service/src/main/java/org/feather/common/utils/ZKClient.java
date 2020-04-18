package org.feather.common.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.feather.common.constants.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-13 09:07
 **/
@Component
public class ZKClient {
    @Autowired
    private Parameters parameters;

    @Bean
    public CuratorFramework getZKClient(){
        CuratorFrameworkFactory.Builder builder=CuratorFrameworkFactory.builder()
                .connectString(parameters.getZkHost())
                .connectionTimeoutMs(3000)//链接超时3秒
                .retryPolicy(new RetryNTimes(5,500));
        CuratorFramework framework=builder.build();
        framework.start();
        return  framework;
    }
}
