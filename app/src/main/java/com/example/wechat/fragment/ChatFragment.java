package com.example.wechat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.wechat.R;
import com.example.wechat.activity.ChatActivity;
import com.example.wechat.activity.MainActivity;
import com.example.wechat.adapter.ChatAdapter;
import com.example.wechat.adapter.UserAdapter;
import com.example.wechat.entity.Msg;
import com.example.wechat.entity.UserEntity;
import com.example.wechat.handler.MyHandler;
import com.example.wechat.thread.ClientThread1;
import com.example.wechat.util.ChatUtil;
import com.example.wechat.util.TestUtil;
import com.example.wechat.util.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-29 19:20
 */
public class ChatFragment extends Fragment {

    Handler handler;
    UserAdapter userAdapter;
    ChatAdapter chatAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
        RecyclerView recyclerView=view.findViewById(R.id.chat_recycleView);
        //接受用户列表
        ArrayList<UserEntity> userEntityArrayList=new ArrayList<UserEntity>();
        userEntityArrayList.add(new UserEntity("All","世界聊天"));
        userAdapter=new UserAdapter(userEntityArrayList);
        ArrayList<Msg> msgArrayList=new ArrayList<>();
        chatAdapter=new ChatAdapter(msgArrayList);

        UserInfo.userAdapter=userAdapter;
        UserInfo.chatAdapter=chatAdapter;

        Map<String,ChatAdapter> map=new HashMap<>();
        map.put("All",new ChatAdapter(new ArrayList<Msg>()));
        UserInfo.chatAdapterMap=map;

        //处理返回的信息
        /*handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Map<String,Object> mapType=JSON.parseObject(msg.obj.toString(), Map.class);
                if(msg.what==0x234){
                    Log.d("TAG", " -------》我是用户界面收到的信息: "+msg.obj.toString());
                    UserEntity userEntity=JSON.parseObject(mapType.get("content").toString(),UserEntity.class);
                    Log.d("TAG", " -------》我是用户界面收到的信息（增加一个用户）: "+msg.obj.toString());
                    adapter.addData(userEntity);

                }else if(msg.what==0x345){
                    Log.d("TAG", " -------》我是用户界面收到的信息（减少一个用户）: "+msg.obj.toString());
                    adapter.removeData(String.valueOf(mapType.get("from")));
                }
            }
        };*/
        //启动
        handler=new MyHandler(chatAdapter,userAdapter);

        ClientThread1 clientThread=new ClientThread1(UserInfo.socket,handler);
        new Thread(clientThread).start();
        UserInfo.clientThread=clientThread;

        userAdapter.setListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void touch(UserEntity userEntity) {
                Log.d("TAG", "touch: "+userEntity.toString());
                Intent intent=new Intent((MainActivity)getActivity(), ChatActivity.class);
                intent.putExtra("name",userEntity.getName());
                intent.putExtra("port",userEntity.getPort());
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayout=new LinearLayoutManager(null);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(userAdapter);

        return view;
    }
}
