package com.example.wechat.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.wechat.adapter.ChatAdapter;
import com.example.wechat.entity.Msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-29 21:24
 */
public class ClientThread1 implements Runnable {

    private Socket s;
    public Handler revHandler;

    BufferedReader br = null;
    OutputStream os = null;
    Handler handler;

    public ClientThread1(Socket s, Handler handler) {
        this.s = s;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
            // 启动一条子线程来读取服务器响应的数据
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    String content = null;
                    while ((content = readFromClient()) != null) {
                        //判断一下是什么信息
                        Map<String,String> map=JSON.parseObject(content, Map.class);
                        Message message = new Message();
                        message.what = 0x123;
                        Log.d("TAG", "接收到服务器的信息 run: "+content);
                        if(map.get("type").equals("msg")){
                            //信息
                            Log.d("TAG", "run: 当前类型是信息");
                            message.what = 0x123;
                        }else if(map.get("type").equals("SYSTEM")){
                            //增加
                            Log.d("TAG", "run: 当前类型是系统提示");
                            message.what = 0x234;
                        }else if(map.get("type").equals("QUIT")){
                            //减少
                            Log.d("TAG", "run: 当前类型是用户退出");
                            message.what = 0x345;
                        }else{
                            Log.d("TAG", "接收到服务器的信息 +++++++++++++++++++++++++++++++++++++++++++++无法判断类型");
                        }
                        message.obj = content;
                        handler.sendMessage(message);

                        //adapter.addData(new Msg(content,Msg.TYPE_RECEIVED));
                    }
                }
            }.start();
            Looper.prepare();
            revHandler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 0x345) {
                        try {
                            os.write((msg.obj.toString() + "\r\n").getBytes("utf-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Looper.loop();
        } catch (SocketTimeoutException e) {
            System.out.println("网络超时");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readFromClient() {
        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
