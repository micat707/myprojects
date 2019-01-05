package com.yao.test.testcase.normal;

import com.yao.test.mockimpl.BaiduRpMock;
import com.yao.test.mockimpl.services.BaiduOutProxyService;
import com.yao.test.mockimpl.services.SearchKeyApi;
import com.yao.tool.remocktest.api.dto.RPMockEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestNormalMock {
    @Resource(name = "baiduOutService")
    private SearchKeyApi searchKeyApi;
    @Test
    /**
     * 对baiduOutService进行mock。其中，有属性为baiduOutService是正常的方法调用，没使用代理。
     * 本测试用例，会mock baiduOutService的调用,对其中的baiduService的调用进行mock。
     * mock的文件将放在src\test\resources\mock_resources\{mockTestClass}.{caseName}/{mockClassName}#{mockfieldName}
     * #{mockMethodName}#{callTime}.json 文件中。
     */
    public void testNormal(){
        BaiduRpMock baiduRpMock = new BaiduRpMock();
        searchKeyApi = baiduRpMock.mockObject(searchKeyApi, RPMockEnum.REPLAY, TestNormalMock.class,"testNormal");
        String result = searchKeyApi.searchKey();
        Assert.assertTrue(result != null);
        Assert.assertTrue(result.length() > 0);
        System.out.println("result:" + result);
    }
}
