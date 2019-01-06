package com.yao.tool.remocktest.business.base.classparsegenerator;

import java.io.IOException;

public interface ClassFileParseApi {

    /**
     * 将类写到文件
     * @param classEntity
     * @param fullFileName
     * @return
     */
    public void writeClassToFile(Object classEntity, String fullFileName) throws IOException;

    /**
     * 将文件写到类
     * @param fullFileName
     * @return
     */
    public Object parseFileToClass(String fullFileName, Class<?> returnType) throws IOException;

}
