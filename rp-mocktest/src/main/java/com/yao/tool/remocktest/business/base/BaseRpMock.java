package com.yao.tool.remocktest.business.base;

import com.yao.tool.remocktest.api.RPMockApi;
import com.yao.tool.remocktest.api.dto.RPMockEnum;
import com.yao.tool.remocktest.api.exception.RPMockException;
import com.yao.tool.remocktest.business.base.classparsegenerator.ClassFileParseApi;
import com.yao.tool.remocktest.business.base.filenamegenerator.MockFileNameGenerator;
import com.yao.tool.remocktest.business.base.filenamegenerator.MockFileNameGeneratorAbstract;
import com.yao.tool.remocktest.business.base.helper.RPMockHelper;
import com.yao.tool.remocktest.business.base.mockstrategy.BaseMockStrategy;
import com.yao.tool.remocktest.business.base.mockstrategy.CglibMockStrategy;
import com.yao.tool.remocktest.business.base.proxy.callbackfilters.AllMethodMockFilter;
import com.yao.tool.remocktest.business.base.validate.InputParamValidate;
import com.yao.tool.remocktest.utils.classes.ClassUtil;
import com.yao.tool.remocktest.utils.log.LoggerHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRpMock implements RPMockApi {

    @Override
    public <T> T mockObject(T entity, RPMockEnum mp, Class<?> mockTestClass, String caseName) {
        InputParamValidate
                .validInputParam(entity, mp, mockTestClass, caseName);
        return this.mockObject(entity, mp, mockTestClass.getSimpleName(),
                caseName);
    }

    /**
     * mock对象
     *
     * @param entity   需要mock的实体
     * @param mp       mock模式
     * @param mockFile mock 测试所在的文件名
     * @param caseName mock用例名
     * @param <T>      mock对象的类型
     * @return mock的对象
     */
    private <T> T mockObject(T entity, RPMockEnum mp, String mockFile,
                             String caseName) {
        return processNormalClass(entity, mp, mockFile, caseName);

    }

    private <T> T processNormalClass(T entity, RPMockEnum mp, String mockFile,
                                     String caseName) {
        Field[] declareFields;
        List<Field> fieldsList;
        T result;
        Class<?> classObject = entity.getClass();
        fieldsList = ClassUtil.getAllFieldArray(classObject);
        if (fieldsList != null && !fieldsList.isEmpty()) {
            declareFields = fieldsList.toArray(new Field[]{});
        } else {
            throw new RPMockException(String.format("实体没有需要处理的field,实体为：%s", entity.getClass().getCanonicalName()));
        }

        String foldName = mockFile;

        List<String> notMockMethodList = iniNotMockMethodList();
        iniAllMethodMockFilterNotMockMethodList(notMockMethodList);
        StringBuilder mockSb = new StringBuilder();
        for (int i = 0; i < declareFields.length; i++) {
            processPerField(entity, mp, mockFile, caseName, declareFields[i], classObject, foldName, mockSb);
        }
        result = entity;
        return result;
    }

    private <T> void processPerField(T entity, RPMockEnum mp, String mockFile, String caseName, Field declareField,
                                     Class<?> classObject, String foldName, StringBuilder mockSb) {
        String fieldName;
        String inputClassCanonicalName;
        try {
            fieldName = declareField.getName();
            inputClassCanonicalName = ClassUtil
                    .findFieldClassStr(declareField);
            Object targetObj = null;
            Object mockObject = null;
            mockSb.append(String.format(" scan field %s  of %s type is %s ", fieldName,
                    entity.getClass().getCanonicalName(), inputClassCanonicalName));
            declareField.setAccessible(true);
            targetObj = declareField.get(entity);
            mockObject = targetObj;
            boolean isNotNeedProcess = RPMockHelper.isNotNeedProcess(declareField, mockObject);
            if (isNotNeedProcess) {
                return;
            }
            boolean isNeedMock = isNeedMock(inputClassCanonicalName);
            isNeedMock = RPMockHelper.isRealNeedMock(declareField, isNeedMock,mockObject);
            boolean isNeedContinue = !judgeNeedContinue(declareField);
            if (isNeedMock) {
                // 判断 该域名是否需要递归mock
                if (targetObj == null) {
                    throw new RPMockException(classObject.getSimpleName()
                            + "的" + fieldName + "属性值为null");
                }
                MockFileNameGeneratorAbstract mockFileNameGeneratorAbstract = generaterRPMockFileNameGenerator();

                MockFileNameGenerator fileNameGenerator = mockFileNameGeneratorAbstract
                        .createFileNameGenerator(foldName, caseName,
                                inputClassCanonicalName, fieldName);
                BaseMockStrategy cglibMockStrategy = new CglibMockStrategy();
                mockObject = cglibMockStrategy.createTestMock(targetObj, fileNameGenerator, generatorClassParseGenerator
                                (), mp,
                        declareField.getType());

            } else {// 看不需要mock的类 是否有需要mock的字段， 递归调用
                if(isNeedContinue){
                    mockSb.append(",not mock tab，but mock continue"
                            + classObject.getCanonicalName() + "->"
                            + fieldName + ",fieldClass is"
                            + inputClassCanonicalName);

                    mockObject = mockObject(targetObj, mp, mockFile, caseName);
                }

            }
            declareField.set(entity, mockObject);
        } catch (Exception e) {

            LoggerHelper.logExceptionOrError(BaseRpMock.class,
                    "mockObject", e);
            throw new RPMockException(e.getMessage());
        } finally {
            LoggerHelper.logInfo(BaseRpMock.class,
                    "mockObject", mockSb.toString());
        }
    }

    /**
     * generatorClassParseGenerator: 实现根据对象转换为字符串 或者根据字符串转换为对象
     *
     * @return ClassParseGenerator
     * @author：micat707
     * @date：2016年3月23日 下午4:18:13
     */
    protected abstract ClassFileParseApi generatorClassParseGenerator();

    private void iniAllMethodMockFilterNotMockMethodList(
            List<String> notMockMethodList) {
        if (notMockMethodList != null && !notMockMethodList.isEmpty()) {
            for (String currentMethod : notMockMethodList) {
                AllMethodMockFilter.getNOT_MOCK_METHOD_LIST()
                        .add(currentMethod);
            }
        }

    }



    /**
     * isNeedMock: 判断是否需要mock
     *
     * @return boolean
     * @author：micat707
     * @date：2016年3月21日 上午10:41:54
     */
    protected abstract boolean isNeedMock(String inputClassCanonicalName);

    protected abstract boolean judgeNeedContinue(Field field);

    protected abstract MockFileNameGeneratorAbstract generaterRPMockFileNameGenerator();

    protected List<String> iniNotMockMethodList() {
        return new ArrayList<String>();
    }
}
