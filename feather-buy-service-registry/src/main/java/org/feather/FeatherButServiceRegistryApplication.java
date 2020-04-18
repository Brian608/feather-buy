package org.feather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-09 16:58
 **/
@SpringBootApplication
@EnableEurekaServer
public class FeatherButServiceRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatherButServiceRegistryApplication.class,args);
    }
}
