package com.zhanghui.controller;


import cn.com.servyou.common.invocation.restful.client.RestfulClient;
import com.zhanghui.api.request.ApiRequest;
import com.zhanghui.api.response.ApiResponse;
import com.zhanghui.api.util.ApplicationContextUtil;
import com.zhanghui.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private RestfulClient client;

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


    @RequestMapping("/print")
    public String print(){
        return demoService.print("i am  demo.print!");
    }

    /**
     * 验证 本地方法调用  和 反射调用 和 http方式调用的效率
     * 结果：
     *  local:796ms
     *  reflect:766ms
     *  HTTP:5166ms
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/xntest")
    public String  xntest() throws Exception{

        int count=5000;

        String result="";

        //本地
        long startTime1 = System.currentTimeMillis();

        for(int i=0;i<count;i++){
            String obj=demoService.print("i am  demo.print!");
            //System.out.println("本地："+obj);
        }
        long timecost1 = System.currentTimeMillis() - startTime1;

        System.out.println("本地调用耗时:"+timecost1+"ms");
        result+="local:"+timecost1+"ms <br/>";


        //反射
        long startTime2 = System.currentTimeMillis();

        for(int i=0;i<count;i++){
            Object bean = ApplicationContextUtil.getBean("demoServiceImpl");
            Method method=bean.getClass().getMethod("print",String.class);
            Object obj = method.invoke(bean, "i am  demo.print!");
            //System.out.println("反射："+obj);
        }
        long timecost2 = System.currentTimeMillis() - startTime2;

        System.out.println("反射调用耗时:"+timecost2+"ms");
        result+="reflect:"+timecost2+"ms <br/>";

        //HTTP
        long startTime3 = System.currentTimeMillis();

        for(int i=0;i<count;i++){
            Object obj=client.getForObject("http://localhost:8080/demo/demo/print",String.class);
            //System.out.println("HTTP："+obj);
        }
        long timecost3 = System.currentTimeMillis() - startTime3;

        System.out.println("HTTP调用耗时:"+timecost3+"ms");
        result+="HTTP:"+timecost3+"ms <br/>";

        return result;
    }
}
