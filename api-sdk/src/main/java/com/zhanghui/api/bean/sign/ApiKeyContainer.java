package com.zhanghui.api.bean.sign;


import com.zhanghui.api.bean.ApiContext;
import com.zhanghui.api.bean.ApiDefinition;
import com.zhanghui.api.exception.ApiException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负责存储定义好的签名 公钥和私钥 信息
 * 类似缓存
 * @author zhui
 *
 */
public class ApiKeyContainer {

	/** key:appid */
	private static Map<String, ApiKey> apiKeyMap = new ConcurrentHashMap<String, ApiKey>(64);

	public static void addApiKey(ApiKey apiKey) throws ApiException {
		String key = getKey(apiKey);
		boolean hasKey = apiKeyMap.containsKey(key);
		if (hasKey) {
			throw new ApiException("重复的商户公钥与秘钥信息,appid:" + apiKey.getAppId() + " ,publicKey:"+ apiKey.getPrivateKey() + ",privateKey:" + apiKey.getPrivateKey());

		}
		apiKeyMap.put(key, apiKey);
	}

	/**
	 * 获取全部Key
	 * 
	 * @return 返回全部解开
	 */
	public static List<ApiKey> listAllKey() {
		Collection<ApiKey> allKey = apiKeyMap.values();
		List<ApiKey> ret = new ArrayList<ApiKey>(allKey.size());
		for (ApiKey apiKey : allKey) {
			ret.add(apiKey);
		}
		return ret;
	}

	/**
	 * 获取ApiKey
	 *
	 * @return 返回ApiKey
	 */
	public static ApiKey getApiKey(String appid) {
		return apiKeyMap.get(appid);
	}

	public static Map<String, ApiKey> apiKeyMap() {
		return apiKeyMap;
	}


	public static String getKey(ApiKey apiKey) {
		return getKey(apiKey.getAppId());
	}

	public static String getKey(String appid) {
		return appid;
	}

	/**
	 * 更新apiKey
	 * @param key
	 */
	public static void setApiKey(ApiKey key) {
		ApiKey apiKey = apiKeyMap.get(key.getAppId());
		if (apiKey != null) {
			apiKey=apiKey;
		}
	}
	
	public static void clear() {
		apiKeyMap.clear();
	}

}
