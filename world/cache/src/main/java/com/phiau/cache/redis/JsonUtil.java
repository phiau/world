/**
 * 
 */
package com.phiau.cache.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	/**
	 * 对象实例转换成Json字符串
	 * 
	 * @param instance
	 * @return
	 */
	public static final String toJsonString(Object instance) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonText = null;
		try {
			jsonText = mapper.writeValueAsString(instance);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		return jsonText;
	}

	/**
	 * json字符串转换成对象实例
	 * 
	 * @param jsonText
	 * @param clazz
	 * @return
	 */
	public static final <T> T toInstance(String jsonText, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		T instance = null;
		try {
			instance = mapper.readValue(jsonText, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return instance;
	}

	/**
	 * 转换为List
	 * @param jsonText
	 * @param elementClass
	 * @return
	 */
	public static final <T> List<T> toList(String jsonText, Class<T> elementClass) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, elementClass);
		List<T> instance = null;
		try {
			instance = mapper.readValue(jsonText, javaType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return instance;
	}
	
	/**
	 * 转换为Map
	 * @param jsonText
	 * @param elementClass
	 * @return
	 */
	public static final <T> Map<String,T> toMap(String jsonText, Class<T> elementClass) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(HashMap.class, elementClass);
		Map<String,T> instance = null;
		try {
			instance = mapper.readValue(jsonText, javaType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return instance;
	}
}
