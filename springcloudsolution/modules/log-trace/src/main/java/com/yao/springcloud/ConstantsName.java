package com.yao.springcloud;


import org.springframework.cloud.sleuth.instrument.web.TraceFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

public class ConstantsName {
    public static final String CLIENT_IP = "CLIENT_IP";
    /**
     * CLOSECONTROLLER:  trace filter order顺序  确保在sluth trace filter（Ordered.HIGHEST_PRECEDENCE  + 5）前
     */
    public final static int TRACE_FILTERORDER = Ordered.HIGHEST_PRECEDENCE  + 4;
}
