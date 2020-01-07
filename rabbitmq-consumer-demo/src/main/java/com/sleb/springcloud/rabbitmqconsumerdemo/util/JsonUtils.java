package com.sleb.springcloud.rabbitmqconsumerdemo.util;

/**
 * @description: TODO
 * @author: lazasha
 * @date: 2019/12/24  10:44
 **/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：JsonUtils
 * 类描述：用于转换输出对象
 * @version
 */
public class JsonUtils {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(JsonUtils.class);

// private static  Logger logger=Logger.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {}

    static{
        /**重写objectMapper 将null转换为"" **/
        objectMapper.getSerializerProvider().setNullValueSerializer(
                new JsonSerializer<Object>() {
                    @Override
                    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException, IOException {
                        jsonGenerator.writeString("");
                    }
                }
        );
    }

    /**
     * 方法名称: Object2JSON
     * 方法描述: 将对象转换为JSON
     * 返回类型: T
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T Object2JSON(Object object) {
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(object);
        } catch (JsonGenerationException e) {
            logger.error("JsonUtils Object2JSON JsonGenerationException",e);
        } catch (JsonMappingException e) {
            logger.error("JsonUtils Object2JSON JsonMappingException",e);
        } catch (IOException e) {
            logger.error("JsonUtils Object2JSON IOException",e);
        }
        return jsonStr == null ? null : (T) jsonStr;
    }

    /**
     * 方法名称: JSON2Object
     * 方法描述: 将JSON转换为对象
     * 返回类型: T
     * @throws
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T JSON2Object(String JSONStr, Class<T> claz) {
        Object object = null;
        try {
            object = objectMapper.readValue(JSONStr, claz);
        } catch (JsonGenerationException e) {
            logger.error("JsonUtils JSON2Object JsonGenerationException",e);
        } catch (JsonMappingException e) {
            logger.error("JsonUtils JSON2Object JsonMappingException",e);
        } catch (IOException e) {
            logger.error("JsonUtils JSON2Object IOException",e);
        }
        return object != null ? (T) object : null;
    }

    /**
     * 方法名称: JSON2List
     * 方法描述:  JSON 转换为List:
     * 返回类型: List<T>
     * @throws
     */
    @SuppressWarnings("deprecation")
    public static <T> List<T> JSON2List(String jsonStr, Class<T> claz) {
        List<T> result = null;
        try{
            JavaType t = objectMapper.getTypeFactory().
                    constructParametricType(List.class, claz);
            result = objectMapper.readValue(jsonStr, t);
        } catch (JsonGenerationException e) {
            logger.error("JsonUtils JSON2List JsonGenerationException",e);
        } catch (JsonMappingException e) {
            logger.error("JsonUtils JSON2List JsonMappingException",e);
        } catch (IOException e) {
            logger.error("JsonUtils JSON2List IOException",e);
        }
        return result == null?new ArrayList<T>():result;
    }


    /**
     * 方法名称: toJSONString
     * 方法描述: 将对象转换为String
     * 返回类型: String
     * @throws
     */
    public static final String toJSONString(Object object){
        return JSON.toJSONString(object, SerializerFeature.WriteNonStringValueAsString);
    }

    /**
     * 方法名称: toJSON
     * 方法描述: 将java对象转换为JSON
     * 返回类型: Object
     * @throws
     */
    public static final Object toJSON(Object javaObject){
        return JSON.toJSON(javaObject);
    }


    /**
     * 方法名称: parseObject
     * 方法描述: 将String转为对象<T>
     * 返回类型: T
     * @throws
     */
    public static final <T> T parseObject(String jsonStr, Class<T> clazz){
        return JSON.parseObject(jsonStr,clazz);
    }

    /**
     * 方法名称: parseArray
     * 方法描述: 将String转换List<T>:
     * 返回类型: List<T>
     * @throws
     */
    public static final <T> List<T> parseArray(String jsonStr, Class<T> clazz){
        return JSON.parseArray(jsonStr, clazz);
    }
}
