package com.zhanghui.api.controller;


import com.zhanghui.api.annotation.Api;
import com.zhanghui.api.annotation.ApiService;
import com.zhanghui.api.bean.ApiConfig;
import com.zhanghui.api.bean.ApiContainer;
import com.zhanghui.api.bean.ApiContext;
import com.zhanghui.api.bean.ApiDefinition;
import com.zhanghui.api.invoker.ApiInvoker;
import com.zhanghui.api.exception.ApiException;
import com.zhanghui.api.request.ApiRequest;
import com.zhanghui.api.response.ApiResponse;
import com.zhanghui.api.util.ApiUtil;
import com.zhanghui.api.util.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("api")
public class ApiController implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    //初始化Api配置
    @Value("${api.config.appname}")
    private String apiConfigAppName;

    @Value("${api.config.default.version}")
    private String apiConfigDefaultVersion;

    @Value("${api.config.sign.ignore}")
    private String apiConfigIgnoreSign;

    @Value("${api.config.timeout}")
    private String apiConfigTimeout;

    @Value("${api.config.publickey}")
    private String apiConfigPublicKey;

    protected static volatile ApplicationContext ctx;

    protected ApplicationContext getApplicationContext() {
        return ctx;
    }


    /**
     spring容器加载完毕后执行
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //初始化Api配置信息  后续改成读取配置
        ApiConfig apiConfig=new ApiConfig();

        try {

            if (!StringUtils.isEmpty(apiConfigAppName.trim()))
                apiConfig.setAppName(apiConfigAppName.trim());
            if (!StringUtils.isEmpty(apiConfigDefaultVersion.trim()))
                apiConfig.setDefaultVersion(apiConfigDefaultVersion.trim());
            if (!StringUtils.isEmpty(apiConfigIgnoreSign.trim()))
                apiConfig.setIgnoreSign(Boolean.getBoolean(apiConfigIgnoreSign.trim()));
            if (!StringUtils.isEmpty(apiConfigTimeout.trim()))
                apiConfig.setTimeoutSeconds(Integer.parseInt(apiConfigTimeout.trim()));
            if (!StringUtils.isEmpty(apiConfigPublicKey.trim()))
                apiConfig.setPublicKey(apiConfigPublicKey.trim());

            ApiContext.setApiConfig(apiConfig);

        }catch (Exception e){
            logger.error("API参数初始化异常",e);
        }

        //注册接口
        this.apiRegister();
    }


    /**
     * 统计扫描次数
     */
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public synchronized void apiRegister( ) {
        logger.info("开始注册Api接口 >> ");

        //扫描所有使用@OpenApiService注解的类
        Map<String, Object> apiServiceBeanMap = ApplicationContextUtil.getBeansWithAnnotation(ApiService.class);

        if (null == apiServiceBeanMap || apiServiceBeanMap.isEmpty()) {
            logger.info("没有Api接口需要注册");
            return;
        }

        for (Map.Entry<String, Object> map : apiServiceBeanMap.entrySet()) {
            //获取扫描类下所有方法
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(map.getValue().getClass());
            for (Method method : methods) {
                atomicInteger.incrementAndGet();
                //找到带有Api 注解的方法
                Api api = AnnotationUtils.findAnnotation(method, Api.class);
                if (null == api) {
                    continue;
                }

                String version=api.version();
                if(StringUtils.isEmpty(api.version())) {
                    version=ApiContext.getApiConfig().getDefaultVersion();
                }

                //校验 Api 接收参数  是否是一个且是String类型
                //Parameter[] parameters = method.getParameters();  //jdk1.8+
                Class<?>[] paramClasses= method.getParameterTypes();

                Class<?> paramClass = null;
                if (paramClasses != null && paramClasses.length == 1) {

                    paramClass = paramClasses[0];

                    if(paramClass.getName().equals(String.class.getName())){
                        //组建ApiDefinition- 放入api容器
                        ApiDefinition apiDefinition=new ApiDefinition();
                        apiDefinition.setName(api.name());
                        apiDefinition.setDescription(api.desc());
                        apiDefinition.setVersion(version);
                        //apiDefinition.setIgnoreSign(api.ignoreSign());
                        apiDefinition.setClazz(map.getValue().getClass());
                        apiDefinition.setBeanName(map.getKey());
                        apiDefinition.setMethodArguClass(paramClass);  //正常应该都是 String类型
                        apiDefinition.setMethod(method);
                        ApiContainer.addApiDefinition(apiDefinition);
                        logger.info("Api接口加载成功 >> 接口名 = {} , 接口版本={}", api.name(), version);
                    }else{
                        logger.error("Api接口参数错误:仅支持单个字符型参数 >> 接口名 = {}  接口版本 = {} ", api.name(),version);
                        throw new ApiException("Api接口参数错误:仅支持单个字符型参数 >> 接口名 = "+api.name()+"  接口版本 = "+version);
                    }
                }else {
                    logger.error("Api接口参数错误:请增加接口参数 >> 接口名 = {}  接口版本 = {}", api.name(),version);
                    throw new ApiException("Api接口参数错误:请增加接口参数 >> 接口名 = "+api.name()+"  接口版本 = "+version);
                }
            }
        }
        logger.info("注册Api接口完毕 >> 数量 = {} 耗时={} 毫秒", ApiContainer.getApiDefinitionMap().size(), atomicInteger.get());

    }


    @Autowired
    private ApiInvoker apiInvoker;


    /**
     * 统一网关入口
     *
     * @param
     * @author zhui
     */
    @RequestMapping (value ={"","/"})
    public ApiResponse api(@RequestBody ApiRequest apiRequest, HttpServletRequest request) throws Throwable {

        //Map<String, Object> params = WebUtils.getParametersStartingWith(request, StringPool.EMPTY);
        //logger.info("【{}】>> 网关执行开始 >> method={} params = {}", apiRequestId, method, JSON.toJSONString(params));
        //long start = SystemClock.millisClock().now();

        //验签
        ApiUtil.checkSign(apiRequest);

        //请求接口
        ApiResponse result = apiInvoker.invoke(apiRequest.getMethod(), apiRequest.getVersion(),apiRequest.getBizContent());

        //logger.info("【{}】>> 网关执行结束 >> method={},result = {}, times = {} ms",apiRequestId, method, JSON.toJSONString(result), (SystemClock.millisClock().now() - start));

        return result;
    }


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
