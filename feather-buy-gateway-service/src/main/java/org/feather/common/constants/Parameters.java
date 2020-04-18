package org.feather.common.constants;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统参数
 */
@Component
@Data
public class Parameters {

    @Value("#{'${security.noneSecurityPath}'.split(',')}")
    private  List<String> noneSecurityPath;

    /*****redis config start*******/
    @Value("${spring.redis.url}")
    private String redisNode;
    @Value("${spring.redis.password}")
    private String redisAuth;
    /*****redis config end*******/

}
