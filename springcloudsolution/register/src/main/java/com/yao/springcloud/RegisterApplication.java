package com.yao.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@EnableEurekaServer
@SpringBootApplication
@RestController
public  class RegisterApplication {
    public static void main( String[] args ){
        new SpringApplicationBuilder(RegisterApplication.class).web(true).run(args);
    }


}
