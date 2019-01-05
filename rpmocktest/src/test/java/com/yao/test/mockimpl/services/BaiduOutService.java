package com.yao.test.mockimpl.services;

import com.yao.test.mockimpl.services.needmockpackage.BaiduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaiduOutService implements SearchKeyApi {
    @Autowired
    private BaiduService baiduService;
    public String searchKey(){
        return  baiduService.searchKey();
    }
}
