package com.yao.tool.remocktest.business.base.mockstrategy.methodinterceptors;

import com.yao.tool.remocktest.api.exception.RPMockException;
import com.yao.tool.remocktest.business.base.classparsegenerator.ClassFileParseApi;
import com.yao.tool.remocktest.business.base.filenamegenerator.MockFileNameGenerator;
import com.yao.tool.remocktest.utils.file.FilePathUtil;
import com.yao.tool.remocktest.utils.log.LoggerHelper;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReplayMethodInterceptor extends BaseMethodInterceptor {
    private static Logger LOG = LoggerFactory.getLogger(ReplayMethodInterceptor.class);

    /**
     * 构造方法
     * @param target
     * @param fileNameGenerator
     * @param classParseGenerator
     */
    public ReplayMethodInterceptor(Object target,
                                 MockFileNameGenerator fileNameGenerator, ClassFileParseApi classParseGenerator) {
        super(target, fileNameGenerator, classParseGenerator);
    }
    @Override
    protected Object invoke(Object proxyObj, Method m, Object[] args,
                            MethodProxy proxy) throws Throwable {
        LOG.debug("begin to replay");
        LOG.info("Replay Class:{},Method:{},Args:{}", new Object[] {
                m.getClass(), m.getName(), Arrays.deepToString(args) });
        Object result = replay(m, args);

        if (result instanceof Throwable)// 复原异常
            throw (Throwable) result;
        return result;
    }
    /**
     * 回放模式  读取文件内容 将其反序列化为对象
     * @param m 方法实体
     * @param args 方法参数
     * @return 返回 反序列化的对象
     */
    public Object replay(Method m, Object[] args) {
        String fileName = this.fileNameGenerator.createFileName(m, args);


        Object result = null;
        try {
            fileName = FilePathUtil.getRecordFilePath(fileName);
            LOG.info("Replay From {}", fileName);
            if(StringUtils.isNotEmpty(fileName)){
                result = classParseGenerator.parseFileToClass(fileName, m.getReturnType());
            }
        } catch (IOException e) {
            LoggerHelper.logExceptionOrError(ReplayMethodInterceptor.class, "replay", e);
            throw new RPMockException("转换对象异常！文件路径为：" + fileName);
        }

        return result;
    }
}
