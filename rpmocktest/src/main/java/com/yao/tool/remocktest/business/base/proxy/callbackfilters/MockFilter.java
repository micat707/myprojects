package com.yao.tool.remocktest.business.base.proxy.callbackfilters;


import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MockFilter implements CallbackFilter {
	/**
	 * 方法拦截列表
	 */
	private List<String> inerceptMethodList;

	public final static String ALL_METHOD = "*";

	/**
	 * @param inerceptMethodList
	 */
	public MockFilter(List<String> inerceptMethodList) {
		List<String> result = new ArrayList<String>();
		if(null != inerceptMethodList){
			result.addAll(inerceptMethodList);
		}
		this.inerceptMethodList = result;
	}

	@Override
	public int accept(Method arg0) {
		if ((getInerceptMethod().contains(arg0.getName()))
		        || (getInerceptMethod().contains(ALL_METHOD))) {
			return 0;
		}
		return 1;
	}

	/**
	 * 获取方法拦截列表
	 * @return
	 */
	public List<String> getInerceptMethod() {
		List<String> result = new ArrayList<String>();
		if(null != inerceptMethodList){
			result.addAll(inerceptMethodList);
		}
		return result;
	}

	/**
	 * 获取方法 过滤器
	 * @param inerceptMethodList
	 * @return
	 */
	public static CallbackFilter newInstance(List<String> inerceptMethodList) {
		return new MockFilter(inerceptMethodList);
	}
}
