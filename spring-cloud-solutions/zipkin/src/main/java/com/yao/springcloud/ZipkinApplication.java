package com.yao.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer
public  class ZipkinApplication {
    public static void main( String[] args ){
        new SpringApplicationBuilder(ZipkinApplication.class).web(true).run(args);
    }


}
