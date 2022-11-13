package com.hbnu.common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author luanhao
 * @date 2022年11月08日 16:37
 */
public class MsgObjectList implements Serializable {
    String username;
    String name;
    byte[] headPortrait;
    boolean isGroup;

    public MsgObjectList() {
    }

    public MsgObjectList(String username, String name, byte[] headPortrait, boolean isGroup) {
        this.username = username;
        this.name = name;
        this.headPortrait = headPortrait;
        this.isGroup = isGroup;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(byte[] headPortrait) {
        this.headPortrait = headPortrait;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    @Override
    public String toString() {
        return "MsgObjectList{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", headPortrait=" + Arrays.toString(headPortrait) +
                ", isGroup=" + isGroup +
                '}';
    }
}
