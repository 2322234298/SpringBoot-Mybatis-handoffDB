package com.xxzy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@SpringBootTest
class SpringBootMyBatisApplicationTests {
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 给redis添加测试数据
    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("dbMap","{\n" +
                "\"db1\":{\n" +
                "\"username\":\"root\",\n" +
                "\"url\":\"jdbc:mysql://127.0.0.1/db1\",\n" +
                "\"password\":\"123456\"\n" +
                "},\n" +
                "\"db2\":{\n" +
                "\"username\":\"root\",\n" +
                "\"url\":\"jdbc:mysql://127.0.0.1/db2\",\n" +
                "\"password\":\"123456\"\n" +
                "}\n" +
                "}");
    }

    @Test
    void contextLoads1(){
        redisTemplate.opsForValue().set("dbMap","{\n" +
                "\"db1\":{\n" +
                "\"username\":\"root\",\n" +
                "\"url\":\"jdbc:mysql://127.0.0.1/db1\",\n" +
                "\"password\":\"123456\"\n" +
                "},\n" +
                "\"db3\":{\n" +
                "\"username\":\"root\",\n" +
                "\"url\":\"jdbc:mysql://127.0.0.1/db3\",\n" +
                "\"password\":\"123456\"\n" +
                "},\n" +
                "\"db2\":{\n" +
                "\"username\":\"root\",\n" +
                "\"url\":\"jdbc:mysql://127.0.0.1/db2\",\n" +
                "\"password\":\"123456\"\n" +
                "}\n" +
                "}");
    }

}
