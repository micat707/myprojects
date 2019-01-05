package com.yao.springcloud.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created by micat707 on 2017/6/5.
 */
public class TraceFeignRequestInterceptor implements RequestInterceptor  {

    /**
     *
     * apply:
     *
     * @return void
     * @author：micat707
     * @date：2017年11月4日 下午4:53:59
     */
    @Override
    public void apply(RequestTemplate template) {
        TraceFeignRequestTemplateInjector.inject(template);
    }
}
