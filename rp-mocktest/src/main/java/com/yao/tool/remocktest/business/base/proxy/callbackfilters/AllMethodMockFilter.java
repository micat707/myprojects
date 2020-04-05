package com.yao.tool.remocktest.business.base.proxy.callbackfilters;


import com.yao.tool.remocktest.utils.classes.ClassUtil;

import java.util.ArrayList;
import java.util.List;


public class AllMethodMockFilter extends MockFilter {
	
	/**   
	* NOT_MOCK_METHOD_LIST:   不需要mock的方法
	*    
	*/   
	private static final ThreadLocal<List<String>> NOT_MOCK_METHOD_LIST = new ThreadLocal<List<String>>() {
		public List<String> initialValue() {
			return new ArrayList<String>();
		}
	};

	/**
	 * 获取 不需要mock的方法列表
	 * @return
	 */
	public static List<String> getNOT_MOCK_METHOD_LIST(){
		return NOT_MOCK_METHOD_LIST.get();
	}

	public AllMethodMockFilter(Object target) {
		super(queryAccessibleMethods(target));
	}


	/**
	 * 查询可访问的方法
	 * @param target
	 * @return
	 */
	private static List<String> queryAccessibleMethods(Object target) {
		List<String> methodNameList;
		methodNameList = ClassUtil.queryAccessibleMethodList(target.getClass());
		List<String> notMockList = NOT_MOCK_METHOD_LIST.get();
		if(notMockList != null && !notMockList.isEmpty()){
			methodNameList.removeAll(notMockList);
		}

		return methodNameList;
	}
}
