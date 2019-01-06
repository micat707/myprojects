package com.yao.springcloud;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RestController;


@EnableEurekaClient
@EnableDiscoveryClient
@SpringCloudApplication
@EnableFeignClients
public  class ServiceAApplication {
    public static void main( String[] args ){
        new SpringApplicationBuilder(ServiceAApplication.class).web(true).run(args);
    }


}
