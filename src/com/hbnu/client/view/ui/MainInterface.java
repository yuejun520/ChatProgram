package com.hbnu.client.view.ui;

import com.hbnu.client.*;
import com.hbnu.client.view.component.BackGroundPanel;
import com.hbnu.common.MsgType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/*
*
 * @author luanhao
 * @date 2022年11月03日 12:17
*/


public class MainInterface extends JFrame {
    private static JTextField uFidle;
    private static JTextField pFidle;
    final int WIDTH = 400;
    final int HEIGHT = 800;

    public MainInterface() {
        this.setTitle("聊天程序");
        Box vBox = Box.createVerticalBox();

        this.setBounds(300, 150, WIDTH, HEIGHT);
        this.setResizable(false);

        BackGroundPanel bgPanel = new BackGroundPanel();

//        组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("用户名：");
        uFidle = new JTextField(16);

        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(uFidle);

//        组装密码
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("密    码：");
        pFidle = new JTextField(16);

        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(20));
        pBox.add(pFidle);

//        组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton loginBtn = new JButton("登录");
        JButton getBackBtn = new JButton("忘记密码");
        JButton registerBtn = new JButton("注册");

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = uFidle.getText().trim();
                String password = pFidle.getText().trim();
                Map sendParam = new HashMap<>();
                Map data = new HashMap<>();
                data.put("username", username);
                data.put("password", password);
                sendParam.put("type", MsgType.LOGIN_REQUEST);
                sendParam.put("data", data);
                Communication.send(sendParam);
                HomeInterface.setUsername(username);
            }
        });

        getBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                跳转找回界面
                Client.jf.dispose();
                Client.jf = new GetBackInterface();
            }
        });

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.jf.dispose();
                Client.jf = new RegistrationInterface();
            }
        });

        btnBox.add(loginBtn);
        btnBox.add(Box.createHorizontalStrut(40));
        btnBox.add(getBackBtn);
        btnBox.add(Box.createHorizontalStrut(40));
        btnBox.add(registerBtn);

        vBox.add(Box.createVerticalStrut(100));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(100));
        vBox.add(btnBox);

        bgPanel.add(vBox);
        this.add(bgPanel);

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void setUser(String username, String password) {
        uFidle.setText(username);
        pFidle.setText(password);
    }
}
