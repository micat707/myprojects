package com.yao.test.testcase.proxy;

import com.yao.test.mockimpl.BaiduRpMock;
import com.yao.test.mockimpl.services.BaiduOutProxyService;
import com.yao.test.mockimpl.services.SearchKeyApi;
import com.yao.test.mockimpl.services.needmockpackage.SearchKeyApiProxyService;
import com.yao.tool.remocktest.api.dto.RPMockEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestProxyMock {
    @Resource(name = "searchKeyApiProxyService")
    private SearchKeyApiProxyService searchKeyApi;

    private SearchKeyApi searchKeyApiProxy;
    @Test
    /**
     * 对searchKeyApiProxyService进行mock。其中，有属性为baiduService是动态代理。
     * 本测试用例，会mock baiduService的调用。
     * mock的文件将放在src\test\resources\mock_resources\{mockTestClass}.{caseName}/{mockClassName}#{mockfieldName}
     * #{mockMethodName}#{callTime}.json 文件中。
     */
    public void testNormal(){
        BaiduRpMock baiduRpMock = new BaiduRpMock();
        /*
        //RECORD 方式调用
        searchKeyApi = baiduRpMock.mockObject(searchKeyApi, RPMockEnum.RECORD, TestProxyMock.class,"testNormal");
*/
        searchKeyApi = baiduRpMock.mockObject(searchKeyApi, RPMockEnum.REPLAY, TestProxyMock.class,"testNormal");
        String result = searchKeyApi.searchKey();
        Assert.assertTrue(result != null);
        Assert.assertTrue(result.length() > 0);
        System.out.println("result:" + result);
    }

    @Test
    /**
     * 动态代理不能作为 mockObject参数对象，这种mock不具有意义。
     *
     */
    public void testProxyError(){
        BaiduRpMock baiduRpMock = new BaiduRpMock();
        //获取动态代理
        SearchKeyApi currentsearchKeyApiProxy = searchKeyApi.getBaiduService();
        //对动态代理进行mock
        currentsearchKeyApiProxy = baiduRpMock.mockObject(currentsearchKeyApiProxy, RPMockEnum.RECORD, TestProxyMock.class,"testNormal");
        String result = searchKeyApi.searchKey();
        Assert.assertTrue(result != null);
        Assert.assertTrue(result.length() > 0);
        System.out.println("result:" + result);
    }
}
