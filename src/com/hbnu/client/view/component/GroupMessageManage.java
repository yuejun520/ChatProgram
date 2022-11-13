package com.hbnu.client.view.component;

import com.hbnu.client.Communication;
import com.hbnu.client.view.ui.HomeInterface;
import com.hbnu.common.Message;
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
public class GroupMessageManage extends Box {
    private static List<Message> messages = new ArrayList<>();
    private static JLabel msgTitle;
    private static Box reMsgBox;
    private static JScrollPane rmScrollPane;
    final int WIDTH = 400;
    final int HEIGHT = 600;

    public GroupMessageManage() {
        super(BoxLayout.Y_AXIS);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

//        提示信息
        JPanel msgTitleJp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        msgTitleJp.setMaximumSize(new Dimension(180, 100));
        msgTitle = new JLabel("公共群聊");
        msgTitleJp.add(msgTitle);
        add(msgTitleJp);

//        接收信息栏
        reMsgBox = Box.createVerticalBox();
        reMsgBox.setMaximumSize(new Dimension(WIDTH, 400));
        rmScrollPane = new JScrollPane(reMsgBox);
        add(rmScrollPane);

//        信息发送栏
        Box seMsgBox = Box.createVerticalBox();
        seMsgBox.setMaximumSize(new Dimension(400, 120));
        seMsgBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
                Map sendparams = new HashMap<>();
                Map data = new HashMap<>();
                Message message = new Message();
                String msg = sendText.getText();
                sendText.setText("");
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                message.setSpokenmanId(HomeInterface.username);
                message.setName(HomeInterface.name);
                message.setHp(HomeInterface.getOwnData().getHeadPortrait());
                message.setMessage(msg);
                message.setDate(date);
                messages.add(message);
                update();
                data.put("message", message);
                sendparams.put("type", MsgType.SEND_MESSAGE_GROUP);
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

    public static void addMessage(Message message) {
        messages.add(message);
        update();
    }

    public static void update() {
        reMsgBox = Box.createVerticalBox();
        reMsgBox.setMaximumSize(new Dimension(400, 400));
        for (Message message : messages) {
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
        rmScrollPane.setViewportView(reMsgBox);
    }
}
