package com.example.wechat.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: admin
 * @Date: 2018-05-03
 * @Time: 17:55
 * @Description:
 */
public class JsonUtil {
    /**
     * json字符串转对象
     *
     * @param str    字符串
     * @param classz
     * @param <T>
     * @return
     */
    public static <T> T toObj(String str, Class<T> classz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            T obj = mapper.readValue(str, classz);
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toObj(String str, TypeReference<T> valueTypeRef) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            T obj = mapper.readValue(str, valueTypeRef);
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json 字符串转list对象
     *
     * @param str
     * @param classz
     * @param <T>
     * @return
     */
    public static <T> List<T> toObjList(String str, Class<T> classz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, classz);
        try {
            List<T> list = (List<T>) mapper.readValue(str, javaType);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转json字符串
     *
     * @param obj 数据
     * @return json字符串
     */
    public static String toJsonString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
