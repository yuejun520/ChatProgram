package com.hbnu.server.view;

import com.hbnu.server.jdbc.factory.DAOFactory;
import com.hbnu.server.jdbc.util.RandomUsername;
import com.hbnu.server.jdbc.vo.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
public class AddUserDialog extends JDialog {
    final int WIDTH = 400;
    final int HEIGHT = 600;
    private String username;
    private byte[] headPortrait;

    public AddUserDialog(JFrame jf, String title, boolean isModel) {
        super(jf, title, isModel);
        setSize(WIDTH, HEIGHT);
        Box vBox = Box.createVerticalBox();

        try {
            username = new RandomUsername().getUsername();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(jf, "获取账户名错误");
        }
//        组装用户名
        Box uBox = Box.createHorizontalBox();
        JLabel uLabel = new JLabel("用 户 名：");
        JTextField uFidle = new JTextField(username, 16);
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
                jfc.setFileFilter(new FileNameExtensionFilter("image(*.jpg)","jpg"));
                jfc.setCurrentDirectory(new File("images/"));
                int result = jfc.showOpenDialog(jf);
                if(result == JFileChooser.APPROVE_OPTION) {
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
        JButton submitBtn = new JButton("添加");

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nFidle.getText().trim();
                String password = pFidle.getText().trim();
                String introduction = iFidle.getText().trim();
                String securityQuestion = sqFidle.getText().trim();
                String classifiedAnswer = caFidle.getText().trim();
                User user = new User(Integer.parseInt(username), name, password, introduction, securityQuestion, classifiedAnswer, headPortrait);
                try {
                    DAOFactory.getUserDAOInstance().doInsert(user);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("message", "添加成功");
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
    }
}
