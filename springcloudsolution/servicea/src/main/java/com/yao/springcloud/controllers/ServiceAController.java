package com.yao.springcloud.controllers;

import com.yao.springcloud.services.ServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Unsafe;

@RestController
public class ServiceAController {
    @Autowired
    private ServiceA serviceA;
    @GetMapping("/sayhello")
    public String sayhello(){
        return  serviceA.sayhello("ServiceA");
    }
}
