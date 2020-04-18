package org.feather.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-15 23:21
 **/
@Configuration
@MapperScan(basePackages = "org.feather.stock.dao",sqlSessionFactoryRef = "stockSqlSessionFactory")
public class SqlSessionConfig {
    @Autowired
    @Qualifier("stockDataSource")
    private DataSource stockDataSource;

    @Bean(name = "stockSqlSessionFactory")
    public SqlSessionFactory stockSqlSessionFactory() throws Exception{
        SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
        factoryBean.setDataSource(stockDataSource);
        return factoryBean.getObject();
    }

    @Bean("stockSqlSessionTemplate")
    public SqlSessionTemplate stockSqlSessionTemplate() throws  Exception{
        SqlSessionTemplate template=new SqlSessionTemplate(stockSqlSessionFactory());
        return  template;

    }
}
