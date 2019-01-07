package com.yao.springcloud.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringUtils;

public class JsonUtil {
	/**   
	* GOSON:TODO（描述变量用途）   
	*    goson 对json的处理帮助类
	*/   
	private static Gson GOSON = new GsonBuilder().serializeNulls().create();

	/**   
	* parseToString:  将对象转化为json字符串
	* @param entity
	* @return String
	* @author：micat707   @date：2015年9月15日 上午11:03:32   
	*/
	public static <T> String parseToString(T entity) {
		String result = "";
		if (entity != null) {
			result = GOSON.toJson(entity);
		}
		return result;
	}
	
	/**   
	* parseToObject:  将json字符串转换为对象 
	* @param jsonStr
	* @param entityClass
	* @return T
	* @author：micat707   @date：2015年9月15日 上午11:09:40   
	*/
	public static <T> T parseToObject(String jsonStr, Class<T> entityClass){
		T result = null;
		if(StringUtils.isNotEmpty(jsonStr) && entityClass != null){
			result = GOSON.fromJson(jsonStr, entityClass);
			
		}
		return result;
	}
	
	
}
