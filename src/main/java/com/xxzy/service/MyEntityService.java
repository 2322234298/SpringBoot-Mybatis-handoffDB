package com.xxzy.service;

import com.xxzy.entity.MyEntity;
import com.xxzy.mapper.MyEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyEntityService {
    @Autowired
    private MyEntityMapper myEntityMapper;

    public void save(MyEntity entity) {
        myEntityMapper.insert(entity);
    }

    public List<MyEntity> findAll() {
        return myEntityMapper.selectAll();
    }
}
