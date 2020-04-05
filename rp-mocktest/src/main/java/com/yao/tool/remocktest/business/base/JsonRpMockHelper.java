package com.yao.tool.remocktest.business.base;

import com.yao.tool.remocktest.business.base.classparsegenerator.ClassFileParseApi;
import com.yao.tool.remocktest.business.base.classparsegenerator.JsonClassStringParseGenerator;
import com.yao.tool.remocktest.business.base.filenamegenerator.MockFileNameGeneratorAbstract;
import com.yao.tool.remocktest.business.base.filenamegenerator.RPMockFileNameJsonGenerator;

import java.lang.reflect.Field;

public abstract class JsonRpMockHelper extends  BaseRpMock {
    @Override
    protected ClassFileParseApi generatorClassParseGenerator() {
        return new JsonClassStringParseGenerator();
    }

    @Override
    protected MockFileNameGeneratorAbstract generaterRPMockFileNameGenerator() {
        return new RPMockFileNameJsonGenerator();
    }
}
