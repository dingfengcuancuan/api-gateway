package com.zhanghui.api.router;

import cn.com.servyou.common.invocation.restful.client.RestfulClient;
import com.alibaba.fastjson.JSON;
import com.zhanghui.api.controller.ApiController;
import com.zhanghui.api.emums.EApiErrorCode;
import com.zhanghui.api.exception.ApiException;
import com.zhanghui.api.invoker.ApiInvoker;
import com.zhanghui.api.request.ApiRequest;
import com.zhanghui.api.response.ApiResponse;
import com.zhanghui.api.util.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collections;

/**
 * Api 路由
 */
public class ApiRouter {

    private static final Logger logger = LoggerFactory.getLogger(ApiRouter.class);
    private static ApiInvoker apiInvoker = (ApiInvoker) ApplicationContextUtil.getBean("apiInvoker");
    private static RestfulClient restfulClient= (RestfulClient) ApplicationContextUtil.getBean("restfulClient");

    /**
     * 接口动态路由
     * 根据 服务名 判断 是走 本地调用（反射）  还是 远程调用（HTTP）
     * @param apiRequest
     * @return
     */
    public static ApiResponse invoke(ApiRequest apiRequest){
        /**
         * 整理逻辑
         * 1.判断 是否有配置或数据，配置某个服务名 有差异化或需要调用远程接口
         * 2.如果无 ，则使用反射调用本地接口
         * 3.如果有，则使用http调用远程接口
         * 4.为兼容各种接口， 要求 输入参数为json字符串，返回结果也统一为json字符串
         */
         try {
             if (!CollectionUtils.isEmpty(ApiRouterContainer.getApiRouterMap())) {
                 String key=ApiRouterContainer.getKey(apiRequest.getMethod(),apiRequest.getVersion());
                 if(ApiRouterContainer.getApiRouterMap().containsKey(key)){  //走HTTP服务
                     //todo  获取 url地址
                     String url="http://127.0.0.1:8080/demo/api";
                     logger.debug("服务路由 >> 调用http服务");
                     String result=restfulClient.postForJson(url, JSON.toJSONString(apiRequest),null);

                     return  restfulClient.postForObject(url,apiRequest,ApiResponse.class);
                 }else{ //走本地 反射
                     logger.debug("服务路由 >> 调用本地服务(反射)");
                     return apiInvoker.invoke(apiRequest);
                 }
             } else {  //为空 直接走本地反射
                 //ApiInvoker apiInvoker = (ApiInvoker) ApplicationContextUtil.getBean("apiInvoker");
                 return apiInvoker.invoke(apiRequest);
             }
         }catch (Exception e){
             logger.error("API路由出错 >> 接口名 = {} ,接口版本 = {}", apiRequest.getMethod(), apiRequest.getVersion());
             throw new ApiException(EApiErrorCode.ROUTER_ERROR);
         }
    }


    /**
     * 提供纯 字符串版本 通常给外部系统用
     * @param param
     * @return
     */
    public static  String invoke(String param){
        /**
         * 整理逻辑
         * 1.判断 是否有配置或数据，配置某个服务名 有差异化或需要调用远程接口
         * 2.如果无 ，则使用反射调用本地接口
         * 3.如果有，则使用http调用远程接口
         * 4.为兼容各种接口， 要求 输入参数为json字符串，返回结果也统一为json字符串
         */
        ApiRequest apiRequest= JSON.parseObject(param,ApiRequest.class);

        ApiResponse apiResponse=invoke(apiRequest);

        return JSON.toJSONString(apiResponse);
    }
}
