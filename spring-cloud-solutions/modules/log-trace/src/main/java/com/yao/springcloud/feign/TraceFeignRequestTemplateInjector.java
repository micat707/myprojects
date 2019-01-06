package com.yao.springcloud.feign;

import com.yao.springcloud.ConstantsName;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;


public class TraceFeignRequestTemplateInjector {


    public static void inject(RequestTemplate carrier) {
        String clientIp = MDC.get("CLIENT_IP");
        setHeader(carrier, ConstantsName.CLIENT_IP, clientIp);
    }


    protected static void setHeader(RequestTemplate request, String name,
                                    String value) {
        if (StringUtils.hasText(value) && !request.headers().containsKey(name)) {
            request.header(name, value);
        }
    }
}