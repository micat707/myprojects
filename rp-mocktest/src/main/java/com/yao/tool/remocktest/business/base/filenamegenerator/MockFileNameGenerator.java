package com.yao.tool.remocktest.business.base.filenamegenerator;

import java.lang.reflect.Method;

public interface MockFileNameGenerator {
    /**
     * 创建文件名
     * @param m 方法
     * @param args 方法参数
     * @return 文件名
     */
    public String createFileName(Method m, Object[] args);
}
