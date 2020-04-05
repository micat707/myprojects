package com.yao.tool.remocktest.business.base.filenamegenerator;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MockFileNameGeneratorAbstract {
    /**
     * methodCallMap
     */
    private static final ThreadLocal<Map<String, Integer>> METHOD_CALL_MAP = new InheritableThreadLocal<Map<String, Integer>>() {
        public Map<String, Integer> initialValue() {
            return new HashMap<String, Integer>();
        }
    };

    /**
     * CASE_NAME_LIST:   存放用例名
     *
     */
    private static final ThreadLocal<List<String>> CASE_NAME_LIST = new InheritableThreadLocal<List<String>>() {
        public List<String> initialValue() {
            return new ArrayList<String>();
        }
    };

    /**
     * createFileNameGenerator: 描述方法的用途 构造一个FileNameGenerator
     *
     * TODO: 描述方法的执行流程–可选
     *
     * TODO: 描述如何调用方法–可选
     *
     * @param
     * @return SpringMockFileNameGenerator
     *
     * @exception
     *
     * @since Ver1.1
     */
    public MockFileNameGenerator createFileNameGenerator(
            final String testUntiName, final String caseName,
            final String fieldClassName, final String fieldName) {
        final String folderName = "" + testUntiName + File.separator + caseName
                + File.separator;
        MockFileNameGenerator result = new MockFileNameGenerator() {

            @Override
            public String createFileName(Method m, Object[] args) {
                int callTime = 0;

                String callKey = buildCallKey(caseName, fieldClassName, m, fieldName);
                if (!CASE_NAME_LIST.get().contains(caseName)) {// 每个用例前清空
                    METHOD_CALL_MAP.get().remove(callKey);
                    CASE_NAME_LIST.get().add(caseName);
                }
                if (METHOD_CALL_MAP.get().get(callKey) == null) {
                    callTime = 1;
                } else {
                    // 上次调用之后，次数+1
                    callTime = METHOD_CALL_MAP.get().get(callKey);
                    callTime++;

                }
                METHOD_CALL_MAP.get().put(callKey, callTime);
                String fullFileName = createCurrentFileName(m, args,
                        fieldClassName,caseName, callTime, fieldName);
                return folderName + fullFileName;
            }
        };
        return result;
    }

    /**
     * 构建唯一key
     * @param caseName 用例名
     * @param className 方法类名
     * @param m 方法实体
     * @return 唯一key
     */
    private static String buildCallKey(String caseName, String className,
                                       Method m, String fieldName) {
        String result = "";
        StringBuilder sb = new StringBuilder();
        sb.append(caseName);
        sb.append("#");
        String[] names = className.split("\\.");
        className = names[names.length - 1];
        sb.append(className);
        sb.append("#");
        sb.append(fieldName);
        sb.append("#");
        sb.append(m.getName());

        result = sb.toString();
        if (result.length() > 200) {
            result = result.substring(0, 200);
        }
        return result;
    }

    protected abstract String createCurrentFileName(Method m, Object[] args,
                                                    String methodClassName,String caseName, int callTime, String
                                                            fieldName);
}
