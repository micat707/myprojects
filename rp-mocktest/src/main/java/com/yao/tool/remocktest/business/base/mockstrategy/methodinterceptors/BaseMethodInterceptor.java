package com.yao.tool.remocktest.business.base.mockstrategy.methodinterceptors;

import com.yao.tool.remocktest.business.base.classparsegenerator.ClassFileParseApi;
import com.yao.tool.remocktest.business.base.filenamegenerator.MockFileNameGenerator;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class BaseMethodInterceptor implements MethodInterceptor {
    private static final Logger log = LoggerFactory.getLogger(BaseMethodInterceptor.class);


    /**
     * 文件名生成器
     */
    protected MockFileNameGenerator fileNameGenerator = null;

    /**
     * 序列化 返序列化 生成器
     */
    protected ClassFileParseApi classParseGenerator;

    /**
     * proxy obj
     */
    protected Object target;


    /**
     * @param
     */
    public BaseMethodInterceptor(Object target, MockFileNameGenerator fileNameGenerator, ClassFileParseApi
            classParseGenerator) {
        this.target = target;
        this.fileNameGenerator = fileNameGenerator;
        this.classParseGenerator = classParseGenerator;
    }

    public Object intercept(Object obj, Method m, Object[] args,
                            MethodProxy proxy) throws Throwable {
        Object result = null;
        try {
            result = invoke(obj, m, args, proxy);
        } catch (Exception e) {
            log.error("invoke unknown error ", e);
            throw e;
        } finally {
            log.info("Method:{},Args:{},Return:{}", new Object[]{m.getName(),
                    Arrays.deepToString(args), result});
        }
        return result;
    }

    /**
     * 拦截器方法，用于控制replay,record的行为
     *
     * @param proxyObj Dynamic proxy object
     * @param m        method
     * @param args     method args
     * @param proxy
     * @return
     * @throws Throwable
     */
    protected abstract Object invoke(Object obj, Method m, Object[] args,
                                     MethodProxy proxy) throws Throwable;


    /**
     * 获取 文件名生成器
     *
     * @return fileNameGenerator 文件名生成器
     */
    public MockFileNameGenerator getFileNameGenerator() {
        return this.fileNameGenerator;
    }

    /**
     * 设置 文件名生成器
     *
     * @param fileNameGenerator 文件名生成器
     */
    public void setFileNameGenerator(MockFileNameGenerator fileNameGenerator) {
        this.fileNameGenerator = fileNameGenerator;
    }

    /**
     * 获取 序列化 返序列化 生成器
     *
     * @return classParseGenerator 序列化 返序列化 生成器
     */
    public ClassFileParseApi getClassParseGenerator() {
        return this.classParseGenerator;
    }

    /**
     * 设置 序列化 返序列化 生成器
     *
     * @param classParseGenerator 序列化 返序列化 生成器
     */
    public void setClassParseGenerator(ClassFileParseApi classParseGenerator) {
        this.classParseGenerator = classParseGenerator;
    }

    /**
     * 获取 proxy obj
     *
     * @return target proxy obj
     */
    public Object getTarget() {
        return this.target;
    }

    /**
     * 设置 proxy obj
     *
     * @param target proxy obj
     */
    public void setTarget(Object target) {
        this.target = target;
    }
}
