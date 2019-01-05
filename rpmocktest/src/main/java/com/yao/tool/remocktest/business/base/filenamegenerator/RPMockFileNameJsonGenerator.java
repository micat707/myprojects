package com.yao.tool.remocktest.business.base.filenamegenerator;


import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;

public class RPMockFileNameJsonGenerator extends
		MockFileNameGeneratorAbstract {
	/**
	 * 构建路径名
	 * @param caseName
	 * @param className
	 * @param m
	 * @param callTime
	 * @return
	 */
	private static String buildFullPathName(String caseName,
			String className, Method m, int callTime, String fieldName) {
		String result = "";
		StringBuilder sb = new StringBuilder();
		String newMethodClassName = className;
		if(StringUtils.isNotEmpty(className)){
			String[] names = className.split("\\.");
			newMethodClassName = names[names.length -1];
		}
		
		result = buildFileName(newMethodClassName, m, fieldName);
		sb.append(result);
		sb.append("#");
		sb.append(callTime);
		sb.append(".json");
		result = sb.toString();
		return result;
	}

	/**
	 * 构建方法唯一key
	 * @param className 类名
	 * @param m 方法对象
	 * @return 唯一key
	 */
	private static String buildFileName(String className,
			Method m, String fieldName) {
		String result = "";
		StringBuilder sb = new StringBuilder();
		sb.append(className);
		sb.append("#");
		sb.append(fieldName);
		sb.append("#");
		sb.append(m.getName());

		result = sb.toString();
		if (result.length() > 200) {//文件名过长，会导致报错
			result = result.substring(0, 200);
		}
		return result;
	}

	@Override
	protected String createCurrentFileName(Method m, Object[] args,
			String className,String caseName, int callTime, String filedName) {
		String result = buildFullPathName(caseName, className, m, callTime,filedName);
		return result;
	}

}
