package com.zhanghui.api.invoker;


import com.zhanghui.api.bean.ApiContainer;
import com.zhanghui.api.bean.ApiDefinition;
import com.zhanghui.api.emums.EApiErrorCode;
import com.zhanghui.api.exception.ApiException;
import com.zhanghui.api.response.ApiResponse;
import com.zhanghui.api.util.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * Api请求客户端
 *
 * @author 码农猿
 */
@Service
public class ApiInvoker {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiInvoker.class);

    /**
     * jackson 序列化工具类
     */
    //private static final ObjectMapper JSON_MAPPER = new ObjectMapper();


    /**
     * 验签
     *
     * @param params          请求参数
     * @param requestRandomId 请求随机标识（用于日志中分辨是否是同一次请求）
     * @param charset         请求编码
     * @param signType        签名格式
     * @author 码农猿
     */
    /*
    public void checkSign(Map<String, Object> params, String requestRandomId, String charset, String signType) {

        try {
            //校验签名开关
            if (!applicationProperty.getIsCheckSign()) {
                LOGGER.warn("【{}】>> 验签开关关闭", requestRandomId);
                return;
            }

            //map类型转换
            Map<String, String> map = new HashMap<>(params.size());
            for (String s : params.keySet()) {
                map.put(s, params.get(s).toString());
            }

            LOGGER.warn("【{}】 >> 验签参数 {}", requestRandomId, map);
            boolean checkSign = AlipaySignature.rsaCheckV1(map, applicationProperty.getPublicKey(), charset, signType);
            if (!checkSign) {
                LOGGER.info("【{}】 >> 验签失败 >> params = {}", requestRandomId, JSON.toJSONString(params));
                throw new BusinessException(ApiExceptionEnum.INVALID_SIGN);
            }
            LOGGER.warn("【{}】 >> 验签成功", requestRandomId);

        } catch (Exception e) {
            LOGGER.error("【{}】 >> 验签异常 >> params = {}, error = {}",
                    requestRandomId, JSON.toJSONString(params), ExceptionUtils.getStackTrace(e));
            throw new BusinessException(ApiExceptionEnum.INVALID_SIGN);

        }

    }
    */

    /**
     * Api调用方法
     *
     * @param bizContent   请求
     * @author 码农猿
     */
    public ApiResponse invoke(String name,String version,String bizContent) throws Throwable {
        //获取api方法
        String apiKey=ApiContainer.getKey(name,version);
        ApiDefinition apiDefinition = ApiContainer.getApiDefinitionMap().get(apiKey);

        if (null == apiDefinition) {
            LOGGER.info("API方法不存在 >> 接口名 = {} ,接口版本 = {}", name, version);
            throw new ApiException(EApiErrorCode.API_NOT_EXIST);
        }

        //获得spring bean
        Object bean = ApplicationContextUtil.getBean(apiDefinition.getBeanName());
        if (null == bean) {
            LOGGER.info("API方法不存在 >> 接口名 = {} ,接口版本 = {}", name, version);
            throw new ApiException(EApiErrorCode.API_NOT_EXIST);
        }

        //执行对应方法
        try {
            Object obj = apiDefinition.getMethod().invoke(bean, bizContent);
            return ApiResponse.success(obj);
        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                throw ((InvocationTargetException) e).getTargetException();
            }
            throw new ApiException(EApiErrorCode.SYSTEM_ERROR);
        }

    }


}
