package com.yao.test.mockimpl.services.needmockpackage;

import com.yao.test.mockimpl.utils.HttpUtils;
import org.springframework.stereotype.Component;

@Component
public class BaiduService {
    public String searchKey(String keyWord){
        return  HttpUtils.sendHttpGet(String.format("http://www.baidu.com/s?wd=%s", keyWord));
    }
}
