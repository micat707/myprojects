package com.yao.springcloud.config;

import com.yao.springcloud.feign.TraceFeignRequestInterceptor;
import com.yao.springcloud.hystrix.TraceSleuthHystrixConcurrencyStrategy;
import com.yao.springcloud.sleuth.TraceZipkinSpanReporter;
import com.yao.springcloud.web.WebTraceFilter;
import feign.RequestInterceptor;
import org.springframework.cloud.sleuth.TraceKeys;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TraceConfig {



    @Bean
    public TraceSleuthHystrixConcurrencyStrategy getTraceSleuthHystrixConcurrencyStrategy(Tracer tracer, TraceKeys traceKeys){

        return new TraceSleuthHystrixConcurrencyStrategy(tracer,traceKeys);
    }
    @Bean
    public WebTraceFilter getWebTraceFilter(){

        return new WebTraceFilter();
    }


    @Bean
    public RequestInterceptor getTraceFeignRequestInterceptor(){
        return new TraceFeignRequestInterceptor();
    }
    @Bean
    public TraceZipkinSpanReporter getTraceZipkinSpanReporter(){
        return new TraceZipkinSpanReporter();
    }
}
