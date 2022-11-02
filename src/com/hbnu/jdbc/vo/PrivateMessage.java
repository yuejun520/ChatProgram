package com.hbnu.jdbc.vo;

import java.util.Arrays;

/**
 * @author luanhao
 * @date 2022年11月02日 10:50
 */
public class PrivateMessage {
    private int id;
    private int friendId;
    private int spokesmanId;
    private String message;
    private byte[] picture;
    private String speakingTime;

    public PrivateMessage() {
    }

    public PrivateMessage(int id, int friendId, int spokesmanId, String message, byte[] picture, String date) {
        this.id = id;
        this.friendId = friendId;
        this.spokesmanId = spokesmanId;
        this.message = message;
        this.picture = picture;
        this.speakingTime = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
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
        return "PrivateMessage{" +
                "id=" + id +
                ", friendId=" + friendId +
                ", spokesmanId=" + spokesmanId +
                ", message='" + message + '\'' +
                ", picture=" + Arrays.toString(picture) +
                ", speakingTime='" + speakingTime + '\'' +
                '}';
    }
}
