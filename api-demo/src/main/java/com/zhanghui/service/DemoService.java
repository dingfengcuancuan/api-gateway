package com.zhanghui.service;

import com.zhanghui.api.annotation.Api;
import com.zhanghui.api.annotation.ApiService;
import org.springframework.stereotype.Service;

public interface DemoService {

    @Api(name = "demo.print",desc ="演示打印" ,version = "1.0")
    public String print(String json);
}
