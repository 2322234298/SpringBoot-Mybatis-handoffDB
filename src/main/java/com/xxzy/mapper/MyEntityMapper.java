package com.xxzy.mapper;

import com.xxzy.entity.MyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MyEntityMapper {


    void insert(MyEntity entity);
    void update(MyEntity entity);
    void delete(Long id);
    MyEntity selectById(Long id);
    @Select("select * from user")
    List<MyEntity> selectAll();
}
