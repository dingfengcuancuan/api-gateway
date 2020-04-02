package com.zhanghui.service.impl;

import com.zhanghui.api.annotation.ApiService;
import com.zhanghui.service.DemoService;
import org.springframework.stereotype.Service;

@ApiService
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String print(String json) {
        System.out.println("调用服务，参数内容："+json);
        return json;
    }
}
