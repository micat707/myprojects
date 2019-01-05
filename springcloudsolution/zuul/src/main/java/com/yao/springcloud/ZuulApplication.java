package com.yao.springcloud;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RestController;


@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
@SpringCloudApplication
public  class ZuulApplication {
    public static void main( String[] args ){
        new SpringApplicationBuilder(ZuulApplication.class).web(true).run(args);
    }


}
