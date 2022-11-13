package com.hbnu.client.view.ui;

import com.hbnu.client.Client;
import com.hbnu.client.Communication;
import com.hbnu.client.util.CheckInfo;
import com.hbnu.client.view.component.BackGroundPanel;
import com.hbnu.common.MsgType;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luanhao
 * @date 2022年11月04日 14:00
 */
public class GetBackInterface extends JFrame {
    private static JTextField sqFidle;
    final int WIDTH = 400;
    final int HEIGHT = 800;

    public GetBackInterface() {
        setTitle("找回密码");
        setBounds(300, 150, WIDTH, HEIGHT);
        setResizable(false);

        BackGroundPanel bgPanel = new BackGroundPanel();

        Box vBox = Box.createVerticalBox();

//        组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("用 户 名：");
        JTextField uFidle = new JTextField(16);

        uFidle.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                String username = uFidle.getText().trim();
                if (!CheckInfo.isFlag(username)) {
                    JOptionPane.showMessageDialog(Client.jf, "账号输入有误");
                } else {
                    Map sendParams = new HashMap<>();
                    Map data = new HashMap<>();
                    data.put("username", username);
                    sendParams.put("type", MsgType.SECURITY_QUESTIONS_REQUEST);
                    sendParams.put("data", data);
                    Communication.send(sendParams);
                }
            }
        });

        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(25));
        uBox.add(uFidle);

//        组装密保问题
        Box sqBox = Box.createHorizontalBox();
        JLabel sqLabel = new JLabel("密保问题：");
        sqFidle = new JTextField(16);
        sqFidle.setEditable(false);

        sqBox.add(sqLabel);
        sqBox.add(Box.createHorizontalStrut(20));
        sqBox.add(sqFidle);

//        组装密保答案
        Box caBox = Box.createHorizontalBox();
        JLabel caLabel = new JLabel("密保答案：");
        JTextField caFidle = new JTextField(16);

        caBox.add(caLabel);
        caBox.add(Box.createHorizontalStrut(20));
        caBox.add(caFidle);

//        组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton submitBtn = new JButton("确认");
        JButton comeBackBtn = new JButton("返回");

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map sendParams = new HashMap<>();
                Map data = new HashMap<>();
                String classifiedAnswer = caFidle.getText().trim();
                data.put("classifiedAnswer", classifiedAnswer);
                sendParams.put("type", MsgType.GET_BACK_REQUEST);
                sendParams.put("data", data);
                Communication.send(sendParams);
            }
        });

        comeBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                返回登录页面
                Client.jf.dispose();
                Client.jf = new MainInterface();
            }
        });


        btnBox.add(submitBtn);
        btnBox.add(Box.createHorizontalStrut(40));
        btnBox.add(comeBackBtn);

        vBox.add(Box.createVerticalStrut(50));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(sqBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(caBox);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(btnBox);

        bgPanel.add(vBox);
        add(bgPanel);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void setSecurityQuestion(String securityQuestion) {
        sqFidle.setText(securityQuestion);
    }
}
