package com.yao.springcloud.hystrix;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.sleuth.instrument.hystrix.SleuthHystrixConcurrencyStrategy;

/**
 * ClassName：TraceSleuthHystrixConcurrencyStrategyPostProcessor
 * Class Description：  ToDo
 *
 * @author：micat707
 * @date：2018/1/3 modifier：micat707
 * modify time：2018/1/3
 * Modify Remark：
 */
public class TraceSleuthHystrixConcurrencyStrategyPostProcessor implements BeanPostProcessor {
    private TraceSleuthHystrixConcurrencyStrategy traceSleuthHystrixConcurrencyStrategy;

    public TraceSleuthHystrixConcurrencyStrategyPostProcessor(TraceSleuthHystrixConcurrencyStrategy
                                                                        traceSleuthHystrixConcurrencyStrategy){
        this.traceSleuthHystrixConcurrencyStrategy = traceSleuthHystrixConcurrencyStrategy;
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof SleuthHystrixConcurrencyStrategy) {
            return traceSleuthHystrixConcurrencyStrategy;
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
