package com.example.wechat.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-29 21:24
 */
public class ClientThread implements Runnable {

    private Socket s;
    private Handler handler;
    public Handler revHandler;

    BufferedReader br = null;
    OutputStream os = null;

    public ClientThread(Socket s, Handler handler) {
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
                        Message message = new Message();
                        message.what = 0x123;
                        message.obj = content;
                        handler.sendMessage(message);
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
