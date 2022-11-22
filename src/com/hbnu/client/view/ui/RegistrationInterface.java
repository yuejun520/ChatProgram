package com.hbnu.client.view.ui;

import com.hbnu.client.Client;
import com.hbnu.client.Communication;
import com.hbnu.client.util.CheckInfo;
import com.hbnu.client.view.component.BackGroundPanel;
import com.hbnu.common.Account;
import com.hbnu.common.MsgType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luanhao
 * @date 2022年11月03日 13:24
 */
public class RegistrationInterface extends JFrame {
    final int WIDTH = 400;
    final int HEIGHT = 800;

    private static JTextField uFidle;
    private byte[] headPortrait;

    public RegistrationInterface() {
        setTitle("用户注册");
        setBounds(300, 150, WIDTH, HEIGHT);
        setResizable(false);

        BackGroundPanel bgPanel = new BackGroundPanel();

        Box vBox = Box.createVerticalBox();

//        组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("用 户 名：");
        uFidle = new JTextField(16);
        uFidle.setEditable(false);

        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(25));
        uBox.add(uFidle);

//        组装昵称
        Box nBox = Box.createHorizontalBox();
        JLabel nLabel = new JLabel("昵     称：");
        JTextField nFidle = new JTextField(16);

        nBox.add(nLabel);
        nBox.add(Box.createHorizontalStrut(30));
        nBox.add(nFidle);

//        组装密码
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("密    码：*");
        JTextField pFidle = new JTextField(16);

        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(30));
        pBox.add(pFidle);

//        组装个人介绍
        Box iBox = Box.createHorizontalBox();
        JLabel iLabel = new JLabel("个人介绍：");
        JTextField iFidle = new JTextField(16);

        iBox.add(iLabel);
        iBox.add(Box.createHorizontalStrut(20));
        iBox.add(iFidle);

//        组装密保问题
        Box sqBox = Box.createHorizontalBox();
        JLabel sqLabel = new JLabel("密保问题：");
        JTextField sqFidle = new JTextField(16);

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

        //        组装头像
        Box hpBox = Box.createHorizontalBox();
        JLabel hpLabel = new JLabel("头    像：");
        JButton hpBtn = new JButton("上传文件");

        hpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setFileFilter(new FileNameExtensionFilter("image(*.jpg)", "jpg"));
                jfc.setCurrentDirectory(new File("images/"));
                int result = jfc.showOpenDialog(Client.jf);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    JLabel jImage = new JLabel();
                    ImageIcon icon = new ImageIcon(String.valueOf(file));
                    icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                    jImage.setIcon(icon);
                    jImage.setSize(50, 50);
                    hpBox.add(jImage);
                    jImage.updateUI();
                    try {
                        BufferedImage bi = ImageIO.read(file);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bi, "jpg", baos);
                        headPortrait = baos.toByteArray();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        hpBox.add(hpLabel);
        hpBox.add(Box.createHorizontalStrut(50));
        hpBox.add(hpBtn);
        hpBox.add(Box.createHorizontalStrut(20));

//        组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton submitBtn = new JButton("确认");
        JButton comeBackBtn = new JButton("返回");

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = uFidle.getText().trim();
                String name = nFidle.getText().trim();
                String password = pFidle.getText().trim();
                if (password.equals("")) {
                    JOptionPane.showMessageDialog(Client.jf, "密码不能为空");
                    return;
                }
                String introduction = iFidle.getText().trim();
                String securityQuestion = sqFidle.getText().trim();
                if (securityQuestion.equals("")) {
                    String[] options = {"确认", "取消"};
                    int i = JOptionPane.showOptionDialog(Client.jf, "若密保问题为空将无法找回密码，是否确认", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "确认");
                    if (i == 1) {
                        return;
                    }
                }
                String classifiedAnswer = caFidle.getText().trim();
                Account account;
                Map sendParams = new HashMap<>();
                Map data = new HashMap<>();
                try {
                    account = CheckInfo.CheckAccount(new Account(Integer.parseInt(username), name, password, introduction, securityQuestion, classifiedAnswer, headPortrait));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                headPortrait = account.getHeadPortrait();
                data.put("username", username);
                data.put("name", account.getName());
                data.put("password", password);
                data.put("introduction", account.getIntroduction());
                data.put("securityQuestion", account.getSecurityQuestion());
                data.put("classifiedAnswer", account.getClassifiedAnswer());
                data.put("headPortrait", headPortrait);
                sendParams.put("type", MsgType.REGISTRATION_REQUEST);
                sendParams.put("data", data);
                Communication.send(sendParams);
            }
        });

        comeBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                跳转登录界面
                Client.jf.dispose();
                Client.jf = new MainInterface();
            }
        });


        btnBox.add(submitBtn);
        btnBox.add(Box.createHorizontalStrut(40));
        btnBox.add(comeBackBtn);

        vBox.add(Box.createVerticalStrut(40));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(nBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(iBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(sqBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(caBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hpBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(btnBox);

        bgPanel.add(vBox);
        add(bgPanel);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getUsername();

    }

    public static void setUsername(String username) {
        uFidle.setText(username);
    }

    public void getUsername() {
        Map sendParams = new HashMap<>();
        sendParams.put("type", MsgType.GET_USERNAME_REQUEST);
        Communication.send(sendParams);
    }
}
