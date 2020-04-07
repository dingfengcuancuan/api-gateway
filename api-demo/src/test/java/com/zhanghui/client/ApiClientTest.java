package com.zhanghui.client;

import cn.com.servyou.common.invocation.restful.client.RestfulClient;
import cn.com.servyou.sbf.toolkits.JsonKit;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.zhanghui.api.request.ApiRequest;
import com.zhanghui.api.util.ApiUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class ApiClientTest {

    @Autowired
    private RestfulClient client;

    private String apiUrl="http://127.0.0.1:8080/demo/api";

    private ApiRequest apiRequest;


    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnLbS4NKB4K6lSwAruObksqJpcrs7t8w51Y38I9ix9r7WCBMiAjRxWu8DNnr2t5ya/XXoioHqyMH3oEkiqsUh3FX1pG/4N/9/hRtFDJqpkCo4Z/D76wEehY+y+k8sGEO+dO8m8bNbmfpcIGDwuPl3qVBFeyJQwPguuWd32/xtzLcYk2C/Eec25++ElHobGvLkRTHHlXeZ+v8IYCIPd058bQssnFMw7nn+Lgr2kQK/2gPQ8gXs8mi1712i/dgoo8bzw9mhzkFnWXExRqzgoXH5DfIMWnlRR4l1u2qXXPU+hIKFua2776qHREmeqPkKiHA2zoMc9LVydVDTGovJjSn00wIDAQAB";

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCcttLg0oHgrqVLACu45uSyomlyuzu3zDnVjfwj2LH2vtYIEyICNHFa7wM2eva3nJr9deiKgerIwfegSSKqxSHcVfWkb/g3/3+FG0UMmqmQKjhn8PvrAR6Fj7L6TywYQ7507ybxs1uZ+lwgYPC4+XepUEV7IlDA+C65Z3fb/G3MtxiTYL8R5zbn74SUehsa8uRFMceVd5n6/whgIg93TnxtCyycUzDuef4uCvaRAr/aA9DyBezyaLXvXaL92CijxvPD2aHOQWdZcTFGrOChcfkN8gxaeVFHiXW7apdc9T6EgoW5rbvvqodESZ6o+QqIcDbOgxz0tXJ1UNMai8mNKfTTAgMBAAECggEAaWS+/7oy5JMv0Pflb63Awq5dcm6fI+XyQ0ABAW6biREgyj9r0MXKMlip9XrO7/8fcTRZ1sS2zJs+WQq2iNiZBCC/Wf5/ldF7xl3nmylsverXEnhMQ+j1yOcyfArA4fS1YtjvfP+drDlmudPYMN59bl3wzHXwj0aZzdDbGBR1F4Y+CzgRObq5092acJz/foUO5SAg/+KLhmQQ28AvBhXPTgV467ER34pDHexCgAnsLsH4KVCPxsbW0eh7vaIgUjWQ/sTAfRzFW5CLJRYO1xH6mDS42dImiUJBiWiz2fK4FRrSM05QXCHkKfXb1DpMnvrNyHyCDZfshRp7UsZoxtVeaQKBgQDvWrX7JNb3Km5JncdKRq+I8lqx+d750Ffj0Ln2wTR0lahonJyF+YL4yuP5R8+sCeFRjcPWMUkZ2oSxxT6gaHRl0Z2fJXqwuCaO9NKq9C03olPmiFXM/H4knXsr1urfcRmE5sg3k0kcmtDnxoyDBBb3w8SRb8p0jqRl/NspDq9IJwKBgQCnnNj/JgCZJkWnK7JPTqnjTlavkV1Zicz89dvQ9Eq7Ff0e/BJiEi+DrndSP2v2YOZEJr0LOpIxJwaNCgea67t055/IlsBqHBu0cBtGQ7i2ykd/3Q1v+q0dEKf3C92lvwcsLRQo01gjnDMRiiSkIf0cYdxs66wgzAxh4xd9BvwNdQKBgAEVrydxNkwudAt/XIzYnykGuCSAVmNZb1yH8J/Oplc06mt28jqlM9O+z6OskKNd9BhzhQSuen9Ufy9zDmKZtpVTitxSxiiQ3RPeximiK6ZJ5QlxarogFs5BrHI5ah0THSN+DEA8OaOYjAPQ4Ygid5wt1fE2yXsXvmT77V5VQ7QXAoGALDRNdl0LY1iYnhIEIK5aV7xdWEg6GlchXMVqh50l6FlQPE+2eW7aYRwuE97uFjhQAkFFMiTsUVI9hAzVHKJ2+cnsdfZsII/xLpEyYEGUAYEvgiVGWfX+md++rITQm9nZhmkNHAdBA4M6ZLHOIAtmuYmFnKQ67RtjWJ2PoEWrS9ECgYBW6QtwiRxoz/Xes9jHRmShVcDfwq210Qubmd81uUtDy/lVOY99qCb4rNsTu3RVrtMubD9syvivDroE6NtFrDBNHAtRaGB77Ivs+WdMdhDzNwGxVfgt6h/qJoY05+NmVPe3ExtC2fs8EWnZkXPbs2qurFxdvhBjxHIQHQwV54GaZQ==";


    /**
     * 字符集
     */
    private static final String CHAR_SET = "UTF-8";
    /**
     * 签名类型  RSA 或 RSA2
     */
    private static final String SIGN_TYPE = "RSA2";


    @Before
    public void init(){
        apiRequest=new ApiRequest();
        apiRequest.setAppId("app1");
        apiRequest.setMethod("demo.print");
        apiRequest.setVersion("1.0");
        User user=new User();
        user.setId("123");
        user.setName("zhui");
        apiRequest.setBizContent(JsonKit.toJson(user));

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
            sign = AlipaySignature.rsaSign(content, PRIVATE_KEY, CHAR_SET,SIGN_TYPE);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        apiRequest.setSign(sign);

        System.out.println("签名后："+ JSON.toJSONString(apiRequest));

        //模拟串改
        //apiRequest.setMethod("cuangai");


    }


    @Test
    public  void  testPostForJson() {
        String result=client.postForJson(apiUrl, JsonKit.toJson(apiRequest),null);
        System.out.println(result);
    }


}
