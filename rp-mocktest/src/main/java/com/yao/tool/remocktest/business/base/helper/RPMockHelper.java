package com.yao.tool.remocktest.business.base.helper;

import com.yao.tool.remocktest.utils.classes.ClassUtil;
import com.yao.tool.remocktest.utils.log.LoggerHelper;
import com.yao.tool.remocktest.utils.proxy.ProxyTargetUtils;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPMockHelper {
    /**
     * 已经mock的对象列表
     */
    private static ThreadLocal<Map<String, List<String>>> ALREADY_MOCK_LIST = new ThreadLocal<Map<String, List<String>>>();

    /**
     * 判断实体对象是否是代理
     *
     * @param entity 实体对象
     * @return 是否是代理
     */
    public static boolean judgeIsProxy(Object entity) {
        if (entity == null || entity.getClass() == null) {
            return false;
        }
        if (AopUtils.isAopProxy(entity)
                || entity.getClass().toString()
                .contains("com.sun.proxy.$Proxy")
                || entity.getClass().toString()
                .contains("com.alibaba.dubbo.common.bytecode.proxy")
                || entity.getClass().toString().contains("$$EnhancerByCGLIB$$")) {// 代理类
            return true;
        }
        return false;
    }

    /**
     * 预处理实体
     *
     * @param entity 实体对象
     * @return 返回处理后的实体对象
     */
    public static Object preProcessEntity(Object entity) {
        Object result = entity;
        try {
            if (entity != null) {
                if (judgeIsProxy(entity)) {
                    entity = getProxyType(entity);

                    List<Field> fields = ClassUtil.getAllFieldArray(entity
                            .getClass());
                    for (Field currentField : fields) {
                        if (currentField.getModifiers() != Modifier.FINAL) {
                            currentField.setAccessible(true);
                            Object value = currentField.get(entity);

                            Object proxyValue;
                            if (judgeIsProxy(value)) {
                                proxyValue = getProxyType(value);
                                currentField.set(entity, proxyValue);
                            }

                        }
                    }

                    //ProxyTargetUtils.setTarget(oldEntity, entity);
                }
            }
            result = entity;
        } catch (Exception e) {
            LoggerHelper.logDebug(RPMockHelper.class,
                    "preProcessEntity",
                    LoggerHelper.getExceptionOrErrorMessage(e));

        }
        return result;
    }

    /**
     * 获取代理类型
     *
     * @param entity 代理对象
     * @return 返回代理类型
     */
    public static Object getProxyType(Object entity) {
        return ProxyTargetUtils.getTarget(entity);
    }




    public static boolean judgeNeedProcess(Field field, boolean needContinue, boolean needMock,
                                           String caseFolderName, String classAndMockfieldCalssName, Object
                                                   needMockObject) {
        String inputClassCanonicalName = inputClassCanonicalName = ClassUtil
                .findFieldClassStr(field);
        boolean isNeedMock = needMock;
        boolean isNeedContinue = !needContinue;
        boolean isFinal = ClassUtil.isFinal(field.getModifiers());
        boolean isScan = false;
        boolean isInnerClass = needMockObject.getClass().getModifiers() == 1042;
        boolean isNotNeedProcess = (!isNeedMock && !isNeedContinue) || isFinal || isInnerClass;
        boolean needProcess = false;
        if (!isNotNeedProcess) {
            needProcess = true;
            //判断是否扫描过，如果扫描过，则跳过
            if (ALREADY_MOCK_LIST.get() == null) {
                ALREADY_MOCK_LIST.set(new HashMap<String, List<String>>());
            }
            List<String> tempResult;
            if (ALREADY_MOCK_LIST.get().containsKey(classAndMockfieldCalssName)) {
                tempResult = ALREADY_MOCK_LIST.get().get(classAndMockfieldCalssName);
                if (tempResult.contains(inputClassCanonicalName)) {
                    isScan = true;
                }
            } else {
                tempResult = new ArrayList<String>();
                tempResult.add(classAndMockfieldCalssName);
            }
            ALREADY_MOCK_LIST.get().put(caseFolderName, tempResult);
            if (isScan) {//如果已经处理过，则不会再进行mock操作。
                needProcess = false;
            }else{
                LoggerHelper.logInfo(RPMockHelper.class,
                        "judgeNeedProcess",String.format("field %s of class %s is already processed!", field.getName(),
                                needMockObject.getClass().getCanonicalName()) );
            }
        }else{
            LoggerHelper.logInfo(RPMockHelper.class,
                    "judgeNeedProcess",String.format("field %s of class %s not need process!", field.getName(),
                            needMockObject.getClass().getCanonicalName()) );
        }

        return needProcess;
    }
}
