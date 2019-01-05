package com.yao.springcloud.config;

import com.yao.springcloud.filters.TraceFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulConfig {

    @Bean
    public TraceFilter getTraceFilter(){
        return new TraceFilter();
    }
}
