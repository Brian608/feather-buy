package org.feather.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-15 06:16
 **/
@Configuration
public class DataSourceConfig {
    @Bean("stockDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.stock")
    public DataSource stockDataSource(){
        return DataSourceBuilder.create().build();
    }
}
