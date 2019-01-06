package com.yao.tool.remocktest.business.base.filenamegenerator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MockFileNameGeneratorAbstract {
    /**
     * methodCallMap
     */
    private static final InheritableThreadLocal<Map<String, Integer>> METHODCALLMAP = new InheritableThreadLocal<Map<String, Integer>>() {
        public Map<String, Integer> initialValue() {
            return new HashMap<String, Integer>();
        }
    };

    /**
     * CASENAMELIST:   存放用例名
     *
     */
    private static final InheritableThreadLocal<List<String>> CASENAMELIST = new InheritableThreadLocal<List<String>>() {
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
            final String folderName, final String caseName,
            final String className, final String fieldName) {

        MockFileNameGenerator result = new MockFileNameGenerator() {

            @Override
            public String createFileName(Method m, Object[] args) {
                int callTime = 0;

                String callKey = buildCallKey(caseName, className, m, fieldName);
                if (!CASENAMELIST.get().contains(caseName)) {// 每个用例前清空
                    METHODCALLMAP.get().remove(callKey);
                    CASENAMELIST.get().add(caseName);
                }
                if (METHODCALLMAP.get().get(callKey) == null) {
                    callTime = 1;
                } else {
                    callTime = METHODCALLMAP.get().get(callKey);
                    // 上次调用之后，次数+1
                    callTime++;

                }
                METHODCALLMAP.get().put(callKey, callTime);
                String fullFileName = createCurrentFileName(m, args,
                        className,caseName, callTime, fieldName);
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

    /**
     * resetThreadLocalInfo:   void
     * @author：micat707   重置本地线程变量
     * @date：2016年4月19日 上午9:40:56
     */
    public static void resetThreadLocalInfo(){
        METHODCALLMAP.get().clear();
        CASENAMELIST.get().clear();
    }
}
