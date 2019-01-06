package com.yao.tool.remocktest.utils.classes;


import com.yao.tool.remocktest.api.exception.RPMockException;
import com.yao.tool.remocktest.utils.log.LoggerHelper;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClassUtil {

	/**
	 * findFieldClassStr: 获取field类全名
	 * 
	 * @param field
	 * @return String
	 * @author：micat707
	 * @date：2016年3月21日 下午1:16:49
	 */
	public static String findFieldClassStr(Field field) {
		String result = null;
		result = field.getType().getCanonicalName();
		return result;
	}

	/**
	 * 构造get 方法
	 * @param fieldName
	 * @return
	 */
	public static String constructGetName(String fieldName) {
		String result = "";
		return result;
	}

	/**
	 * 判断 是否是final方法
	 * @param mod
	 * @return
	 */
	public static boolean isFinal(int mod) {
		return (mod & Modifier.FINAL) != 0;
	}

	/**
	 * 
	 * arrFields: 获取所有的属性
	 * 
	 * @param clazz
	 * @return Field[]
	 * @author Administrator
	 * @date 2016年1月14日 下午6:07:25
	 */
	public static Field[] arrFields(Class<?> clazz) {
		if (null == clazz) {
			return null;
		}
		List<Field> list = new ArrayList<Field>();
		Class<?> superClass = clazz;
		while (null != superClass && !superClass.isPrimitive()
				&& !Object.class.equals(superClass)) {
			for (Field f : superClass.getDeclaredFields()) {
				boolean has = false;
				for (Field hasField : list) {
					if (f.getName().equals(hasField.getName())
							&& f.getType().equals(hasField.getType())) {
						has = true;
						break;
					}
				}
				if (!has) {
					list.add(f);
				}
			}
			superClass = superClass.getSuperclass();
		}
		Field[] re = new Field[list.size()];
		return list.toArray(re);
	}

	/**
	 * findGetName: 根据fieldName 找get 方法名
	 * 
	 * @param fieldName
	 * @return String
	 * @author：micat707
	 * @date：2016年3月21日 下午1:40:13
	 */
	public static String findGetName(String fieldName) {
		String result = "";
		if (StringUtils.isNotEmpty(fieldName)) {
			result = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
		}
		return result;
	}

	/**
	 * findSetName: 根据fieldName 找set 方法名
	 * 
	 * @param fieldName
	 * @return String
	 * @author：micat707
	 * @date：2016年3月21日 下午1:40:37
	 */
	public static String findSetName(String fieldName) {
		String result = "";
		if (StringUtils.isNotEmpty(fieldName)) {
			result = "set" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
		}
		return result;
	}

	/**
	 * 获取域 List泛型的类型
	 * 
	 * @param field
	 * @return
	 * @throws RPMockException
	 */
	public static Class generateFieldListType(Field field) {
		Class result = Object.class;
		Type fc;
		if (field.getType().equals(List.class)
				|| field.getType().equals(ArrayList.class)) {// 如果为List
			fc = field.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型
			if (fc instanceof ParameterizedType) // 如果是泛型参数的类型
			{
				ParameterizedType pt = (ParameterizedType) fc;

				try {
					result = (Class) pt.getActualTypeArguments()[0]; // 得到泛型里的class类型对
				} catch (Exception e) {

					LoggerHelper.logExceptionOrError(ClassUtil.class,
							"generateFieldListType", e);
					throw new RPMockException(e.getMessage());
				}
			}
		}
		return result;
	}

	/**
	 * 获取域 List泛型的类型
	 * 
	 * @param field
	 * @return
	 */
	public static Class generateObjectListType(Class classType) {
		Class result = null;
		Type fc;
		fc = classType.getGenericSuperclass(); // 关键的地方，如果是List类型，得到其Generic的类型

		if (!classType.isPrimitive()) {

			if (fc instanceof ParameterizedType) // 如果是泛型参数的类型
			{
				ParameterizedType pt = (ParameterizedType) fc;

				result = (Class) pt.getActualTypeArguments()[0]; // 得到泛型里的class类型对
			}
		} else {
			LoggerHelper.logInfo(ClassUtil.class, "generateObjectListType",
					"基本类型：" + classType.getCanonicalName());
		}
		return result;
	}

	/**
	 * 获取数组对象类型
	 * 
	 * @param field
	 * @return
	 * @throws RPMockException
	 */
	public static Class generateArrayType(Class classType) {
		Class result;
		String orgClassName;

		orgClassName = classType.getCanonicalName();
		if (orgClassName.indexOf('[') != -1) {// 如果是数组
			result = classType.getComponentType();
		} else {
			throw new RPMockException(orgClassName + "该类型不为数组类型！");
		}

		return result;
	}

	/**
	 * 判断是否是复杂类型
	 * 
	 * @param objectClass
	 * @return
	 */
	public static boolean isComplexType(Class objectClass) {

		boolean result = false;
		if (null != objectClass && !objectClass.isPrimitive()
				&& !Object.class.equals(objectClass)
				&& !String.class.equals(objectClass)
				&& !Long.class.equals(objectClass)
				&& !Double.class.equals(objectClass)) {
			result = true;
		}
		return result;
	}

	public static List<Method> queryAccessibleMethods(Class inputclass) {
		List<Method> methodList = new ArrayList<Method>();
		if (inputclass != null) {
			Method[] tempMethodArr = inputclass.getDeclaredMethods();
			if (tempMethodArr != null) {
				int modifier = 0;
				for (Method method : tempMethodArr) {
					modifier = method.getModifiers();
					if (modifier != 2 && modifier != 4
							&& !Modifier.isStatic(modifier)) {// 2 is private
						methodList.add(method);
					}
				}
			}
			if (isComplexType(inputclass.getSuperclass())) {// 递归调用
				methodList.addAll(queryAccessibleMethods(inputclass
						.getSuperclass()));
			}

		}
		return methodList;
	}

	public static List<String> queryAccessibleMethodList(Class inputclass) {
		List<String> methodList = new ArrayList<String>();
		if (inputclass != null) {
			Method[] tempMethodArr = inputclass.getDeclaredMethods();
			if (tempMethodArr != null) {
				int modifier = 0;
				for (Method method : tempMethodArr) {
					modifier = method.getModifiers();
					if (modifier != 2 && modifier != 4
							&& !Modifier.isStatic(modifier)) {// 2 is private
						methodList.add(method.getName());
					}
				}
			}
			if (isComplexType(inputclass.getSuperclass())) {// 递归调用
				methodList.addAll(queryAccessibleMethodList(inputclass
						.getSuperclass()));
			}

		}
		return methodList;
	}

	/**
	 * 获取一个类的所有域
	 * 
	 * @param classObject
	 * @return
	 */
	public static List<Field> getAllFieldArray(Class classObject) {
		List<Field> result;
		Field[] declareFields;
		Class supperClass;
		List<Field> fieldList;

		declareFields = classObject.getDeclaredFields();
		supperClass = classObject.getSuperclass();
		result = new ArrayList<Field>();
		result.addAll(Arrays.asList(declareFields));
		if (supperClass != null && (!isJavaClass(supperClass))) {
			fieldList = getAllFieldArray(supperClass);
			if (fieldList != null && result != null) {
				result.addAll(fieldList);// 收集父类定义 的字段
			}
		}

		return result;
	}

	/**
	 * 判断 是否是java 类
	 * @param clz 需要判断 的类
	 * @return 判断 结果
	 */
	public static boolean isJavaClass(Class<?> clz) {
		return clz != null && clz.getClassLoader() == null;
	}

	/**
	 * 判断 Field数组中 是否存在某个 字段
	 * @param fieldName 需要判断 的字段名
	 * @param fields  Field数组
	 * @return
	 */
	public static boolean hasField(String fieldName, Field[] fields) {
		if (StringUtils.isNotEmpty(fieldName) && fields != null
				&& fields.length > 0) {
			for (Field currentField : fields) {
				if (fieldName.equals(currentField.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
