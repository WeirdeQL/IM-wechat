package com.example.wechat.entity;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-30 20:20
 */
public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT =1 ;
    private String content;
    private int type;
    public Msg(String content,int type){
        this.content=content;
        this.type=type;
    }
    public String getContent(){
        return content;
    }

    public int getType() {
        return type;
    }
}
