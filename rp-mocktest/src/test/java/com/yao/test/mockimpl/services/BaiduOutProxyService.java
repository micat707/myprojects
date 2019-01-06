package com.yao.test.mockimpl.services;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Component
public class BaiduOutProxyService implements SearchKeyApi {
    @Resource(name = "searchKeyApiProxyService")
    private SearchKeyApi baiduService;
    public String searchKey(){
        return  baiduService.searchKey();
    }

    public static class BaiduMethodInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return methodProxy.invokeSuper(o, objects);
            //return methodProxy.invoke(o, objects);
        }
    }


}
