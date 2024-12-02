
package com.xxzy.common;

/**
 * 通过ThreadLocal线程控制单次请求的数据源
 */
public class DataSourceContext {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setDataSource(String str) {
        CONTEXT.set(str);
    }

    public static String getDataSource() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}