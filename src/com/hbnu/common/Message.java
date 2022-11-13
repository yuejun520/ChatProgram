package com.hbnu.common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author luanhao
 * @date 2022年11月10日 4:59
 */
public class Message implements Serializable {
    private String spokenmanId;
    private String name;
    private byte[] hp;
    private String message;
    private String date;

    public Message() {
    }

    public Message(String spokenmanId, String name, byte[] hp, String message, String date) {
        this.spokenmanId = spokenmanId;
        this.name = name;
        this.hp = hp;
        this.message = message;
        this.date = date;
    }

    public String getSpokenmanId() {
        return spokenmanId;
    }

    public void setSpokenmanId(String spokenmanId) {
        this.spokenmanId = spokenmanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getHp() {
        return hp;
    }

    public void setHp(byte[] hp) {
        this.hp = hp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "spokenmanId='" + spokenmanId + '\'' +
                ", name='" + name + '\'' +
                ", hp=" + Arrays.toString(hp) +
                ", message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
