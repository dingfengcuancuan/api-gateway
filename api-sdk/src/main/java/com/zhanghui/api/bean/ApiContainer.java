package com.zhanghui.api.bean;


import com.zhanghui.api.exception.ApiException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负责存储定义好的接口信息
 * 
 * @author zhui
 *
 */
public class ApiContainer {
	/** key:nameversion */
	private static Map<String, ApiDefinition> apiDefinitionMap = new ConcurrentHashMap<String, ApiDefinition>(64);

	public static void addApiDefinition(ApiDefinition apiDefinition) throws ApiException {
		String key = getKey(apiDefinition);
		boolean hasApi = apiDefinitionMap.containsKey(key);
		if (hasApi) {
			throw new ApiException("重复申明接口,name:" + apiDefinition.getName() + " ,version:"
					+ apiDefinition.getVersion() + ",method:" + apiDefinition.getMethod().getName());

		}
		apiDefinitionMap.put(key, apiDefinition);
	}

	/**
	 * 获取全部接口
	 * 
	 * @return 返回全部解开
	 */
	public static List<ApiDefinition> listAllApi() {
		Collection<ApiDefinition> allApi = apiDefinitionMap.values();
		List<ApiDefinition> ret = new ArrayList<ApiDefinition>(allApi.size());
		for (ApiDefinition apiDefinition : allApi) {
			ret.add(apiDefinition);
		}
		return ret;
	}

	public static Map<String, ApiDefinition> getApiDefinitionMap() {
		return apiDefinitionMap;
	}

	/*public static ApiDefinition getByParam(ApiParam param) {
		String key = getKey(param.fatchName(), param.fatchVersion());
		return apiDefinitionMap.get(key);
	}*/

	public static String getKey(ApiDefinition apiDefinition) {
		return getKey(apiDefinition.getName(), apiDefinition.getVersion());
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
			synchronized (ApiContainer.class) {
				if (defaultVersion == null) {
					defaultVersion = ApiContext.getApiConfig().getDefaultVersion();
				}
			}
		}
		return defaultVersion;
	}

	public static void setApiInfo(ApiDefinition api) {
		ApiDefinition apiDefinition = apiDefinitionMap.get(api.getName() + api.getVersion());
		if (apiDefinition != null) {
            apiDefinition=api;
		}
	}
	
	public static void clear() {
		apiDefinitionMap.clear();
	}

}
