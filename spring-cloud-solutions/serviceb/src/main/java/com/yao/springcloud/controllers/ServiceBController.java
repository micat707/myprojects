package com.yao.springcloud.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceBController {
    @GetMapping("/saybHello")
    public String saybHello(String name){
        return  String.format("Hello! %s", name);
    }
}
