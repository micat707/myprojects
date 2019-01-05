package com.yao.springcloud.fallback;

import com.yao.springcloud.ServiceBInterface;

/**
 * 设置当调用失败时的处理方式
 */
public class ServiceBClientFallback implements ServiceBInterface {


	@Override
	public String saybhello(String name) {
		return "call B service failed!";
	}
}
