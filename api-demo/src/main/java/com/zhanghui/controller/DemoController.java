package com.zhanghui.controller;


import cn.com.servyou.common.invocation.restful.client.RestfulClient;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.zhanghui.api.request.ApiRequest;
import com.zhanghui.api.response.ApiResponse;
import com.zhanghui.api.router.ApiRouter;
import com.zhanghui.api.util.ApiUtil;
import com.zhanghui.api.util.ApplicationContextUtil;
import com.zhanghui.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;

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

    private String PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCcttLg0oHgrqVLACu45uSyomlyuzu3zDnVjfwj2LH2vtYIEyICNHFa7wM2eva3nJr9deiKgerIwfegSSKqxSHcVfWkb/g3/3+FG0UMmqmQKjhn8PvrAR6Fj7L6TywYQ7507ybxs1uZ+lwgYPC4+XepUEV7IlDA+C65Z3fb/G3MtxiTYL8R5zbn74SUehsa8uRFMceVd5n6/whgIg93TnxtCyycUzDuef4uCvaRAr/aA9DyBezyaLXvXaL92CijxvPD2aHOQWdZcTFGrOChcfkN8gxaeVFHiXW7apdc9T6EgoW5rbvvqodESZ6o+QqIcDbOgxz0tXJ1UNMai8mNKfTTAgMBAAECggEAaWS+/7oy5JMv0Pflb63Awq5dcm6fI+XyQ0ABAW6biREgyj9r0MXKMlip9XrO7/8fcTRZ1sS2zJs+WQq2iNiZBCC/Wf5/ldF7xl3nmylsverXEnhMQ+j1yOcyfArA4fS1YtjvfP+drDlmudPYMN59bl3wzHXwj0aZzdDbGBR1F4Y+CzgRObq5092acJz/foUO5SAg/+KLhmQQ28AvBhXPTgV467ER34pDHexCgAnsLsH4KVCPxsbW0eh7vaIgUjWQ/sTAfRzFW5CLJRYO1xH6mDS42dImiUJBiWiz2fK4FRrSM05QXCHkKfXb1DpMnvrNyHyCDZfshRp7UsZoxtVeaQKBgQDvWrX7JNb3Km5JncdKRq+I8lqx+d750Ffj0Ln2wTR0lahonJyF+YL4yuP5R8+sCeFRjcPWMUkZ2oSxxT6gaHRl0Z2fJXqwuCaO9NKq9C03olPmiFXM/H4knXsr1urfcRmE5sg3k0kcmtDnxoyDBBb3w8SRb8p0jqRl/NspDq9IJwKBgQCnnNj/JgCZJkWnK7JPTqnjTlavkV1Zicz89dvQ9Eq7Ff0e/BJiEi+DrndSP2v2YOZEJr0LOpIxJwaNCgea67t055/IlsBqHBu0cBtGQ7i2ykd/3Q1v+q0dEKf3C92lvwcsLRQo01gjnDMRiiSkIf0cYdxs66wgzAxh4xd9BvwNdQKBgAEVrydxNkwudAt/XIzYnykGuCSAVmNZb1yH8J/Oplc06mt28jqlM9O+z6OskKNd9BhzhQSuen9Ufy9zDmKZtpVTitxSxiiQ3RPeximiK6ZJ5QlxarogFs5BrHI5ah0THSN+DEA8OaOYjAPQ4Ygid5wt1fE2yXsXvmT77V5VQ7QXAoGALDRNdl0LY1iYnhIEIK5aV7xdWEg6GlchXMVqh50l6FlQPE+2eW7aYRwuE97uFjhQAkFFMiTsUVI9hAzVHKJ2+cnsdfZsII/xLpEyYEGUAYEvgiVGWfX+md++rITQm9nZhmkNHAdBA4M6ZLHOIAtmuYmFnKQ67RtjWJ2PoEWrS9ECgYBW6QtwiRxoz/Xes9jHRmShVcDfwq210Qubmd81uUtDy/lVOY99qCb4rNsTu3RVrtMubD9syvivDroE6NtFrDBNHAtRaGB77Ivs+WdMdhDzNwGxVfgt6h/qJoY05+NmVPe3ExtC2fs8EWnZkXPbs2qurFxdvhBjxHIQHQwV54GaZQ==";

    @RequestMapping("/routerReflect")
    public ApiResponse  routerReflect(){
        String ret="";

        ApiRequest apiRequest=new ApiRequest();
        apiRequest.setAppId("app1");
        apiRequest.setMethod("demo.print");
        apiRequest.setVersion("1.0");

        apiRequest.setBizContent("{\"name\":\"zhanghui.reflect\"}");

        //模拟tocken
        String token= ApiUtil.createToken(apiRequest.getAppId());

        //正常token
        apiRequest.setAppAuthToken(token);

        //非法token
        //apiRequest.setAppAuthToken("test_token");

        String apiRequestJson= JSON.toJSONString(apiRequest);

        System.out.println("原始："+apiRequestJson);



        Map map=JSON.parseObject(apiRequestJson);

        String content = AlipaySignature.getSignContent(map);
        String sign = null;
        try {
            sign = AlipaySignature.rsaSign(content, PRIVATE_KEY, "UTF-8","RSA2");
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        apiRequest.setSign(sign);

        System.out.println("签名后："+ JSON.toJSONString(apiRequest));

        ApiResponse apiResponse=ApiRouter.invoke(apiRequest);

        return apiResponse;
    }

    @RequestMapping("/routerHttp")
    public ApiResponse  routerHttp(){
        String ret="";

        ApiRequest apiRequest=new ApiRequest();
        apiRequest.setAppId("app1");
        apiRequest.setMethod("demo.print.http");
        apiRequest.setVersion("1.0");

        apiRequest.setBizContent("{\"name\":\"zhanghui\"}");

        //模拟tocken
        String token= ApiUtil.createToken(apiRequest.getAppId());

        //正常token
        apiRequest.setAppAuthToken(token);

        //非法token
        //apiRequest.setAppAuthToken("test_token");

        String apiRequestJson= JSON.toJSONString(apiRequest);

        System.out.println("原始："+apiRequestJson);



        Map map=JSON.parseObject(apiRequestJson);

        String content = AlipaySignature.getSignContent(map);
        String sign = null;
        try {
            sign = AlipaySignature.rsaSign(content, PRIVATE_KEY, "UTF-8","RSA2");
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        apiRequest.setSign(sign);

        System.out.println("签名后："+ JSON.toJSONString(apiRequest));

        ApiResponse apiResponse=ApiRouter.invoke(apiRequest);

        return apiResponse;
    }
}
