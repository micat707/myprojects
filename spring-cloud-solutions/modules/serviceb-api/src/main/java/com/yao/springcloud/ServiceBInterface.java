package com.yao.springcloud;

import com.yao.springcloud.fallback.ServiceBClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "serviceb", fallbackFactory =
        ServiceBClientFallbackFactory.class)
public interface ServiceBInterface {
    @RequestMapping(value="/saybHello",method = RequestMethod
            .GET)
    String saybHello(@RequestParam("name") String name);
}
