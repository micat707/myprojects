package com.yao.tool.remocktest.business.base.validate;

import com.yao.tool.remocktest.api.dto.RPMockEnum;
import com.yao.tool.remocktest.api.exception.RPMockException;
import org.apache.commons.lang.StringUtils;

public class InputParamValidate {
    public static void validInputParam(Object entity, RPMockEnum mp,
                                       String mockFile, String caseName) {
        if (mp == null) {
            throw new RPMockException("mock模式为空！");
        }
        if (StringUtils.isBlank(mockFile)) {
            throw new RPMockException("mock文件存取路径为空！");
        }
        if (StringUtils.isEmpty(caseName)) {
            throw new RPMockException("用例名称为空！");
        }
    }
}
