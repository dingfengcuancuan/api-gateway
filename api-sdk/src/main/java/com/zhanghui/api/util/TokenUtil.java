package com.zhanghui.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.zhanghui.api.emums.EApiErrorCode;
import com.zhanghui.api.exception.ApiException;

public class TokenUtil {

    /**
     * todo 这个密码不该放在这
     */
    public  static String TOKEN_SECRET="f26ecerererere6871734";

    /**
     * 获取token
     * @param appid
     * @param secret
     * @return
     */
    public static String getToken(String  appid,String secret){

        String token;
        try {
            token = JWT.create().withAudience(appid).sign(Algorithm.HMAC256(secret));
        }catch (Exception e){
            throw  new ApiException(EApiErrorCode.TOKEN_ERROR);
        }
        return token;
    }

    public static void checkToken(String token,String secret){
        String appid=JWT.decode(token).getAudience().get(0);
        if(null==appid){
            throw  new ApiException(EApiErrorCode.TOKEN_ERROR);
        }
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwtVerifier.verify(token);
        }catch (Exception e){
            throw  new ApiException(EApiErrorCode.TOKEN_ERROR);
        }
    }


    public static void main(String[] args) {
        String token=getToken("app1",TOKEN_SECRET);
        System.out.println(token);

        System.out.println("check ok!");
        checkToken(token,TOKEN_SECRET);

        System.out.println("check error!");
        checkToken(token+"1",TOKEN_SECRET);
        System.out.println("i am here!");
    }
}
