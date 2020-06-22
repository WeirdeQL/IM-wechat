package com.example.wechat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wechat.R;
import com.example.wechat.thread.ClientThread1;
import com.example.wechat.util.UserInfo;

import java.io.IOException;
import java.net.Socket;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-29 22:42
 */
public class LoginActivity extends AppCompatActivity {

    EditText ip_address,port,name;
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        validation();
    }

    private void validation() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ip_address.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"ip地址不能为空！",Toast.LENGTH_SHORT).show();
                }else if(port.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"端口号不能为空！",Toast.LENGTH_SHORT).show();
                }else if(name.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                }else{
                    saveUserInfo();
                    //建立socket连接
                    new MyThread().start();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void saveUserInfo() {
        UserInfo.ip=ip_address.getText().toString();
        UserInfo.port= port.getText().toString();
        UserInfo.username=name.getText().toString();

    }

    private void findViews() {
        ip_address=findViewById(R.id.ip_addr);
        port=findViewById(R.id.port);
        name=findViewById(R.id.username);
        login=findViewById(R.id.login_btn);
    }

    class MyThread extends Thread {
        Socket socket;

        @Override
        public void run() {
            try {
                socket = new Socket(ip_address.getText().toString(), Integer.parseInt(port.getText().toString()));
                socket.setKeepAlive(true);
                UserInfo.socket=socket;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }


}
