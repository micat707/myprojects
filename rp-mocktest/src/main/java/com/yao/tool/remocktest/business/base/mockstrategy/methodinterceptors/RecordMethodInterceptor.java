package com.yao.tool.remocktest.business.base.mockstrategy.methodinterceptors;

import com.yao.tool.remocktest.api.exception.RPMockException;
import com.yao.tool.remocktest.business.base.classparsegenerator.ClassFileParseApi;
import com.yao.tool.remocktest.business.base.filenamegenerator.MockFileNameGenerator;
import com.yao.tool.remocktest.utils.file.FilePathUtil;
import com.yao.tool.remocktest.utils.log.LoggerHelper;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RecordMethodInterceptor extends BaseMethodInterceptor {
    private static Logger LOG = LoggerFactory.getLogger(RecordMethodInterceptor.class);
    /**
     * RecordMockHandler 构造方法
     * @param target
     * @param fileNameGenerator
     * @param classParseGenerator
     */
    public RecordMethodInterceptor(Object target,
                                   MockFileNameGenerator fileNameGenerator, ClassFileParseApi classParseGenerator) {
        super(target,  fileNameGenerator, classParseGenerator);
    }

    @Override
    protected Object invoke(Object proxyObj, Method m, Object[] args,
                            MethodProxy proxy) throws Throwable {
        try {
            LOG.info("Record Class:{},Method:{},Args:{}", new Object[] {
                    m.getClass(), m.getName(), Arrays.deepToString(args) });
            Object result = proxy.invoke(target, args);
            record(m,args, result);
            return result;
        } catch (Exception e) {
            // 记录异常
            record(m,args, e);
            throw e;
        }
    }
    public void record(Method m, Object[] args, Object result){
        String fileName = "";
        try {
            fileName = this.fileNameGenerator.createFileName(m, args);
            fileName = FilePathUtil.getRecordFilePath(fileName);
            LOG.info("record to {}", fileName);
            classParseGenerator.writeClassToFile(result, fileName);
        } catch (IOException e) {
            LoggerHelper.logExceptionOrError(RecordMethodInterceptor.class, "record", e);
            throw new RPMockException("写文件异常！文件路径为：" + fileName);
        }

    }

}
