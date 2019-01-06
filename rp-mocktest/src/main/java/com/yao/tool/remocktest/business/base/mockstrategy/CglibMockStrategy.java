package com.yao.tool.remocktest.business.base.mockstrategy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

public class CglibMockStrategy extends BaseMockStrategy{


    @Override
    protected <T> T createProxy(T target, Class<?> cls, Callback[] callbacks, CallbackFilter filter) {
        T result = null;
        Enhancer enhancer;
        enhancer = new Enhancer();
        //test 注意测试重复mock的情况
        enhancer.setSuperclass(cls);
        enhancer.setCallbacks(callbacks);
        if (filter != null) {
            enhancer.setCallbackFilter(filter);
        }
        result = (T) enhancer.create();
        return result;
    }
}
