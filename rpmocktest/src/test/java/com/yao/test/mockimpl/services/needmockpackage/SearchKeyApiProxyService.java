package com.yao.test.mockimpl.services.needmockpackage;

import com.yao.test.mockimpl.services.BaiduOutProxyService;
import com.yao.test.mockimpl.services.SearchKeyApi;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

@Component
public class SearchKeyApiProxyService implements SearchKeyApi {


    private BaiduService baiduService;
    public SearchKeyApiProxyService(){
        baiduService =(BaiduService) createProxy(BaiduService.class);

    }

    public BaiduService getBaiduService() {
        return baiduService;
    }

    @Override
    public String searchKey() {
        return baiduService.searchKey();
    }
    private Object createProxy(Class classt){

/*//生成代理类到指定路径
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\yao\\code\\test\\rpmocktest\\src\\test\\java\\com\\yao\\test\\testcase\\services");
*/
        Object result = null;
        Enhancer enhancer;
        enhancer = new Enhancer();
        //test 注意测试重复mock的情况
        enhancer.setSuperclass(classt);
        enhancer.setCallback(new BaiduOutProxyService.BaiduMethodInterceptor());
        result = enhancer.create();
        return result;
    }
}
