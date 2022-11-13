package com.hbnu.server.view;

import com.hbnu.server.Server;
import com.hbnu.server.jdbc.factory.DAOFactory;
import com.hbnu.server.jdbc.util.RandomUsername;
import com.hbnu.server.jdbc.vo.User;

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
 * @date 2022年11月09日 17:14
 */
public class UpdateUserDialog extends JDialog {
    final int WIDTH = 400;
    final int HEIGHT = 600;
    private String username;
    private byte[] headPortrait;
    private JTextField nFidle;
    private JTextField pFidle;
    private JTextField iFidle;
    private JTextField sqFidle;
    private JTextField caFidle;
    private JLabel hpIcon;

    public UpdateUserDialog(JFrame jf, String title, boolean isModel, String username) {
        super(jf, title, isModel);
        this.username = username;
        setSize(WIDTH, HEIGHT);
        Box vBox = Box.createVerticalBox();

//        组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("用 户 名：");
        JTextField uFidle = new JTextField(username, 16);

        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(25));
        uBox.add(uFidle);

//        组装昵称
        Box nBox = Box.createHorizontalBox();
        JLabel nLabel = new JLabel("昵     称：");
        nFidle = new JTextField(16);

        nBox.add(nLabel);
        nBox.add(Box.createHorizontalStrut(30));
        nBox.add(nFidle);

//        组装密码
        Box pBox = Box.createHorizontalBox();
        JLabel pLabel = new JLabel("密    码：*");
        pFidle = new JTextField(16);

        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(30));
        pBox.add(pFidle);

//        组装个人介绍
        Box iBox = Box.createHorizontalBox();
        JLabel iLabel = new JLabel("个人介绍：");
        iFidle = new JTextField(16);

        iBox.add(iLabel);
        iBox.add(Box.createHorizontalStrut(20));
        iBox.add(iFidle);

//        组装密保问题
        Box sqBox = Box.createHorizontalBox();
        JLabel sqLabel = new JLabel("密保问题：");
        sqFidle = new JTextField(16);

        sqBox.add(sqLabel);
        sqBox.add(Box.createHorizontalStrut(20));
        sqBox.add(sqFidle);

//        组装密保答案
        Box caBox = Box.createHorizontalBox();
        JLabel caLabel = new JLabel("密保答案：");
        caFidle = new JTextField(16);

        caBox.add(caLabel);
        caBox.add(Box.createHorizontalStrut(20));
        caBox.add(caFidle);

        //        组装头像
        Box hpBox = Box.createHorizontalBox();
        JLabel hpLabel = new JLabel("头    像：");
        JButton hpBtn = new JButton("上传文件");
        hpIcon = new JLabel();
        hpIcon.setSize(50, 50);

        hpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setFileFilter(new FileNameExtensionFilter("image(*.jpg)", "jpg"));
                int result = jfc.showOpenDialog(jf);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    ImageIcon icon = new ImageIcon(String.valueOf(file));
                    icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
                    hpIcon.setIcon(icon);
                    hpIcon.setSize(50, 50);
                    hpIcon.updateUI();
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
        hpBox.add(hpIcon);

//        组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton submitBtn = new JButton("修改");

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username_n = uFidle.getText().trim();
                String name = nFidle.getText().trim();
                String password = pFidle.getText().trim();
                String introduction = iFidle.getText().trim();
                String securityQuestion = sqFidle.getText().trim();
                String classifiedAnswer = caFidle.getText().trim();
                User user = new User(Integer.parseInt(username_n), name, password, introduction, securityQuestion, classifiedAnswer, headPortrait);
                try {
                    if (!DAOFactory.getUserDAOInstance().doUpdateId(user, Integer.parseInt(username))) {
                        JOptionPane.showMessageDialog(MainInterface.jf, "该用户名已存在");
                        return;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainInterface.jf, "数据库连接错误");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("message", "修改成功");
                MainInterface.sendLog(params);
                dispose();
            }
        });

        btnBox.add(submitBtn);

        vBox.add(Box.createVerticalStrut(40));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(nBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(iBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(sqBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(caBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hpBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(btnBox);

//        添加两边白边
        Box hBox = Box.createHorizontalBox();
        hBox.add(Box.createHorizontalStrut(30));
        hBox.add(vBox);
        hBox.add(Box.createHorizontalStrut(30));

        this.add(hBox);

        requestData();
    }

    public void requestData() {
        User user;
        try {
            user = DAOFactory.getUserDAOInstance().doQueryById(Integer.parseInt(username));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        nFidle.setText(user.getName());
        pFidle.setText(user.getPassword());
        iFidle.setText(user.getIntroduction());
        sqFidle.setText(user.getSecurityQuestion());
        caFidle.setText(user.getClassifiedAnswer());
        headPortrait = user.getHeadPortrait();
        ImageIcon icon = new ImageIcon(headPortrait);
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        hpIcon.setIcon(icon);
        hpIcon.setSize(50, 50);
        hpIcon.updateUI();
    }
}
