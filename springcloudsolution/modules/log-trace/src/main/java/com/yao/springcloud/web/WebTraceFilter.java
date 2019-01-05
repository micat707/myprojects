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
        if(StringUtils.isEmpty(clientIp)){//如果为空，则表示第一次访问，即网关端的请求
            clientIp = httpServletRequest.getRemoteAddr();
        }
        MDC.put(ConstantsName.CLIENT_IP, clientIp);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
