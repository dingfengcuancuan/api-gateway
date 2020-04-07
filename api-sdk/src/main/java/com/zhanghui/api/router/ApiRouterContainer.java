package com.zhanghui.api.router;


import com.zhanghui.api.bean.ApiContext;
import com.zhanghui.api.bean.ApiDefinition;
import com.zhanghui.api.exception.ApiException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负责存储  接口路由信息
 *
 * todo 临时使用
 *
 * 这里有的 则需要走 http接口调用，否则走本地反射
 * 
 * @author zhui
 *
 */
public class ApiRouterContainer {
	/** key:nameversion */
	private static Map<String, Boolean> apiRouterMap = new ConcurrentHashMap<String, Boolean>(64);

	public static void addApiRouter(String name,String version) throws ApiException {
		String key = getKey(name,version);
		boolean hasApi = apiRouterMap.containsKey(key);
		if (hasApi) {
			throw new ApiException("重复申明接口,key:" + key);

		}
		apiRouterMap.put(key, true);
	}



	public static Map<String, Boolean> getApiRouterMap() {
		return apiRouterMap;
	}



	public static String getKey(String name, String version) {
		if (version == null) {
			version = getDefaultVersion();
		}
		return name + version;
	}

	private static String getDefaultVersion() {
        String defaultVersion = ApiContext.getApiConfig().getDefaultVersion();

        if (defaultVersion == null) {
			synchronized (ApiRouterContainer.class) {
				if (defaultVersion == null) {
					defaultVersion = ApiContext.getApiConfig().getDefaultVersion();
				}
			}
		}
		return defaultVersion;
	}


	public static void clear() {
		apiRouterMap.clear();
	}

}
