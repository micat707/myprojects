package com.yao.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaServer
@SpringBootApplication
@RestController
public  class RegisterApplication {
    public static void main( String[] args ){
        new SpringApplicationBuilder(RegisterApplication.class).web(true).run(args);
    }


}
