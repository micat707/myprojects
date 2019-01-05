package com.yao.springcloud.config;

import com.netflix.config.ConfigurationManager;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ServiceBClientHystrixConfig {

    /**
     * 统一设置调用serviceb的超时时间，不需要在每个服务里单独设置
     */
   public ServiceBClientHystrixConfig(){
       ConfigurationManager.getConfigInstance().setProperty("hystrix.command.ServiceBInterface#" +
               "saybHello(String)." +
               "execution.isolation.thread.timeoutInMilliseconds", 50000);

   }

}
