package com.example.wechat.util;

import com.example.wechat.entity.Msg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-30 20:25
 */
public class ChatUtil {

    private static List<Msg> msgList;

    public static List<Msg> getList() {
        msgList=new ArrayList<>();
        Msg msg1=new Msg("hello zhukai 1231231231233333333333333333333333333333333333333333333333123123",Msg.TYPE_RECEIVED);
        Msg msg2=new Msg("nihao",Msg.TYPE_SENT);
        Msg msg3=new Msg("heihei",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        msgList.add(msg2);
        msgList.add(msg3);
        return msgList;
    }
}
