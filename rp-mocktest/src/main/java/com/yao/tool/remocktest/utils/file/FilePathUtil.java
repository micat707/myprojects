package com.yao.tool.remocktest.utils.file;


import com.yao.tool.remocktest.utils.log.LoggerHelper;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class FilePathUtil {
    private FilePathUtil(){
        
    }

    /**
     * 获取文件的全路径名
     * @param fileName 文件名
     * @return
     */
    public static String getRootFileName(String fileName)
    {
      String result = "";
      ClassLoader load = Thread.currentThread().getContextClassLoader();
      if(load != null){
          String rootPath = load.getResource("").getFile();
          try {
			URI uri = new URI(rootPath.toString());
			rootPath = uri.getPath();
		} catch (URISyntaxException e) {
			LoggerHelper.logExceptionOrError(FilePathUtil.class, "getRootFileName", e);
		}
          String extFileName = fileName;
          rootPath = FilenameUtils.getFullPathNoEndSeparator(rootPath);
  
          rootPath = FilenameUtils.concat(rootPath, "mock_resources");
          result = FilenameUtils.concat(rootPath, extFileName);
      }
      return result;
    }

    /**
     * 获取record的文件全名
     * @param fileName  文件名
     * @return record的文件全名
     */
    public static String getRecordFilePath(String fileName){
        String result = "";
        result = getRootFileName(fileName);
        String resoureespaht = "src" + File.separator + "test"   + File.separator +"resources";
        
        result = result.replace("target" + File.separator + "test-classes", resoureespaht);
        result = result.replace("target" + File.separator + "classes", resoureespaht);
        result = result.replace("src" + File.separator + "main" + File.separator  + 
        		"webapp" + File.separator + "WEB-INF"+ File.separator 
        		+ "classes", resoureespaht);
        return result;
    }


    
  
}
