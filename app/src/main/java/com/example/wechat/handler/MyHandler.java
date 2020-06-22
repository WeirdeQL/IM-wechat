package com.example.wechat.handler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.example.wechat.adapter.ChatAdapter;
import com.example.wechat.adapter.UserAdapter;
import com.example.wechat.entity.Msg;
import com.example.wechat.entity.UserEntity;
import com.example.wechat.util.UserInfo;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-31 21:45
 */
public class MyHandler extends Handler {

    ChatAdapter chatAdapter;
    UserAdapter userAdapter;

    public MyHandler(ChatAdapter chatAdapter, UserAdapter userAdapter) {
        this.chatAdapter = chatAdapter;
        this.userAdapter = userAdapter;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        //把所有的信息集中在这里处理
        super.handleMessage(msg);
        Map<String,String> mapType= JSON.parseObject(msg.obj.toString(), Map.class);
        if(msg.what==0x123){
            Log.d("TAG", "我是聊天界面收到的信息  : "+msg.obj.toString());
            Log.d("TAG", "我是聊天界面收到的信息（渲染信息）: "+msg.obj.toString());
            /*msgList.add(new Msg(map.get("content").toString(),Msg.TYPE_RECEIVED));
            adapter.notifyDataSetChanged();*/
            UserInfo.chatAdapterMap.get(mapType.get("from")).addData(new Msg(mapType.get("content"),Msg.TYPE_RECEIVED));
            userAdapter.infoComing(mapType.get("from"));

        }else if(msg.what==0x234){
            Log.d("TAG", " -------》我是用户界面收到的信息: "+msg.obj.toString());
            UserEntity userEntity=JSON.parseObject(mapType.get("content").toString(),UserEntity.class);
            Log.d("TAG", " -------》我是用户界面收到的信息（增加一个用户）: "+msg.obj.toString());
            userAdapter.addData(userEntity);
            UserInfo.chatAdapterMap.put(userEntity.getPort(),new ChatAdapter(new ArrayList<Msg>()));

        }else if(msg.what==0x345){
            Log.d("TAG", " -------》我是用户界面收到的信息（减少一个用户）: "+msg.obj.toString());
            userAdapter.removeData(String.valueOf(mapType.get("from")));
            UserInfo.chatAdapterMap.remove(String.valueOf(mapType.get("from")));
        }
    }
}
