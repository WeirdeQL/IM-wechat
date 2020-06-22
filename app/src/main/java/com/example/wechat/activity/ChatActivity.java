package com.example.wechat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.wechat.R;
import com.example.wechat.adapter.ChatAdapter;
import com.example.wechat.entity.Msg;
import com.example.wechat.entity.UserEntity;
import com.example.wechat.thread.ClientThread1;
import com.example.wechat.util.ChatUtil;
import com.example.wechat.util.TransportMsg;
import com.example.wechat.util.UserInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-29 21:25
 */
public class ChatActivity extends Activity {

    TextView name;
    RecyclerView chat_activity_recycleView;
    EditText content;
    Button send;
    Handler handler;
    Intent intent;
    List<Msg> msgList;
    ChatAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        findViews();
        init();
        //获取数据
        msgList=new ArrayList<>();
        //adapter=new ChatAdapter(msgList);
        adapter=UserInfo.chatAdapterMap.get(intent.getStringExtra("port"));
        LinearLayoutManager linearLayout=new LinearLayoutManager(null);
        chat_activity_recycleView.setLayoutManager(linearLayout);
        chat_activity_recycleView.setAdapter(adapter);

        //处理返回的信息
        /*handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==0x123){
                    Map<String,Object> map=JSON.parseObject(msg.obj.toString(),Map.class);
                    Log.d("TAG", "我是聊天界面收到的信息  : "+msg.obj.toString());
                    Log.d("TAG", "我是聊天界面收到的信息（渲染信息）: "+msg.obj.toString());
                    *//*msgList.add(new Msg(map.get("content").toString(),Msg.TYPE_RECEIVED));
                    adapter.notifyDataSetChanged();*//*
                    adapter.addData(new Msg(map.get("content").toString(),Msg.TYPE_RECEIVED));
                }
            }
        };*/

        //启动

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=content.getText().toString();
                if(str.equals("")){
                    Toast.makeText(ChatActivity.this,"发送内容不能位空",Toast.LENGTH_SHORT).show();
                    return;
                }
                //发送数据
                Msg msg=new Msg(str,Msg.TYPE_SENT);
                adapter.addData(msg);
                //发送信息给服务器
                //当用户按下发送按钮后，将用户输入的数据封装成Message，
                // 然后发送给子线程的Handler
                Message message=new Message();
                message.what=0x345;
                message.obj=TransportMsg.getJsonString("msg","",intent.getStringExtra("port"),str);
                UserInfo.clientThread.revHandler.sendMessage(message);


                content.setText("");

            }
        });


    }

    private void init() {
        intent=getIntent();
        String str=intent.getStringExtra("name");
        name.setText(str);
    }

    private void findViews() {
        name=findViewById(R.id.chat_name);
        content=findViewById(R.id.content);
        send=findViewById(R.id.send);
        chat_activity_recycleView=findViewById(R.id.chat_activity_recycleView);
    }

}
