package com.yao.tool.remocktest.api;

import com.yao.tool.remocktest.api.dto.RPMockEnum;

public interface RPMockApi {
    /**
     *
     * @param entity 需要进行mock的对象  在mock时，会根据配置将其内容的第三方接口调用进行mock
     * @param mp mock模式  RECORD or REPLAY
     * @param mockTestClass  单元测试类 用于保存测试用例数据
     * @param caseName 测试用例
     * @param <T>
     * @return
     */
    public <T> T mockObject(T entity, RPMockEnum mp, Class<?> mockTestClass,
                            String caseName);
}
