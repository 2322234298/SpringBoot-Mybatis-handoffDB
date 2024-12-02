package com.xxzy.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * ClassName: MyBatisConfig
 * Package: com.xxzy.common
 *
 * @Description:
 * @Author: 李志坚
 * @Create: 2024/12/1 下午1:08
 * @Version: 1.0
 */
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@Slf4j
public class MyBatisConfig {
    @Autowired
    private DatabaseConfigTool databaseConfigTool;

    @Bean
    public DynamicDataSource dynamicDataSource() {
        // 获取DynamicDataSource（单例模式）
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();

        // 数据源map集合
        Map<Object, Object> targetDataSources = databaseConfigTool.getDatabaseConfig();

        // 将多个数据源map添加进去
        dynamicDataSource.setTargetDataSources(targetDataSources);
        log.info("初始化数据源:{}",targetDataSources);
        return dynamicDataSource;
    }
}