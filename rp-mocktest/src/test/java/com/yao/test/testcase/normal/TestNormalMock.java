package com.yao.test.testcase.normal;

import com.yao.test.mockimpl.BaiduRpMock;
import com.yao.test.mockimpl.services.BaiduOutService;
import com.yao.tool.remocktest.api.dto.RPMockEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestNormalMock {
    @Autowired
    private BaiduOutService baiduOutService;
    @Test
    /**
     * 对baiduOutService进行mock。其中，有属性为baiduOutService是正常的方法调用。
     * 本测试用例，会mock baiduOutService的调用,对其中的baiduService的调用进行mock。
     * mock的文件将放在src\test\resources\mock_resources\{mockTestClass}.{caseName}/{mockClassName}#{mockfieldName}
     * #{mockMethodName}#{callTime}.json 文件中。
     */
    public void testNormal(){
        BaiduRpMock baiduRpMock = new BaiduRpMock();
        baiduOutService = baiduRpMock.mockObject(baiduOutService, RPMockEnum.REPLAY, TestNormalMock.class,"testNormal");
        String result = baiduOutService.searchKey("mock");
        Assert.assertTrue(result != null);
        Assert.assertTrue(result.length() > 0);
        System.out.println("result:" + result);
    }
}
