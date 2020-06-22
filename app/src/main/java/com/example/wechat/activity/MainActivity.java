package com.example.wechat.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.wechat.R;
import com.example.wechat.fragment.ChatFragment;
import com.example.wechat.fragment.FindFragment;
import com.example.wechat.fragment.LinkFragment;
import com.example.wechat.fragment.MineFragment;
import com.example.wechat.util.TransportMsg;
import com.example.wechat.util.UserInfo;
import com.example.wechat.viewPager.IndexViewPagerAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    ViewPager viewPager;
    ImageButton btn1,btn2,btn3,btn4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        setViewPager();
        setOnClickEvent();

    }

    private void setOnClickEvent() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });
    }

    private void findViewsById(){
        viewPager=findViewById(R.id.viewPager);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
    }

    private void setViewPager(){
        ArrayList<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(new ChatFragment());
        fragmentList.add(new LinkFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new MineFragment());
        IndexViewPagerAdapter pagerAdapter=new IndexViewPagerAdapter(getSupportFragmentManager(),0,fragmentList);
        viewPager.setAdapter(pagerAdapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.d("TAG", "onPageSelected: 当前选中的是："+position);
                Bitmap bitmap;
                switch (position){
                    case 0:
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_chat_in);
                        btn1.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_link_out);
                        btn2.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_find_out);
                        btn3.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_mine_out);
                        btn4.setImageBitmap(bitmap);
                        break;
                    case 1:
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_chat_out);
                        btn1.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_link_in);
                        btn2.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_find_out);
                        btn3.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_mine_out);
                        btn4.setImageBitmap(bitmap);
                        break;
                    case 2:
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_chat_out);
                        btn1.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_link_out);
                        btn2.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_find_in);
                        btn3.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_mine_out);
                        btn4.setImageBitmap(bitmap);
                        break;
                    case 3:
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_chat_out);
                        btn1.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_link_out);
                        btn2.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_find_out);
                        btn3.setImageBitmap(bitmap);
                        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.weirdo_mine_in);
                        btn4.setImageBitmap(bitmap);
                        break;
                    default:

                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Message message=new Message();
        message.what=0x345;
        message.obj=TransportMsg.getJsonString("QUIT","","SYSTEM","退出了群聊");

        UserInfo.clientThread.revHandler.sendMessage(message);
    }

}
