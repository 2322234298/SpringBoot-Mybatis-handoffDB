package com.xxzy.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;

/**
 * ClassName: DatabaseContextFilter
 * Package: com.xxzy.common
 *
 * @Description:
 * @Author: 李志坚
 * @Create: 2024/12/1 下午1:09
 * @Version: 1.0
 */
@Component
public class DatabaseContextFilter extends OncePerRequestFilter {

    @Autowired
    public DatabaseConfigTool databaseConfigTool;

    @Autowired
    public DynamicDataSource dynamicDataSource;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 获取请求中的id参数值
        String dbId = request.getParameter("id");
        System.out.println("dbId:"+dbId);

        // 判断dbId参数是否有误
        if (dbId == null) {
            // 清理上下文中的数据源
            DataSourceContext.clear();
            return;
        }

        // 如果为空 则从redis重新载入
        if (!DynamicDataSource.isExistDataSource(dbId)){
            // 从redis中获取全部数据源
            Map<Object, Object> databaseConfig = databaseConfigTool.getDatabaseConfig();
            DataSource dataSource = (DataSource)databaseConfig.get(dbId);
            // 查看对应数据源是否存在
            // 没有则直接返回
            if (dataSource == null) {
                // 清理上下文中的数据源
                DataSourceContext.clear();
                return;
            }
            // 有则覆盖更新
            dynamicDataSource.setTargetDataSources(databaseConfig);
        }

        // 根据dbId设置数据源
        try {
            DataSourceContext.setDataSource(dbId);
        } catch (Exception e) {
            // 处理异常，比如记录日志，返回错误响应等
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Database ID");
            System.out.println("e = " + e);
        }

        try {
            // 继续执行过滤链
            chain.doFilter(request, response);
        } finally {
            // 清理上下文中的数据源
            DataSourceContext.clear();
        }

    }
}
