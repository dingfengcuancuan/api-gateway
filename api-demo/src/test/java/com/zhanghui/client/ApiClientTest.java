package com.zhanghui.client;

import cn.com.servyou.common.invocation.restful.client.RestfulClient;
import cn.com.servyou.sbf.toolkits.JsonKit;
import com.zhanghui.api.request.ApiRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class ApiClientTest {

    @Autowired
    private RestfulClient client;

    private String apiUrl="http://127.0.0.1:8080/demo/api";

    private ApiRequest apiRequest;

    @Before
    public void init(){
        apiRequest=new ApiRequest();
        apiRequest.setAppId("testid");
        apiRequest.setMethod("demo.print");
        apiRequest.setVersion("1.0");
        User user=new User();
        user.setId("123");
        user.setName("zhui");
        apiRequest.setBizContent(JsonKit.toJson(user));
    }


    @Test
    public  void  testPostForJson() {
        String result=client.postForJson(apiUrl, JsonKit.toJson(apiRequest),null);
        System.out.println(result);
    }
}
