package com.zhanghui.api.json;

import cn.com.servyou.sbf.toolkits.JsonKit;
import com.zhanghui.api.request.ApiRequest;

public class ApiRequestJsonTest {
    public static void main(String[] args) {
        ApiRequest request=new ApiRequest();
        request.setAppId("appid");

        String jsonResult=JsonKit.toJson(request);
        System.out.println(jsonResult);
    }
}
