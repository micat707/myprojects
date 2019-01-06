package com.yao.springcloud.fallback;

import com.yao.springcloud.ServiceBInterface;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * ProjectName：AccessValidateClientFallbackFactory
 * AccessValidateClientFallbackFactory
 *
 * @author：micat707
 * @date：2017-1-20 下午2:15:50
 */
@Component
public class ServiceBClientFallbackFactory implements
        FallbackFactory<ServiceBInterface> {
    private Logger LOGGER = LoggerFactory.getLogger
            (ServiceBClientFallbackFactory.class);
    @Override
    public ServiceBInterface create(Throwable cause) {
        if(cause != null){
            LOGGER.error("call serviceB error",cause);
        }
        LOGGER.error("call serviceB error,use ServiceBClientFallback");
        return new ServiceBClientFallback();
    }
}
