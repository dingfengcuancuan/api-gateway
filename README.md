# api-gateway
统一接口网关：统一接口入口、接口编写规范、接口注解注册  基于spring+springmvc


#接口请求

{
   "app_id":"zhui_appid",
   "method":"zhui.cxjm.zjsb",
   "format":"JSON",
   "charset":"UTF-8",
   "sign_type":"RAS2",
   "sign":"xtmegetqq==eerewwWggd1=",
   "timestamp":"2020-03-04 03:07:50",
   "version":"1.0",
   "notify_url":"http://abc.com/receive.html",
   "app_auth_token":"ewruwerdfasdfaere543436465",
   "biz_content":{
      "sbdjxh":"340404199008087654",
      "xm":"张三"
   }
}

#接口返回
{
    "code": "1000",
    "msg": "success",
    "sign": "xtmegetqq==eerewwWggd1=",
    "result": {
        "sbdjxh": "340404199008087654",
        "xm": "张三"
    }
}