package com.yao.test.mockimpl;

import com.yao.tool.remocktest.business.base.JsonRpMockHelper;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

public class BaiduRpMock  extends JsonRpMockHelper {

    @Override
    protected boolean isNeedMock(String inputClassCanonicalName) {
        if(StringUtils.isNotEmpty(inputClassCanonicalName) && inputClassCanonicalName.contains("com.yao.test.mockimpl.services.needmockpackage")){
            return true;
        }
        return false;
    }

    @Override
    protected boolean judgeNeedContinue(Field field) {
        if(field.getType().getCanonicalName().contains("com.yao.test.mockimpl")){
            return true;
        }
        return false;
    }
}
