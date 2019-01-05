package com.yao.springcloud.services;

import com.yao.springcloud.ServiceBInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceA {
    @Autowired
    private ServiceBInterface serviceBInterface;

    public String sayhello(String name){
        return serviceBInterface.saybhello(name);
    }
}
