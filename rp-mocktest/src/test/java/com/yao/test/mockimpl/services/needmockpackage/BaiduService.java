package com.yao.test.mockimpl.services.needmockpackage;

import com.yao.test.mockimpl.services.BaiduOutProxyService;
import com.yao.test.mockimpl.services.SearchKeyApi;
import com.yao.test.mockimpl.utils.HttpUtils;
import org.springframework.stereotype.Component;

@Component
public class BaiduService implements SearchKeyApi {
    public String searchKey(){
        String keyWord = "who";
        return  HttpUtils.sendHttpGet(String.format("http://www.baidu.com/s?wd=%s", keyWord));
    }
}
