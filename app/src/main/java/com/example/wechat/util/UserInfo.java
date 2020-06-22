package com.example.wechat.util;

import com.example.wechat.adapter.ChatAdapter;
import com.example.wechat.adapter.UserAdapter;
import com.example.wechat.thread.ClientThread1;

import java.net.Socket;
import java.util.Map;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-29 23:05
 */
public class UserInfo {

    public static String ip;
    public static String port;
    public static String username;
    public static Socket socket;
    public static ClientThread1 clientThread;
    public static UserAdapter userAdapter;
    public static ChatAdapter chatAdapter;
    public static Map<String,ChatAdapter> chatAdapterMap;
}
