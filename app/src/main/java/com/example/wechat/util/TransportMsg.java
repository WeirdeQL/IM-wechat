package com.example.wechat.util;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-31 2:15
 */
public class TransportMsg {
    public final static String TYPE="type";
    public final static String FROM="from";
    public final static String TO="to";
    public final static String CONTENT="content";

    static Map<String,String> map;

    public static Map<String,String> getDemo(String type,String from,String to,String content){
        map=new HashMap<>();
        map.put(TYPE,type);
        map.put(FROM,from);
        map.put(TO,to);
        map.put(CONTENT,content);
        return map;
    }

    public static String getJsonString(String type,String from,String to,String content){
        map=new HashMap<>();
        map.put(TYPE,type);
        map.put(FROM,from);
        map.put(TO,to);
        map.put(CONTENT,content);
        return JSON.toJSONString(map);
    }

}
