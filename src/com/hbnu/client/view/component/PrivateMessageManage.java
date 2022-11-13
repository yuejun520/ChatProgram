package com.hbnu.client.view.component;

import com.hbnu.client.Communication;
import com.hbnu.client.view.ui.HomeInterface;
import com.hbnu.common.Message;
import com.hbnu.common.MessagesById;
import com.hbnu.common.MsgType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luanhao
 * @date 2022年11月10日 2:51
 */
public class PrivateMessageManage extends Box {
    private static List<MessagesById> messagesByIds = new ArrayList<>();
    private static String username;
    private static JLabel msgTitle;
    private static Box reMsgBox;
    private static JScrollPane rmScrollPane;
    final int WIDTH = 400;
    final int HEIGHT = 600;

    public PrivateMessageManage() {
        super(BoxLayout.Y_AXIS);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

//        提示信息
        JPanel msgTitleJp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        msgTitleJp.setMaximumSize(new Dimension(180, 100));
        msgTitle = new JLabel("请选择好友");
        msgTitleJp.add(msgTitle);
        add(msgTitleJp);

//        接收信息栏
        reMsgBox = Box.createVerticalBox();
        reMsgBox.setMaximumSize(new Dimension(WIDTH, 400));
        rmScrollPane = new JScrollPane(reMsgBox);
        add(rmScrollPane);

//        信息发送栏
        Box seMsgBox = Box.createVerticalBox();
        seMsgBox.setMaximumSize(new Dimension(400, 140));

//        消息输入框
        JTextArea sendText = new JTextArea(10, 5);
        sendText.setLineWrap(true);
        seMsgBox.add(sendText);

//        发送按钮
        JPanel sendJp = new JPanel();
        sendJp.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton sendBtn = new JButton("发送");
        sendJp.add(sendBtn);
        seMsgBox.add(sendJp);

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String otherName = msgTitle.getText();
                if (otherName.equals("请选择好友")) {
                    return;
                }
                Map sendparams = new HashMap<>();
                Map data = new HashMap<>();
                boolean flag = false;
                Message message = new Message();
                String msg = sendText.getText();
                sendText.setText("");
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                message.setSpokenmanId(HomeInterface.username);
                message.setName(HomeInterface.name);
                message.setHp(HomeInterface.getOwnData().getHeadPortrait());
                message.setMessage(msg);
                message.setDate(date);
                for (MessagesById messagesById : messagesByIds) {
                    if (messagesById.getUsername().equals(username)) {
                        flag = true;
                        List<Message> messages = messagesById.getMessages();
                        messages.add(message);
                        messagesById.setMessages(messages);
                    }
                }
                if (!flag) {
                    List<Message> messages = new ArrayList<>();
                    messages.add(message);
                    MessagesById messagesById = new MessagesById(username, messages);
                    messagesByIds.add(messagesById);
                }
                update(username, otherName);
                data.put("otherId", username);
                data.put("message", message);
                sendparams.put("type", MsgType.SEND_MESSAGE_PRIVATE);
                sendparams.put("data", data);
                Communication.send(sendparams);
            }
        });

//        设置消息列表

        add(Box.createVerticalStrut(30));
        add(reMsgBox);
        add(Box.createVerticalStrut(30));
        add(seMsgBox);
    }

    public static void addMessage(String username, Message message) {
        boolean flag = false;
        for (MessagesById messagesById : messagesByIds) {
            if (messagesById.getUsername().equals(username)) {
                flag = true;
                List<Message> messages = messagesById.getMessages();
                messages.add(message);
                messagesById.setMessages(messages);
            }
        }
        if (!flag) {
            List<Message> messages = new ArrayList<>();
            messages.add(message);
            MessagesById messagesById = new MessagesById(username, messages);
            messagesByIds.add(messagesById);
        }
        if (username.equals(PrivateMessageManage.username)) {
            update(username, msgTitle.getText());
        }
    }

    public static void update(String username, String name) {
        reMsgBox = Box.createVerticalBox();
        reMsgBox.setMaximumSize(new Dimension(400, 400));
        PrivateMessageManage.username = username;
        msgTitle.setText(name);
        for (MessagesById messagesById : messagesByIds) {
            if (messagesById.getUsername().equals(username)) {
                for (Message message : messagesById.getMessages()) {
                    if (message.getSpokenmanId().equals(HomeInterface.username)) {
                        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                        jPanel.setMaximumSize(new Dimension(380, 30));
                        JLabel msg = new JLabel(message.getMessage() + "------" + message.getDate() + "------   :我");
                        JLabel hpIcon = new JLabel();
                        ImageIcon icon = new ImageIcon(message.getHp());
                        icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                        hpIcon.setIcon(icon);
                        hpIcon.setSize(20, 20);
                        jPanel.add(msg);
                        jPanel.add(hpIcon);
                        reMsgBox.add(jPanel);
                    } else {
                        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        jPanel.setMaximumSize(new Dimension(380, 30));
                        JLabel msg = new JLabel(message.getName() + ":   ------" + message.getMessage() + "------" + message.getDate());
                        JLabel hpIcon = new JLabel();
                        ImageIcon icon = new ImageIcon(message.getHp());
                        icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
                        hpIcon.setIcon(icon);
                        hpIcon.setSize(20, 20);
                        jPanel.add(hpIcon);
                        jPanel.add(msg);
                        reMsgBox.add(jPanel);
                    }
                }
            }
        }
        rmScrollPane.setViewportView(reMsgBox);
    }
}
