package org.feather.common.constants;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 系统参数
 */
@Component
@Data
public class Parameters {

    /*****redis config start*******/
    @Value("${spring.redis.url}")
    private String redisNode;
    @Value("${spring.redis.password}")
    private String redisAuth;
    /*****redis config end*******/

    /***zk config start ***/
    @Value("${zk.host}")
    private String zkHost;

    /***es config***/
    @Value("${es.host}")
    private String esHost;


}
