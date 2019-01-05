package com.yao.springcloud.filters;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yao.springcloud.ConstantsName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;


public class TracePreFilter extends ZuulFilter {
    private static Logger LOGGER = LoggerFactory.getLogger(TracePreFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }


    @Override
    public Object run() {
        LOGGER.info("TracePreFilter run");
        RequestContext ctx = RequestContext.getCurrentContext();
        Map<String, String> requestHeaders = ctx.getZuulRequestHeaders();

        String clientIp = ctx.getRequest().getRemoteAddr();
        //设置头信息
        setHeader(requestHeaders, ConstantsName.CLIENT_IP, clientIp);
        //添加到MDC中
        MDC.put(ConstantsName.CLIENT_IP, clientIp);
        LOGGER.info("TracePreFilter END");

        return null;
    }


    /**
     * 设置请求头
     *
     * @param request 请求头
     * @param name    参数名
     * @param value   参数值
     */
    public void setHeader(Map<String, String> request, String name, String
            value) {
        if (value != null) {
            request.put(name, value);
        }
    }

}
