package com.example.wechat.entity;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-29 20:15
 */
public class UserEntity {
    //端口号
    private String port;
    //名称
    private String name;
    //是否有信息
    private boolean isComing;

    @Override
    public String toString() {
        return "UserEntity{" +
                "port='" + port + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public UserEntity() {
    }

    public UserEntity(String port, String name) {
        this.port = port;
        this.name = name;
    }

    public boolean isComing() {
        return isComing;
    }

    public void setComing(boolean coming) {
        isComing = coming;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
