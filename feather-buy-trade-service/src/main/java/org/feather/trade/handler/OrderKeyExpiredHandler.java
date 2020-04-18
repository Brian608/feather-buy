package org.feather.trade.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-16 22:15
 **/
@Configuration
public class OrderKeyExpiredHandler {
    @Bean
    public RedisMessageListenerContainer configRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory){
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(redisConnectionFactory);
        listenerContainer.addMessageListener((message,listener)->{
            //处理key过期事件逻辑
            System.out.println("------redis过期事件"+new String(message.getBody()));
        }, new PatternTopic("__keyevent@*__:expired"));
        return listenerContainer;
    }
}
