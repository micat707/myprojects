package com.yao.tool.remocktest.business.base.validate;

import com.yao.tool.remocktest.api.dto.RPMockEnum;
import com.yao.tool.remocktest.api.exception.RPMockException;
import com.yao.tool.remocktest.business.base.BaseRpMock;
import com.yao.tool.remocktest.business.base.helper.RPMockHelper;
import com.yao.tool.remocktest.utils.log.LoggerHelper;
import org.apache.commons.lang.StringUtils;

public class InputParamValidate {
    public static void validInputParam(Object entity, RPMockEnum mp,
                                       Class<?> mockTestClass, String caseName) {
        if (mp == null) {
            throw new RPMockException("mock模式为空！");
        }
        if (entity == null) {
            throw new RPMockException("entity为空！");
        }
        if (mockTestClass == null) {
            throw new RPMockException("单元测试类为空！");
        }
        if (StringUtils.isEmpty(caseName)) {
            throw new RPMockException("用例名称为空！");
        }
        if (RPMockHelper.judgeIsProxy(entity)) {
            LoggerHelper.logInfo(BaseRpMock.class, "mockObject", String.format("动态代理不进行内部mock %s",
                    entity
                            .getClass().getCanonicalName()));

            throw new RPMockException(String.format("Do not process proxy entity! entity is %s", entity.getClass()));
            //entity = (T) RPMockHelper.findProxyProject(entity);
        }
    }
}
