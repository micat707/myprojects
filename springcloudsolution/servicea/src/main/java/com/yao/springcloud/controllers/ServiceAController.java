package com.yao.springcloud.controllers;

import com.yao.springcloud.services.ServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceAController {
    @Autowired
    private ServiceA serviceA;
    @GetMapping("/sayHello")
    public String sayHello(){
        return  serviceA.sayHello("ServiceA");
    }
}
