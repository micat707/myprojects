package com.yao.springcloud.config;

import com.yao.springcloud.filters.TracePreFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulConfig {

    @Bean
    public TracePreFilter getTraceFilter(){
        return new TracePreFilter();
    }
}
