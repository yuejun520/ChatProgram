package com.hbnu.jdbc.vo;

import java.util.Arrays;

/**
 * @author luanhao
 * @date 2022年11月02日 11:20
 */
public class GroupMessage {
    private int id;
    private int groupId;
    private int spokesmanId;
    private String message;
    private byte[] picture;
    private String speakingTime;

    public GroupMessage() {
    }

    public GroupMessage(int id, int groupId, int spokesmanId, String message, byte[] picture, String speakingTime) {
        this.id = id;
        this.groupId = groupId;
        this.spokesmanId = spokesmanId;
        this.message = message;
        this.picture = picture;
        this.speakingTime = speakingTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSpokesmanId() {
        return spokesmanId;
    }

    public void setSpokesmanId(int spokesmanId) {
        this.spokesmanId = spokesmanId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getSpeakingTime() {
        return speakingTime;
    }

    public void setSpeakingTime(String speakingTime) {
        this.speakingTime = speakingTime;
    }

    @Override
    public String toString() {
        return "GroupMessage{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", spokesmanId=" + spokesmanId +
                ", message='" + message + '\'' +
                ", picture=" + Arrays.toString(picture) +
                ", speakingTime='" + speakingTime + '\'' +
                '}';
    }
}
