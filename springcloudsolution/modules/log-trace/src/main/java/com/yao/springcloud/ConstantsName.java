package com.yao.springcloud;


import org.springframework.core.Ordered;

public class ConstantsName {
    public static final String CLIENT_IP = "CLIENT_IP";
    /**
     * CLOSECONTROLLER:  trace filter order顺序
     */
    public final static int TRACE_FILTERORDER = Ordered.HIGHEST_PRECEDENCE + 6;
}
