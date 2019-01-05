package com.yao.springcloud.hystrix;


import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import com.yao.springcloud.ConstantsName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.MDC;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.TraceKeys;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.hystrix.SleuthHystrixConcurrencyStrategy;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Callable;


public class TraceSleuthHystrixConcurrencyStrategy extends SleuthHystrixConcurrencyStrategy {
    private static final Log log = LogFactory.getLog(TraceSleuthHystrixConcurrencyStrategy.class);
    private static final String HYSTRIX_COMPONENT = "hystrix";
    private final Tracer tracer;
    private final TraceKeys traceKeys;
    private HystrixConcurrencyStrategy delegate;



    public HystrixConcurrencyStrategy getDelegate() {
        return delegate;
    }

    public void setDelegate(HystrixConcurrencyStrategy delegate) {
        this.delegate = delegate;
    }

    public Tracer getTracer() {
        return tracer;
    }

    public TraceKeys getTraceKeys() {
        return traceKeys;
    }

    public TraceSleuthHystrixConcurrencyStrategy(Tracer tracer, TraceKeys traceKeys) {
        super(tracer, traceKeys);
        this.tracer = tracer;
        this.traceKeys = traceKeys;
    }


    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        if (callable instanceof TraceHystrixTraceCallable) {
            return callable;
        }
        Callable<T> wrappedCallable = this.delegate != null ? this.delegate.wrapCallable(callable) : callable;
        if (wrappedCallable instanceof TraceHystrixTraceCallable) {
            return wrappedCallable;
        }
        return new TraceHystrixTraceCallable<>(this.tracer, this.traceKeys, wrappedCallable);
    }

    private void logCurrentStateOfHysrixPlugins(HystrixEventNotifier eventNotifier,
            HystrixMetricsPublisher metricsPublisher, HystrixPropertiesStrategy propertiesStrategy) {
        if (log.isDebugEnabled()) {
            log.debug("Current Hystrix plugins configuration is [" + "concurrencyStrategy [" + this.delegate
                    + "]," + "eventNotifier [" + eventNotifier + "]," + "metricPublisher [" + metricsPublisher
                    + "]," + "propertiesStrategy [" + propertiesStrategy + "]," + "]");
            log.debug("Registering Sleuth Hystrix Concurrency Strategy.");
        }
    }

    // Visible for testing
    static class TraceHystrixTraceCallable<S> implements Callable<S> {

        private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

        private Tracer tracer;
        private TraceKeys traceKeys;
        private Callable<S> callable;
        private String userIp;
        private String accessPath;
        private String userName;
        private Span parent;
        private String domain;
        private String clientIp;

        public Tracer getTracer() {
            return tracer;
        }

        public void setTracer(Tracer tracer) {
            this.tracer = tracer;
        }

        public TraceKeys getTraceKeys() {
            return traceKeys;
        }

        public void setTraceKeys(TraceKeys traceKeys) {
            this.traceKeys = traceKeys;
        }

        public Callable<S> getCallable() {
            return callable;
        }

        public void setCallable(Callable<S> callable) {
            this.callable = callable;
        }

        public String getUserIp() {
            return userIp;
        }

        public void setUserIp(String userIp) {
            this.userIp = userIp;
        }

        public String getAccessPath() {
            return accessPath;
        }

        public void setAccessPath(String accessPath) {
            this.accessPath = accessPath;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Span getParent() {
            return parent;
        }

        public void setParent(Span parent) {
            this.parent = parent;
        }

        public TraceHystrixTraceCallable(Tracer tracer, TraceKeys traceKeys, Callable<S> callable) {
            this.tracer = tracer;
            this.traceKeys = traceKeys;
            this.callable = callable;
            this.parent = tracer.getCurrentSpan();
            this.clientIp = MDC.get(ConstantsName.CLIENT_IP);
        }

        @Override
        public S call() throws Exception  {
            Span span = this.parent;
            boolean created = false;
            MDC.put(ConstantsName.CLIENT_IP,this.clientIp);
            if (span != null) {
                span = this.tracer.continueSpan(span);
                if (log.isDebugEnabled()) {
                    log.debug("Continuing span " + span);
                }
            } else {
                span = this.tracer.createSpan(HYSTRIX_COMPONENT);
                created = true;
                if (log.isDebugEnabled()) {
                    log.debug("Creating new span " + span);
                }
            }
            if (!span.tags().containsKey(Span.SPAN_LOCAL_COMPONENT_TAG_NAME)) {
                this.tracer.addTag(Span.SPAN_LOCAL_COMPONENT_TAG_NAME, HYSTRIX_COMPONENT);
            }
            String asyncKey = this.traceKeys.getAsync().getPrefix()
                    + this.traceKeys.getAsync().getThreadNameKey();
            if (!span.tags().containsKey(asyncKey)) {
                this.tracer.addTag(asyncKey, Thread.currentThread().getName());
            }
            try {
                return this.callable.call();
            } finally {
                MDC.remove(ConstantsName.CLIENT_IP);
                if (created) {
                    if (log.isDebugEnabled()) {
                        log.debug("Closing span since it was created" + span);
                    }
                    this.tracer.close(span);
                } else if (this.tracer.isTracing()) {
                    if (log.isDebugEnabled()) {
                        log.debug("Detaching span since it was continued " + span);
                    }
                    this.tracer.detach(span);
                }
            }
        }

    }

}
