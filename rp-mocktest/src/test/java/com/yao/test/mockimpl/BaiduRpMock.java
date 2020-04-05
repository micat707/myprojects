package com.yao.test.mockimpl;

import com.yao.tool.remocktest.business.base.BaseRpMock;
import com.yao.tool.remocktest.business.base.classparsegenerator.ClassFileParseApi;
import com.yao.tool.remocktest.business.base.classparsegenerator.JsonClassStringParseGenerator;
import com.yao.tool.remocktest.business.base.filenamegenerator.MockFileNameGeneratorAbstract;
import com.yao.tool.remocktest.business.base.filenamegenerator.RPMockFileNameJsonGenerator;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

public class BaiduRpMock  extends BaseRpMock {

    @Override
    protected boolean isNeedMock(String inputClassCanonicalName) {
        if(StringUtils.isNotEmpty(inputClassCanonicalName) && inputClassCanonicalName.contains("com.yao.test.mockimpl.services.needmockpackage")){
            return true;
        }
        return false;
    }
    @Override
    protected ClassFileParseApi generatorClassParseGenerator() {
        return new JsonClassStringParseGenerator();
    }

    @Override
    protected MockFileNameGeneratorAbstract generaterRPMockFileNameGenerator() {
        return new RPMockFileNameJsonGenerator();
    }
    @Override
    protected boolean judgeNeedContinue(Field field) {
        if(field.getType().getCanonicalName().contains("com.yao.test.mockimpl")){
            return true;
        }
        return false;
    }
}
