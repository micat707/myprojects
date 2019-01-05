package com.yao.tool.remocktest.utils.log;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggerHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(LoggerHelper.class);

	/**
	 * 写info信息
	 * @param loggerNameClass
	 * @param functionName Log 方法所在的方法名
	 * @param info 需要写的信息
	 */
	public static void logInfo(Class<?> loggerNameClass, String functionName,
			String info) {
		StringBuffer logMessage = new StringBuffer();
		Logger logger = null;
		try {
			logger = LoggerFactory.getLogger(loggerNameClass);
			logMessage.append("函数名：" + functionName + "\r\n");
			logMessage.append("信息：" + info + "\r\n");
			logger.info(logMessage.toString());
		} catch (Exception ex) {
			LOGGER.error("logInfo",ex);
		}
	}

	/**
	 * 写debug信息
	 * @param loggerNameClass  Log 方法所在类名
	 * @param functionName Log 方法所在的方法名
	 * @param info  需要写的信息
	 */
	public static void logDebug(Class<?> loggerNameClass, String functionName,
			String info) {
		StringBuffer logMessage = new StringBuffer();
		Logger logger = null;
		try {
			logger = LoggerFactory.getLogger(loggerNameClass);
			logMessage.append("函数名：" + functionName + "\r\n");
			logMessage.append("信息：" + info + "\r\n");
			logger.debug(logMessage.toString());
		} catch (Exception ex) {
			LOGGER.error("logDebug",ex);
		}
	}

	/**
	 * 记录异常
	 * @param loggerNameClass Log 方法所在类名
	 * @param functionName Log 方法所在的方法名
	 * @param exceptionOrError 异常
	 */
	public static void logExceptionOrError(Class<?> loggerNameClass,
			String functionName, Object exceptionOrError) {
		StringBuffer logMessage = new StringBuffer();
		Logger logger = null;
		String info = null;
		try {
			info = getExceptionOrErrorMessage(exceptionOrError);
			if (shouldWriteInfo(info)) {
				logger = LoggerFactory.getLogger(loggerNameClass);
				logMessage.append("函数名：" + functionName + "\r\n");
				logMessage.append("异常：" + info + "\r\n");
				writeErrorInfo(logger, logMessage.toString());
			}
		} catch (Exception ex) {
			LOGGER.error("logExceptionOrError",ex);
		}
	}

	/**
	 * 记录异常
	 * @param loggerNameClass Log 方法所在类名
	 * @param functionName  Log 方法所在的方法名
	 * @param exceptionOrError  异常
	 * @param message 需要添加的对异常的附加信息
	 */
	public static void logExceptionOrError(Class<?> loggerNameClass,
			String functionName, Object exceptionOrError, String message) {
		StringBuffer logMessage = new StringBuffer();
		Logger logger = null;
		String info = null;
		try {
			info = getExceptionOrErrorMessage(exceptionOrError);
			if ((shouldWriteInfo(info)) || (StringUtils.isNotEmpty(message))) {
				logger = LoggerFactory.getLogger(loggerNameClass);
				logMessage.append("函数名：" + functionName + "\r\n");
				logMessage.append("异常：" + info);
				logMessage.append("异常附加信息：" + message);
				writeErrorInfo(logger, logMessage.toString());
			}
		} catch (Exception ex) {
			LOGGER.error("logExceptionOrError",ex);
		}
	}

	/**
	 * 获取异常堆信息
	 * @param ex 异常
	 * @return  异常堆信息
	 */
	private static String getExceptionMessage(Exception ex) {
		String result = "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			if (ex != null) {
				ex.printStackTrace(pw);
				result = sw.toString();
				pw.close();
				sw.close();
			}
		} catch (Exception ex1) {
			LOGGER.error("getExceptionMessage",ex);
		}
		return result;
	}

	/**
	 * 获取错误堆信息
	 * @param er 错误
	 * @return 错误堆信息
	 */
	private static String getErrorMessage(Error er) {
		String result = "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			if (er != null) {
				er.printStackTrace(pw);
				result = sw.toString();
				pw.close();
				sw.close();
			}
		} catch (Exception ex1) {
			if (pw != null) {
				pw.close();
			}
			LOGGER.error("getErrorMessage",ex1);
		}
		return result;
	}

	/**
	 * 获取异常 或错误的 消息
	 * @param exOrError 异常或错误
	 * @return 异常 或错误的 消息
	 */
	public static String getExceptionOrErrorMessage(Object exOrError) {
		String result = "";
		Exception ex = null;
		Error error = null;
		if (exOrError != null) {
			if (exOrError instanceof Exception) {
				ex = (Exception) exOrError;
				result = getExceptionMessage(ex);
			} else if (exOrError instanceof Error) {
				error = (Error) exOrError;
				result = getErrorMessage(error);
			}
		}
		return result;
	}

	/**
	 * 记录错误信息
	 * @param logger 写日志的logger
	 * @param message  需要记录的错误信息
	 */
	private static void writeErrorInfo(Logger logger, String message) {
		if ((message == null) || (message.length() <= 0))
			return;
		logger.error(message);
	}

	/**
	 * 是否需要记录日志
	 * @param message 用来判断的message
	 * @return 判断结果
	 */
	private static boolean shouldWriteInfo(String message) {
		boolean result = false;
		if ((message != null) && (message.length() > 0)) {
			result = true;
		}
		return result;
	}
}