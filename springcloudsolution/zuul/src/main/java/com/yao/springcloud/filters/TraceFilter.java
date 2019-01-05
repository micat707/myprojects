package com.yao.springcloud.filters;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yao.springcloud.ConstantsName;
import com.yao.springcloud.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;


public class TraceFilter extends ZuulFilter {
    private static Logger LOGGER = LoggerFactory.getLogger(TraceFilter.class);

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
        LOGGER.info("TraceFilter run");
        RequestContext ctx = RequestContext.getCurrentContext();
        Map<String, String> requestHeaders = ctx.getZuulRequestHeaders();

        String  clientIp = MDC.get(ConstantsName.CLIENT_IP);
        setHeader(requestHeaders, ConstantsName.CLIENT_IP, clientIp);

        LOGGER.info("TraceFilter END");

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
