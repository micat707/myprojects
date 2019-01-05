package com.yao.tool.remocktest.business.base.mockstrategy;

import com.yao.tool.remocktest.api.dto.RPMockEnum;
import com.yao.tool.remocktest.business.base.classparsegenerator.ClassFileParseApi;
import com.yao.tool.remocktest.business.base.filenamegenerator.MockFileNameGenerator;
import com.yao.tool.remocktest.business.base.mockstrategy.methodinterceptors.BaseMethodInterceptor;
import com.yao.tool.remocktest.business.base.mockstrategy.methodinterceptors.RecordMethodInterceptor;
import com.yao.tool.remocktest.business.base.mockstrategy.methodinterceptors.ReplayMethodInterceptor;
import com.yao.tool.remocktest.business.base.proxy.callbackfilters.AllMethodMockFilter;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.NoOp;

public abstract class BaseMockStrategy {
    public   <T> T createTestMock(T target,
                                             MockFileNameGenerator fileNameGenerator, ClassFileParseApi
                                                     classParseGenerator, RPMockEnum mp, Class<?> proxyClass) {


        T result;
        if(mp == RPMockEnum.RECORD){
            RecordMethodInterceptor recordMockHandlerImpl = new RecordMethodInterceptor(target, fileNameGenerator, classParseGenerator);
            result = createProxy(target,proxyClass,
                    createHandlers(target,recordMockHandlerImpl), createFilters(target));
        }else{
            //replay 不需要 target
            ReplayMethodInterceptor replayMethodInterceptor = new ReplayMethodInterceptor(null, fileNameGenerator,
                    classParseGenerator);
            result = createProxy(target,proxyClass,
                    createHandlers(target, replayMethodInterceptor), createFilters(target));
        }
        return result;
    }
    /**
     * 创建方法过滤器
     * @param target 需要创建过滤器的对象
     * @return 方法过滤器
     */
    private static CallbackFilter createFilters(Object target) {
        return new AllMethodMockFilter(target);
    }

    /**
     * 创建代理回调方法
     * @param target 需要创建回调的对象
     * @param baseRecordMockHandler 方法回调处理器
     * @return 方法回调数组
     */
    private static Callback[] createHandlers(Object target,
                                             BaseMethodInterceptor baseRecordMockHandler) {
        return new Callback[] {
                baseRecordMockHandler,
                NoOp.INSTANCE };
    }

    protected abstract  <T> T createProxy(T target, Class<?> cls,
                                              Callback[] callbacks, CallbackFilter filter);



}
