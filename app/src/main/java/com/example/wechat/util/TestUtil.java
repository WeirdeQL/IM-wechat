package com.example.wechat.util;

import com.example.wechat.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-29 20:56
 */
public class TestUtil {
    static ArrayList<UserEntity> list;

    public static ArrayList<UserEntity> getData(){
        list=new ArrayList<>();
        list.add(new UserEntity("8080","张三"));
        list.add(new UserEntity("8080","李四"));
        list.add(new UserEntity("8080","王五"));
        list.add(new UserEntity("8080","王二狗"));
        return  list;
    }
}
