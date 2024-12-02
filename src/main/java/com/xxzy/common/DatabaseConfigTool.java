package com.xxzy.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: DatabaseConfigTool
 * Package: com.xxzy.common
 *
 * @Description:
 * @Author: 李志坚
 * @Create: 2024/12/1 下午1:07
 * @Version: 1.0
 */
@Component
@Slf4j
public class DatabaseConfigTool {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 从redis中获取数据源map集合
     *
     * @return 数据源map集合
     */
    public Map<Object, Object> getDatabaseConfig() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        try {
            // 获取全部的DataSource配置信息
            String json = redisTemplate.opsForValue().get("dbMap");
            log.info("json = " + json);
            // 类型转换
            Map<String, Map<String, String>> dbMap = new ObjectMapper().readValue(json, Map.class);
            for (String key : dbMap.keySet()) {
                // DataSource配置信息
                Map<String, String> value = dbMap.get(key);
                // 将数据源添加至map
                targetDataSources.put(key, toDataSource(value));
                log.info("MyBatisConfig.dynamicDataSource:{}" + value);
            }
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            // 当json异常时给予补救措施
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://127.0.0.1/db1");
            dataSource.setUsername("root");
            dataSource.setPassword("123456");
            targetDataSources.put("db1", dataSource);
            log.error("redis中返回的结果 json格式有错，已临时补救 请及时更正");
        }
        return targetDataSources;
    }


    /**
     * 将map转换为DataSource
     *
     * @param dbConfig 存放数据连接的map
     * @return DataSource
     */
    public DataSource toDataSource(Map<String, String> dbConfig) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        System.out.println(dbConfig);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dbConfig.get("url"));
        dataSource.setUsername(dbConfig.get("username"));
        dataSource.setPassword(dbConfig.get("password"));
        return dataSource;
    }
}