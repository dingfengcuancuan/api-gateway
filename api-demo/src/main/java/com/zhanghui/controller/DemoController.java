package com.zhanghui.controller;


import com.zhanghui.api.request.ApiRequest;
import com.zhanghui.api.response.ApiResponse;
import com.zhanghui.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private DemoService demoService;


    @RequestMapping("/test")
    public ApiResponse test(){
        ApiResponse response=new ApiResponse();
        response.setCode("1000");
        response.setMsg("success");
        response.setSign("eregegdfdx");

        ApiRequest request=new ApiRequest();
        request.setAppId("test");
        response.setResult(request);

        return response;
    }
}
