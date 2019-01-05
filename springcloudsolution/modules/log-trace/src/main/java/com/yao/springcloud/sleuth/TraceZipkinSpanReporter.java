package com.yao.springcloud.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import zipkin.Span;


public class TraceZipkinSpanReporter implements ZipkinSpanReporter {
    private Logger LOGGER = LoggerFactory.getLogger(TraceZipkinSpanReporter
            .class);


    @Override
    public void report(Span span) {
        if(span != null){
            LOGGER.info(span.toString());
        }

    }
}
