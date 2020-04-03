package com.zhanghui.api.bean.sign;

import lombok.Data;

@Data
public class ApiKey {
    String appId;
    String publicKey;
    String privateKey;
}
