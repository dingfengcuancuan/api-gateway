package com.zhanghui.api.json;

import cn.com.servyou.sbf.toolkits.JsonKit;
import com.zhanghui.api.response.ApiResponse;

public class ApiResponseJsonTest {
    public static void main(String[] args) {
        ApiResponse response=new ApiResponse();
        response.setCode("1000");
        response.setMsg("success");
        response.setSign("eregegdfdx");

        User user=new User();
        user.setId("1");
        user.setName("zhanghui");
        response.setResult(user);


        String jsonResult= JsonKit.toJson(response);
        System.out.println(jsonResult);
    }
}
