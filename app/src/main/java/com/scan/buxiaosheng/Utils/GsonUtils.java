package com.scan.buxiaosheng.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.scan.buxiaosheng.Base.BaseEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * gson工具类
 * Created by BertramTan on 2017/11/26.
 */

public class GsonUtils {
    private static Gson gson;

    static {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    /**
     * 实体转json
     * @param obj entity or object
     * @return string
     */
    public static String toJson(Object obj){
        if (obj == null){
            new Throwable("this object is null").printStackTrace();
            return null;
        }
        return gson.toJson(obj);
    }

    public static BaseEntity ToObj(String json, Class clazz){
        BaseEntity entity = null;
        try{
            Type objectType = type(BaseEntity.class,clazz);
            entity = gson.fromJson(json,objectType);
        }catch (Exception e){
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * json转实体
     * @param json json String
     * @param clazz class
     * @param <T> The generic of object
     * @return object
     */
    public static <T> T gsonToobj(String json, Class<T> clazz){
        T t = null;
        try{
            t = gson.fromJson(json,clazz);
            if (t == null){
                new Throwable("this object is null").printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    /**
     * json转数组实体
     * @param json json String
     * @param <T> The generic of object
     * @return list
     */
    public static <T> List<T> gsonToList(String json, Class<T> clazz){
        List<T> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
