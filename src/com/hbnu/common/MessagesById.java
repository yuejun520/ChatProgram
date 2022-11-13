package com.hbnu.common;

import java.io.Serializable;
import java.util.List;

/**
 * @author luanhao
 * @date 2022年11月10日 5:06
 */
public class MessagesById implements Serializable {
    private String username;
    private List<Message> messages;

    public MessagesById() {
    }

    public MessagesById(String username, List<Message> messages) {
        this.username = username;
        this.messages = messages;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessagesById{" +
                "username='" + username + '\'' +
                ", messages=" + messages +
                '}';
    }
}
