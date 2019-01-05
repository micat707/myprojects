package com.yao.springcloud.web;

import com.yao.springcloud.ConstantsName;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(ConstantsName.TRACE_FILTERORDER)
public class WebTraceFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String clientIp = httpServletRequest.getHeader(ConstantsName.CLIENT_IP);
        if(StringUtils.isNotEmpty(clientIp)){//如果为空，则表示第一次访问，即网关端的请求
            MDC.put(ConstantsName.CLIENT_IP, clientIp);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        //确保每个应用处理完后 清除相关MDC的内容
        MDC.remove(ConstantsName.CLIENT_IP);

    }
}
