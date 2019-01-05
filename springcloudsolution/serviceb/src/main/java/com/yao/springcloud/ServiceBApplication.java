package com.yao.springcloud;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@EnableDiscoveryClient
@SpringCloudApplication
public  class ServiceBApplication {
    public static void main( String[] args ){
        new SpringApplicationBuilder(ServiceBApplication.class).web(true).run(args);
    }


}
