package com.xxzy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xxzy.common.DatabaseConfigTool;
import com.xxzy.common.DynamicDataSource;
import com.xxzy.entity.MyEntity;
import com.xxzy.service.MyEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MyEntityController
 * Package: com.xxzy.controller
 *
 * @Description:
 * @Author: 李志坚
 * @Create: 2024/12/1 下午1:28
 * @Version: 1.0
 */
@RestController
@RequestMapping("/myEntity")
@Slf4j
public class MyEntityController {
    @Autowired
    private MyEntityService myEntityService;

    @RequestMapping("/findAll")
    public List<MyEntity> findAll() {
        List<MyEntity> all = myEntityService.findAll();
        return all;
    }

}
