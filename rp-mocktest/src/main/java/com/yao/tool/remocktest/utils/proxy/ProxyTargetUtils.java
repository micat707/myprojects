package com.yao.tool.remocktest.utils.proxy;

import com.yao.tool.remocktest.utils.classes.ClassUtil;
import com.yao.tool.remocktest.utils.log.LoggerHelper;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

public class ProxyTargetUtils {

	/**
	 * 获取 目标对象
	 *
	 * @param proxy
	 *            代理对象
	 * @return
	 * @throws Exception
	 */
	public static Object getTarget(Object proxy) {
		Object result = proxy;
		try {

			if (!AopUtils.isAopProxy(proxy)) {
				return proxy;
			}

			if (AopUtils.isJdkDynamicProxy(proxy)) {
				return getJdkDynamicProxyTargetObject(proxy);
			} else { // cglib
				return getCglibProxyTargetObject(proxy);
			}
		} catch (Exception e) {
			LoggerHelper.logDebug(ProxyTargetUtils.class,
					"getTarget", LoggerHelper.getExceptionOrErrorMessage(e));
		}
		return result;
	}

	/**
	 * 获取cglib代理对象
	 * @param proxy
	 * @return cglib代理对象
	 * @throws Exception
	 */
	private static Object getCglibProxyTargetObject(Object proxy)
			throws Exception {
		if (ClassUtil.hasField("CGLIB$CALLBACK_0", proxy.getClass()
				.getDeclaredFields())) {
			Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
			h.setAccessible(true);
			Object dynamicAdvisedInterceptor = h.get(proxy);
			Object target = processProxy(dynamicAdvisedInterceptor);
			if (target != null) {
				return target;
			}

		} else {
			LoggerHelper.logInfo(ProxyTargetUtils.class,
					"getJdkDynamicProxyTargetObject",
					"不存在CGLIB$CALLBACK_0 field");
		}

		return proxy;
	}

	/**
	 * 获取jdk代理对象
	 * @param proxy
	 * @return jdk代理对象
	 * @throws Exception
	 */
	private static Object getJdkDynamicProxyTargetObject(Object proxy)
			throws Exception {

		if (ClassUtil.hasField("h", proxy.getClass().getSuperclass()
				.getDeclaredFields())) {
			Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
			h.setAccessible(true);
			AopProxy aopProxy = (AopProxy) h.get(proxy);
			Object target = processProxy(aopProxy);
			if (target != null) {
				return target;
			}

		} else {
			LoggerHelper.logInfo(ProxyTargetUtils.class,
					"getJdkDynamicProxyTargetObject", "不存在h field");
		}
		return proxy;
	}

	/**
	 * 处理代理对象
	 * @param proxyObject
	 * @return 代理对象
	 * @throws Exception
	 */
	private static Object processProxy( Object proxyObject) throws Exception {
		if (ClassUtil.hasField("advised", proxyObject.getClass()
                .getDeclaredFields())) {
            Field advised = proxyObject.getClass().getDeclaredField("advised");
            advised.setAccessible(true);
            Object target = ((AdvisedSupport) advised.get(proxyObject))
                    .getTargetSource().getTarget();
            return target;
        } else {
            LoggerHelper.logInfo(ProxyTargetUtils.class,
                    "getJdkDynamicProxyTargetObject", "不存在advised field");
        }
		return null;
	}

}
