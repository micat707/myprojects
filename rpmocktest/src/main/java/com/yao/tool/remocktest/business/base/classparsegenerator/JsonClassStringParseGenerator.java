package com.yao.tool.remocktest.business.base.classparsegenerator;

import com.yao.tool.remocktest.utils.json.JsonUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class JsonClassStringParseGenerator implements ClassFileParseApi{

	@Override
	public void writeClassToFile(Object classEntity, String fullFileName)
			throws IOException {
		String content = JsonUtil.parseToString(classEntity);
		File file = new File(fullFileName);
		FileUtils.writeStringToFile(file, content);
	}

	@Override
	public Object parseFileToClass(String fullFileName, Class<?> returnType) throws IOException {
		File file = new File(fullFileName);
		String content = FileUtils.readFileToString(file);
		Object result = JsonUtil.parseToObject(content, returnType);
		return result;
	}


}
