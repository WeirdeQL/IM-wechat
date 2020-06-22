package com.example.wechat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.wechat.R;
import com.example.wechat.thread.ClientThread;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-29 16:29
 */
public class TestActivity extends Activity {

    TextView show;
    EditText content;
    Button sendBtn;
    Handler handler;
    ClientThread clientThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findView();

        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==0x123){
                    show.append("\n服务器："+msg.obj.toString());
                }
            }
        };

        //clientThread =new ClientThread(handler,);
        new Thread(clientThread).start();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当用户按下发送按钮后，将用户输入的数据封装成Message，

                // 然后发送给子线程的Handler
                Message message=new Message();
                message.what=0x345;
                message.obj=content.getText().toString();
                clientThread.revHandler.sendMessage(message);
                //把发送的内容展示在show上
                show.append("\r\n我："+content.getText());
                content.setText("");


            }
        });


    }

    private void findView() {
        show=findViewById(R.id.show);
        content=findViewById(R.id.content);
        sendBtn=findViewById(R.id.sendBtn);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Message message=new Message();
        message.what=0x345;
        message.obj="退出了群聊";
        clientThread.revHandler.sendMessage(message);
        //把发送的内容展示在show上
        show.append("\r\n我："+content.getText());
        content.setText("");

    }
}
